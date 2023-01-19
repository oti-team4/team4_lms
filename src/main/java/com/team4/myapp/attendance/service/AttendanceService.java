package com.team4.myapp.attendance.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.team4.myapp.attendance.dao.IAttendanceRepository;
import com.team4.myapp.attendance.model.Attendance;
import com.team4.myapp.attendance.model.dto.CalendarDto;
import com.team4.myapp.member.dao.IMemberRepository;
import com.team4.myapp.out.dao.IOutRepository;
import com.team4.myapp.out.model.dto.OutListDto;
import com.team4.myapp.out.service.OutService;
import com.team4.myapp.util.date.Today;
import com.team4.myapp.util.scheduler.dto.Statistics;

@Service
public class AttendanceService implements IAttendanceService {

	@Autowired
	IAttendanceRepository attendanceRepository;

	@Autowired
	IMemberRepository memberRepository;

	@Autowired
	IOutRepository outRepository;

	@Autowired
	OutService outService;

	// 근무시간 충족 여부 검사 : 조퇴, 퇴근 시 수행 됨// 5 시간 이상 근무 시 true 리턴한다.
	private boolean calWorkingTime(String memberId, String today, OutListDto out) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = null; // 출근시간
		Date date2 = null; // 퇴근 , 조퇴 버튼 누른 현재 시간 or 조회된 퇴근시간
		long totalOut = 0;

		try {
			// 출근 시간 조회
			String ain = null;
			if (today == null && out == null) {
				ain = attendanceRepository.selectCheckIn(memberId, Today.getToday());
				date2 = new Date();
			} else {
				ain = attendanceRepository.selectCheckIn(memberId, today);
				// 외출 복귀하지 않은 사람의 퇴근시간을 가져온다. -> null : 결석처리 returan false
				// 값이 있으면 -> 해당 값으로 시간을 계산한다.
				String aout = attendanceRepository.selectCheckOut(memberId, today);
				if (aout == null)
					return false;
				date2 = format.parse(aout);

				// 총 외출 시간
				Date outs = new Date();
				outs.setYear(date2.getYear());
				outs.setMonth(date2.getMonth());
				outs.setDate(date2.getDate());
				outs.setHours(out.getHours());
				outs.setMinutes(out.getMinutes());
				outs.setSeconds(out.getSeconds());
				totalOut = out.getTotal() * 1000;
			}
			date = format.parse(ain);
		} catch (ParseException e) {
			System.out.println("calWorkingTime() 에러 ");
			e.printStackTrace();
		}

		// 근무시간 >= 5 이여야 출석 인정
		long total = (date2.getTime() - date.getTime() - totalOut) / 3600000;
		if (total < 5) {
			return false;
		}
		return true;
	}

	public List<CalendarDto> selectMemberAttendance(String memberId, int month) {
		List<Attendance> alist = attendanceRepository.selectMemberAttendance(memberId, month);
		List<CalendarDto> clist = new ArrayList<CalendarDto>();

		for (Attendance a : alist) {
			clist.add(CalendarDto.toCalendarDto(a));
		}
		return clist;
	}

	public void updateChekIn(String memberId) {
		Attendance attendance = new Attendance();
		attendance.setMemberId(memberId);

		String today = Today.getToday();

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		attendance.setCheckIn(ts);

		// 2시 이후 출석 시 결석 처리
		if (ts.getHours() >= 14) {
			attendance.setAttendanceStatus(0);
		}
		// 지각처리
		else if (ts.getHours() >= 9 && ts.getMinutes() > 0) {
			attendance.setAttendanceStatus(2);
		}
		// 정상 출석 처리
		else {
			attendance.setAttendanceStatus(1);
		}
		attendanceRepository.updateCheckIn(attendance, today);
	}

	public void updateCheckOut(String memberId) {
		// memberId, 날짜로 출석 id 찾아서 퇴근 값 넣기
		String today = Today.getToday();
		// 퇴근 버튼 누른 시점에서 퇴근시간 - 출근시간 >= 5 여야 출석 인정 됨
		boolean result = calWorkingTime(memberId, null, null);

		// 근무시간 미달 여부 검사
		if (!result) {	
			attendanceRepository.updateCheckOut(memberId, 0, today);
		} else
			attendanceRepository.updateCheckOut(memberId, -1, today);
	}

	public int selectId(String memberId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		String today = sdf.format(new Date());

		int attendanceId = attendanceRepository.selectId(memberId, today);
		return attendanceId;
	}

	public String selectCheckIn(String memberId) {
		String today = Today.getToday();

		String checkin = attendanceRepository.selectCheckIn(memberId, today);
		return checkin;
	}

	public String selectCheckOut(String memberId) {
		String today = Today.getToday();
		String checkout = attendanceRepository.selectCheckOut(memberId, today);
		return checkout;
	}

	// 출석ID 가져오기
	public int selectAttendanceId(String memberId, Date attendanceDate) {
		return attendanceRepository.selectAttendanceId(memberId, attendanceDate.toString());
	}

	// 날짜와 출석유형 조회하기
	public Attendance selectDataAndCategory(int attendanceId) {
		
		return attendanceRepository.selectDataAndCategory(attendanceId);
	}

	@Transactional
	public void insertAll() {
		String today = Today.getToday();
		// 매일 오전 12시에 스켘줄러에 의해 실행 될 메소드이다.
		// 모든 학생 조회
		// 만약 휴가 신청할 경우 출석 데이터가 미리 존재하는 경우도 있다. <- 처리 필요
		List<String> members = memberRepository.selectAllStudent();
		System.out.println("attendance service: insertAll() " + members);
		// 출석 데이터 삽입하기 (결석상태로 초기화)
		try {
			for (String mid : members) {
				if (attendanceRepository.selectId(mid, today) == null) {
					System.out.println("출석 삽입 : " + mid);
					attendanceRepository.insertAttendance(mid);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(" 출석 삽입 실패 ! ! !");
		}
	}

	// 조퇴 처리 : 퇴근시간=현재시간으로 설정
	// 출근 시간 충족 여부에 따라 결석/ 조퇴 결정 됨
	public void leaveEarly(String memberId) {
		boolean result = calWorkingTime(memberId, null, null);
		String today = Today.getToday();
		if (!result) { // 출근 시간 미달인 경우
			attendanceRepository.updateCheckOut(memberId, 0, today); // 결석 처리
		} else {
			attendanceRepository.updateCheckOut(memberId, 3, today); // 조퇴 처리
		}
	}

	// 스케줄러가 호출함 : 최종적으로 퇴근처리 안한 사람 update 처리
	@Transactional
	public void todayPost(Date date, String today) {
		// 0. 출근했는데 퇴근 시간이 빈 유저 목록 가져오기
		List<Integer> alist = attendanceRepository.selectCheckoutNull(today);

		// 1. 그 사람들 결석처리, 퇴근시간은 null로 유지
		for (int aid : alist) {
			System.out.println(" 결석 처리 : " + aid);
			attendanceRepository.updateAttendanceStatusById(aid, 0, null, null); // 결석 처리
		}
		// 3. 외출데이터에 복귀값 없으면 18:00 로 수정
		SimpleDateFormat sdt = new SimpleDateFormat("YYYY/MM/DD HH:mm:ss");
		date.setHours(18);
		date.setMinutes(0);
		String timestamp = sdt.format(date);
		System.out.println("timestamp  : " + timestamp);
		outRepository.updateOutNull(today, timestamp);

		// 4. 오늘 외출한 member_Id 가져오기
		List<String> members = outRepository.selectMemberId(today);
		OutListDto outListDto = null;
		boolean result;
		for (String mid : members) {
			try {
				// 5. 외출한 사람별 총 외출시간 계산하여
				// 6. 근무시간 계산하고 최종 수정
				outListDto = outService.getOutDetails(mid, today);
				System.out.println(mid + " 의 오늘 하루 총 외출 시간 : " + outListDto.toString());
				result = calWorkingTime(mid, today, outListDto);

				if (!result) { // 출근 시간 미달인 경우 그 상태만 수정한다.
					System.out.println(" 미달 ~~~~~~");
					attendanceRepository.updateAttendanceStatusById(null, 0, mid, today); // 결석 처리
				} /*else {
					attendanceRepository.updateAttendanceStatusById(null, -1, mid, today); // 가존 상태 유지
				}*/

			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	//다음달 통계행 넣기
	@Override
	public void next_statistics() {
		List<String> members = memberRepository.selectAllStudent();
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int year = Calendar.getInstance().get(Calendar.YEAR);
		for (String member : members) {
			attendanceRepository.insertMonthSchedule(member, year, month);
		}
	}
	
	//어제 출결 통계에 넣기
	@Override
	public void insertToday(List<Statistics> mlist, String yesterday, String year, String month) {
		for(Statistics memberStatus : mlist) {
			// 승인된 사유면 출석 +1
			if(memberStatus.getSubmitStatus() == 2) attendanceRepository.insertToday(memberStatus.getMemberId(),1 , yesterday, year, month);		
			else attendanceRepository.insertToday(memberStatus.getMemberId(), memberStatus.getAttendanceStatus(), yesterday, year, month);
		}	
	}
	// 이달의 출석 통계 가져오기
	@Override
	public Statistics selectStatistics(String memberId) {
		return attendanceRepository.selectStatistics(memberId);
	}
}

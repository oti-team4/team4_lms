package com.team4.myapp.cause.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import com.team4.myapp.attendance.dao.IAttendanceRepository;
import com.team4.myapp.cause.dao.ICauseRepository;
import com.team4.myapp.cause.model.Cause;
import com.team4.myapp.cause.model.dto.CauseDto;
import com.team4.myapp.cause.model.dto.CauseListDto;
import com.team4.myapp.util.date.Today;

@Service
public class CauseService implements ICauseService{
	
	@Autowired
	ICauseRepository causeRepository;
	
	@Autowired
	IAttendanceRepository attendanceRepository;
	
	//사유서 작성하기
	@Override
	@Transactional
	public void insertCause(CauseDto causeDto) {
		Cause cause = new Cause();
		int aId = 0;
		if(causeDto.getAttendanceId() == 0) {
			//attendanceId조회
			Integer check = attendanceRepository.selectAttendanceId(causeDto.getMemberId(), Today.getAttendanceDay(causeDto.getAttendanceDate()));
			System.out.println("check: "+check);
			if(check == null) {
				//attendanceID가 없는 경우
				attendanceRepository.insertFutureAttendance(causeDto.getMemberId(), Today.getAttendanceDay(causeDto.getAttendanceDate()), causeDto.getAttendanceStatus());
			} 
			
			//attendanceId가 있음.
			aId = attendanceRepository.selectAttendanceId(causeDto.getMemberId(), Today.getAttendanceDay(causeDto.getAttendanceDate()));
			cause.setAttendanceId(aId);
			System.out.println("attendanceId찾기완료: "+aId);
		} else {
			//attendanceId가 있는 경우
			cause.setAttendanceId(causeDto.getAttendanceId());
		}
		cause.setContent(causeDto.getContent());
		cause.setCategoryId(causeDto.getCategoryId());
		cause.setMemberId(causeDto.getMemberId());
		cause.toString();
		//파일이 있는 경우
		MultipartFile file = causeDto.getFile();
		if(file != null && !file.isEmpty()) {
			cause.setFileName(file.getOriginalFilename());
			cause.setFileSize(file.getSize());
			cause.setFileContentType(file.getContentType());
			try {
				cause.setFileData(file.getBytes());
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("파일에러");
			}
		}
		causeRepository.insertCause(cause);
		attendanceRepository.changeSubmitStatus(cause.getAttendanceId(), 1);
	}	
	
	@Override
	public int selectCauseCount(String memberId) {
		return causeRepository.selectCauseCount(memberId);
	} 
	
	@Override
	public int selectCount() {
		return causeRepository.selectCount();
	}
	
	//사유서 리스트 보기(학생)
	@Override
	public List<CauseListDto> selectCauseList(String memberId, int page) {
		//페이징 처리
		int start = ((page-1) * 5) +1;
		
		List<CauseListDto> list = causeRepository.selectCauseList(memberId, start, start+4);
		for(CauseListDto i : list) {
			String  s1 = i.attendanceStatus(i.getAttendanceStatus());
			String s2 = i.submitStatus(i.getCauseStatus());
			i.setAttendanceStatusString(s1);
			i.setCauseStatusString(s2);
		}		
		return list;
	}

	//사유서 리스트 보기(관리자)
	@Override
	public List<CauseListDto> selectCauseListAdmin(int page) {
		//페이징 처리
		int start = ((page-1) * 5) +1;
		
		List<CauseListDto> list = causeRepository.selectCauseAdmin(start, start+4);
		for(CauseListDto i : list) {
			String  s1 = i.attendanceStatus(i.getAttendanceStatus());
			String s2 = i.submitStatus(i.getCauseStatus());
			i.setAttendanceStatusString(s1);
			i.setCauseStatusString(s2);
		}		
		return list;
	}

	//사유서 자세히 보기
	@Override
	public CauseListDto selectCauseDetail(int causeId) {
		CauseListDto list = causeRepository.selectCauseDetail(causeId);
		String  s1 = list.attendanceStatus(list.getAttendanceStatus());
		String s2 = list.submitStatus(list.getCauseStatus());
		String s3 = list.categoryString(list.getCategoryId());
		list.setAttendanceStatusString(s1);
		list.setCauseStatusString(s2);
		list.setCategoryString(s3);
		
		return list;
	}
	
	//사진파일 불러오기용
	@Transactional
	public Cause selectFileDetail(int causeId) {
		CauseListDto causeDto = causeRepository.selectCauseDetail(causeId);
		Cause cause = new Cause();
		cause.setFileData(causeDto.getFileData());
		cause.setFileContentType(causeDto.getFileContentType());
		cause.setFileSize(causeDto.getFileSize());
		cause.setFileName(causeDto.getFileName());
		return cause;
	}

	//사유서 수정하기
	@Transactional
	public void updateCause(Cause cause) {
		
		if(cause.getFile() != null && !cause.getFile().isEmpty()) {
			cause.setFileName(cause.getFile().getOriginalFilename());
			cause.setFileSize(cause.getFile().getSize());
			cause.setFileContentType(cause.getFile().getContentType());
			try {
				cause.setFileData(cause.getFile().getBytes());
			} catch (Exception e) {
				e.printStackTrace();
			}
			causeRepository.updateCauseFile(cause);
		} else {
			causeRepository.updateCauseContent(cause.getContent(), cause.getCauseId());
		}
		
	}
	
	//사유서 삭제하기
	@Transactional
	public void deleteCause(int causeId) {
		int attendanceId = attendanceRepository.selectAttendanceIdByCauseId(causeId);
		causeRepository.deleteCause(causeId);
		attendanceRepository.changeSubmitStatus(attendanceId, 0);
	}

	@Override
	public void accept(int causeId, int causeStatus) {
		causeRepository.accept(causeId, causeStatus);	
		attendanceRepository.attendanceUcc(causeId, causeStatus+1);	
	}

	@Override
	public List<Integer> getSubmitStatusNo() {
		List<Integer> submitStatusList= new ArrayList<Integer>();
		submitStatusList.add(causeRepository.getSubmitStatusNo(0));
		submitStatusList.add(causeRepository.getSubmitStatusNo(1));
		submitStatusList.add(causeRepository.getSubmitStatusNo(2));
		return submitStatusList;
	}

	@Override
	public List<CauseListDto> selectCauseListAdminDate(String date, int page) {
		//페이징 처리
		int start = ((page-1) * 5) +1;
				
		List<CauseListDto> list = causeRepository.selectCauseListAdminDate(date, start, start+4);
		for(CauseListDto i : list) {
			String  s1 = i.attendanceStatus(i.getAttendanceStatus());
			String s2 = i.submitStatus(i.getCauseStatus());
			i.setAttendanceStatusString(s1);
			i.setCauseStatusString(s2);
		}				
		return list;
	}

	@Override
	public List<Integer> getSubmitStatusDateNo(String date) {
		List<Integer> submitStatusList= new ArrayList<Integer>();
		submitStatusList.add(causeRepository.getSubmitStatusDateNo(0, date));
		submitStatusList.add(causeRepository.getSubmitStatusDateNo(1, date));
		submitStatusList.add(causeRepository.getSubmitStatusDateNo(2, date));
		return submitStatusList;
	}

	@Override
	public int selectDateCount(String date) {
		return causeRepository.selectDateCount(date);
	}

	@Override
	public int selectByAttendanceId(int attendaceId) {
		return causeRepository.selectByAttendanceId(attendaceId);
	}

}

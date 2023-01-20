package com.team4.myapp.attendance.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.team4.myapp.attendance.model.Attendance;
import com.team4.myapp.util.scheduler.dto.Statistics;

public interface IAttendanceRepository {
	List<Attendance> selectMemberAttendance(@Param("memberId")String memberId,  @Param("month") int month);
	void insertAttendance(String memberId);

	void updateCheckIn(@Param("attendance") Attendance attendance, @Param("today") String today);
	void updateCheckOut(@Param("memberId") String memberId, @Param("attendanceStatus") int attendanceStatus, @Param("today") String today);
	
	Integer selectId(@Param("memberId") String memberId, @Param("today") String today);
	Integer selectAttendanceId(@Param("memberId") String memberId, @Param("attendanceDate") String attendanceDate);
	
	String selectCheckIn(@Param("memberId") String memberId, @Param("today") String today);
	String selectCheckOut(@Param("memberId") String memberId, @Param("today") String today);
	
	void insertFutureAttendance(@Param("memberId")String memberId, @Param("attendanceDate") String attendanceDate, @Param("attendanceStatus") int attendanceStatus);
	Attendance selectDataAndCategory(int attendanceId);
	
	void changeSubmitStatus(@Param("attendanceId")int attendanceId, @Param("s_status")int s_status);
	
	void insertToday(@Param("memberId")String memberId, @Param("attendanceStatus")int attendanceStatus,@Param("yesterday") String yesterday, @Param("year") String year, @Param("month") String month);
	List<Statistics> selectStatus(@Param("yesterday") String yesterday);
	
	List<Integer> selectCheckoutNull(String today);
	String selectCheckInById(int attendanceId);
	
	void attendanceUcc(@Param("causeId") int causeId, @Param("submitStatus") int submitStatus);
	void insertMonthSchedule(@Param("member") String member, @Param("year") int year, @Param("month") int month);

	void updateAttendanceStatusById(@Param("attendanceId") Integer attendanceId,  @Param("attendanceStatus") int attendanceStatus,
			@Param("memberId") String memberId, @Param("today") String today);
	
	Statistics selectStatistics(@Param("memberId")String memberId);
	int selectAttendanceIdByCauseId(int causeId);
	}

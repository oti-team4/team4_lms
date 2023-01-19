package com.team4.myapp.cause.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.team4.myapp.cause.model.Cause;
import com.team4.myapp.cause.model.dto.CauseListDto;


public interface ICauseRepository {
	int selectCauseCount(String memberId);
	int selectCount();
	CauseListDto selectCauseDetail(int causeId);
	
	void insertCause(Cause cause);
	
	List<CauseListDto> selectCauseList(@Param("memberId") String memberId, @Param("start")int start, @Param("end") int end);
	
	List<CauseListDto> selectCauseAdmin(@Param("start")int start, @Param("end") int end);
	
	void updateCauseFile(Cause cause);

	void updateCauseContent(@Param("content") String content, @Param("causeId") int causeId);
	void deleteCause(int causeId);

	void accept(@Param("causeId") int causeId, @Param("causeStatus") int causeStatus);
	int getSubmitStatusNo(int status);
	List<CauseListDto> selectCauseListAdminDate(@Param("date") String date, @Param("start") int start, @Param("end") int end);
	
	Integer getSubmitStatusDateNo(@Param("status") int status, @Param("date") String date);
	int selectDateCount(String date);
	
	int selectByAttendanceId(int attendaceId);
}

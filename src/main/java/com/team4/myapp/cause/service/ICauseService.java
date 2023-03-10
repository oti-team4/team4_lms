package com.team4.myapp.cause.service;

import java.util.List;

import com.team4.myapp.cause.model.Cause;
import com.team4.myapp.cause.model.dto.CauseDto;
import com.team4.myapp.cause.model.dto.CauseListDto;
import com.team4.myapp.reasoncategory.model.ReasonCategory;

public interface ICauseService {	
	int selectCount();
	int selectCauseCount(String memberId);
	CauseListDto selectCauseDetail(int causeId);
	Cause selectFileDetail(int causeId);
	
	void insertCause(CauseDto causeDto);

	List<CauseListDto> selectCauseList(String memberId, int page);
	List<CauseListDto> selectCauseListAdmin(int page);
	
	void updateCause(Cause cause);
	void deleteCause(int causeId);
	
	void accept(int causeId, int causeStatus);
	List<Integer> getSubmitStatusNo();
	
	List<CauseListDto> selectCauseListAdminDate(String date, int page);
	List<Integer> getSubmitStatusDateNo(String date);
	int selectDateCount(String date);
	int selectByAttendanceId(int attendaceId);
	
	
}

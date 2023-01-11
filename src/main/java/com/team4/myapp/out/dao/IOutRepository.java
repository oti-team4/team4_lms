package com.team4.myapp.out.dao;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.team4.myapp.out.model.OutDto;

public interface IOutRepository {
	OutDto selectCheckOut(@Param("memberId") String memberId, @Param("today") String today);
	
	void insertCheckIn(String membetId);
	
	void updateCheckOut(String memberId);
	
	List<OutDto> selectOutList(@Param("memberId") String memberId, @Param("today") String today);
	
	void updateOutNull(@Param("today") String today, @Param("timestamp") Timestamp timestamp);

	List<String> selectMemberId(String today);
}

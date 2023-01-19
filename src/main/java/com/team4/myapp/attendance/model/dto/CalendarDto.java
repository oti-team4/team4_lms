package com.team4.myapp.attendance.model.dto;

import java.util.Date;

import com.team4.myapp.attendance.model.Attendance;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CalendarDto {
	private Date start;
	private Date end;
	private String title;
	private String id;
	private int attendanceStatus;
	private int submitStatus;
	private String memberName;
	private int lectureId;
	private String backgroundColor;
	private String borderColor;
	private String url;
	
	public static CalendarDto toCalendarDto(Attendance attendance) {
		String status = "";
		String color = "";
		String url = null;
		int aStatus = attendance.getAttendanceStatus();
		System.out.println(attendance.getAttendanceDate());

		switch (aStatus) {
		case 0:
			status = "결석";
			color = "#FF6363";
			break;
		case 1:
			status = "출석";
			color = "#47D99E";
			break;
		case 2:
			status = "지각";
			color = "#efca66";
			break;
		case 3:
			status = "조퇴";
			color = "#FFAB76";
			break;
		}
		
		// 승인된 사유라면 출석버튼과 동일한 색상으로 처리
		if(attendance.getSubmitStatus() == 2) color = "#47D99E";

		if(attendance.getAttendanceStatus() == 1) url = "/attendance/main";
		else if(attendance.getAttendanceStatus() != 1 && attendance.getSubmitStatus() == 0) {
			// 작성페이지
			url = "/cause/write?attendanceId=" + attendance.getAttendanceId();
		} 
		else if(attendance.getAttendanceStatus() != 1 && (attendance.getSubmitStatus() == 2 
				|| attendance.getSubmitStatus() == 3)) {
			// 리스트 페이지
			url = "/cause/list/1";
		}
		else if(attendance.getAttendanceStatus() != 1 && attendance.getSubmitStatus() == 1){
			// 수정페이지
			url = "/cause/update/0?attendanceId=" + attendance.getAttendanceId();
		}  
		
		if(attendance.getCheckIn() == null && attendance.getCheckOut() == null) return  new CalendarDto(attendance.getAttendanceDate(), attendance.getAttendanceDate(), "-", attendance.getAttendanceId()+"",
				attendance.getAttendanceStatus(), attendance.getSubmitStatus(), attendance.getMemberName(), attendance.getLectureId(), color, color, url);
		
		return new CalendarDto(attendance.getCheckIn(), attendance.getCheckOut(), status, attendance.getAttendanceId()+"",
				attendance.getAttendanceStatus(), attendance.getSubmitStatus(), attendance.getMemberName(), attendance.getLectureId(), color, color, url);

	}
}
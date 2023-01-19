package com.team4.myapp.util.scheduler.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Statistics {
	private int attendanceStatus;
	private String memberId;
	private int year;
	private int month;
	private int attendance;
	private int absent;
	private int late;
	private int leave;
	private int submitStatus;
}

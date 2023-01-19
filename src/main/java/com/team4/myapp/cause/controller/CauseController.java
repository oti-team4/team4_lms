package com.team4.myapp.cause.controller;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team4.myapp.attendance.model.Attendance;
import com.team4.myapp.attendance.service.IAttendanceService;
import com.team4.myapp.cause.model.Cause;
import com.team4.myapp.cause.model.dto.CauseDto;
import com.team4.myapp.cause.model.dto.CauseListDto;
import com.team4.myapp.cause.service.ICauseService;
import com.team4.myapp.interceptor.Auth;
import com.team4.myapp.interceptor.Role;
import com.team4.myapp.reasoncategory.model.ReasonCategory;
import com.team4.myapp.reasoncategory.service.IReasonCategoryService;

@Controller
public class CauseController {
	static final Logger logger = LoggerFactory.getLogger(CauseController.class);
	
	@Autowired
	ICauseService causeService;
	
	@Autowired
	IReasonCategoryService reansonCategoryService;
	
	@Autowired
	IAttendanceService attendanceService;
	
	//사유서 작성폼 띄우기
	@RequestMapping(value = "/cause/write", method = RequestMethod.GET)
	public String causeForm(@RequestParam(value="attendanceId", required=false, defaultValue="0") int attendanceId, Model model) {
		System.out.println("causeForm띄우기");
		//사유 카테고리 불러오기
		if(attendanceId != 0) {
			//attendance_id로 날짜와 카테고리 불러오기(달력에서 작성버튼 누름)
			Attendance dateAndCategory = attendanceService.selectDataAndCategory(attendanceId);			
			model.addAttribute("dateAndCategory", dateAndCategory);	
		}
		List<ReasonCategory> categoryList = reansonCategoryService.selectCategoryList();
		model.addAttribute("attendanceId", attendanceId);
		model.addAttribute("categoryList", categoryList);
		return "cause/write";
	}
	
	//사유서 작성하기
	@RequestMapping(value="/cause/write", method = RequestMethod.POST)
	public String insertCauseWrite(CauseDto causeDto, RedirectAttributes redirectAttrs, HttpSession session) {
		
		try {
			MultipartFile file = causeDto.getFile();
			if(file != null && !file.isEmpty()) {
				logger.info("/cause/write file: " + file.getOriginalFilename() + file.getSize());
			}		
			causeService.insertCause(causeDto);
			System.out.println("글쓰기 성공");
			logger.info("/cause/write : "+ causeDto.toString());
			return "redirect:/cause/list/"+ session.getAttribute("page");
			
		} catch(Exception e) {
			e.printStackTrace();
			redirectAttrs.addFlashAttribute("message", e.getMessage());
		}
		return "cause/write";
	}
	
	//사유서 목록 불러오기(유저)
	@RequestMapping(value = "/cause/list/{page}", method = RequestMethod.GET)
	public String selectCauseList(@PathVariable int page, HttpSession session, Model model) {
		//리스트 불러오기
		session.setAttribute("page", page);
		System.out.println("멤버id: "+session.getAttribute("memberid").toString());
		List<CauseListDto> causeList = causeService.selectCauseList((String)session.getAttribute("memberid"), page);
		System.out.println("사이즈: "+causeList.size() + "  페이징: "+page);
		model.addAttribute("causeList", causeList);
		String memberId = session.getAttribute("memberid").toString();
		
		//전체 페이지 구하기(5페이지씩 구분)
		int bbsCount = causeService.selectCauseCount(memberId);
		int totalPageCount=0;
		if(bbsCount > 0) {
			totalPageCount = (int)Math.ceil(bbsCount/5.0);
		}
		
		model.addAttribute("totalPageCount",totalPageCount);
		model.addAttribute("page",page);
		model.addAttribute("boardType", "/cause/list/");
		
		return "cause/list";
	}
	
	//사유서 목록 보기 (관리자)
	@Auth(role = Role.PROFESSOR)
	@RequestMapping(value="/cause/admin/list/{page}", method = RequestMethod.GET)
	public String selectCauseListAdmin(@PathVariable int page, HttpSession session, Model model) {
		//리스트 불러오기
		session.setAttribute("page", page);
		List<CauseListDto> causeList = causeService.selectCauseListAdmin(page);
		model.addAttribute("causeList", causeList);
		
		//대기 수		
		model.addAttribute("awaitNo", causeService.getSubmitStatusNo().get(0));
		model.addAttribute("approveNo", causeService.getSubmitStatusNo().get(1));
		model.addAttribute("rejectNo",causeService.getSubmitStatusNo().get(2));
		
		//전체 페이지 구하기(5페이지씩 구분)
		int bbsCount = causeService.selectCount();
		int totalPageCount=0;
		if(bbsCount > 0) {
			totalPageCount = (int)Math.ceil(bbsCount/5.0);
		}
		model.addAttribute("totalPageCount",totalPageCount);
		model.addAttribute("page",page);
		model.addAttribute("boardType","/cause/admin/list/");
		
		return "manager/list";
	}
	
	//신청서 상세 조회
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="YYYY-MM-dd")
	@ResponseBody
	@RequestMapping(value="/cause/detail/{id}", method=RequestMethod.GET)
	public CauseListDto selectCauseDetail(@PathVariable int id, HttpSession session, Model model) {
		CauseListDto cdlist = causeService.selectCauseDetail(id);
		logger.info(cdlist.toString());
		model.addAttribute("cdlist", cdlist);
		return cdlist;
	}
	
	@Auth(role = Role.PROFESSOR)
	@RequestMapping(value="/cause/admin/accept", method=RequestMethod.POST)
	public String accept(CauseListDto cause, int page) {	
		causeService.accept(cause.getCauseId(),cause.getCauseStatus());
		return "redirect:/cause/admin/list/"+page;
	}
	
	@Auth(role = Role.PROFESSOR)
	@RequestMapping(value="/cause/admin/date/{page}", method = RequestMethod.GET)
	public String selectCauseListAdminDate(@PathVariable int page, String keyword, HttpSession session, Model model) {
		//리스트 불러오기
		List<CauseListDto> causeList = causeService.selectCauseListAdminDate(keyword,page);
		model.addAttribute("causeList", causeList);
		
		//대기 수		
		model.addAttribute("awaitNo", causeService.getSubmitStatusDateNo(keyword).get(0));
		model.addAttribute("approveNo", causeService.getSubmitStatusDateNo(keyword).get(1));
		model.addAttribute("rejectNo",causeService.getSubmitStatusDateNo(keyword).get(2));
		
		//전체 페이지 구하기(5페이지씩 구분)
		int bbsCount = causeService.selectDateCount(keyword);
		int totalPageCount=0;
		if(bbsCount > 0) {
			totalPageCount = (int)Math.ceil(bbsCount/5.0);
		}
		model.addAttribute("totalPageCount",totalPageCount);
		model.addAttribute("page",page);
		model.addAttribute("keyword", keyword);
		model.addAttribute("boardType","/cause/admin/date");
		
		return "manager/list";
	}
	
	//사진 보여주기
	@RequestMapping("/file/{causeId}")
	public ResponseEntity<byte[]> getFile(@PathVariable int causeId){
		Cause file = causeService.selectFileDetail(causeId);
		logger.info("getFile "+ file.toString());
		final HttpHeaders headers = new HttpHeaders();
		String[] mtypes = file.getFileContentType().split("/");
		headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
		headers.setContentLength(file.getFileSize());
		headers.setContentDispositionFormData("attachment", file.getFileName(), Charset.forName("UTF-8"));
		return new ResponseEntity<byte[]>(file.getFileData(), headers, HttpStatus.OK);
	}
	
	// 사유서 수정
	@RequestMapping(value = "/cause/update/{causeId}", method = RequestMethod.GET)
	public String updateCause(@PathVariable int causeId,
         @RequestParam(value = "attendanceId", required = false, defaultValue = "0") int attendanceId, Model model) {
		CauseListDto cause = null;
		if (attendanceId != 0) {
			System.out.println("~!!!!!  " + attendanceId);
			// attendanceId 로 causeId 조회하는 서비스 구현
			int cid = causeService.selectByAttendanceId(attendanceId);
			cause = causeService.selectCauseDetail(cid);
		} else {
			cause = causeService.selectCauseDetail(causeId);         
		}
	  logger.info("/cause/update : " + cause.toString());
	  model.addAttribute("list", cause);
	  return "cause/update";
	}
	
	@RequestMapping(value="/cause/update", method=RequestMethod.POST)
	public String updateCause(Cause cause, HttpSession session) {
		logger.info("/cause/update : "+ cause.toString());
	
		causeService.updateCause(cause);
		if(session.getAttribute("page") == null) {
			session.setAttribute("page", 1);
		}
		return "redirect:/cause/list/"+(Integer)session.getAttribute("page");
	}
	
	//사유서 삭제
	@RequestMapping(value="/cause/delete", method=RequestMethod.POST)
	public String deleteCause(int causeId, HttpSession session) {
		try {
			causeService.deleteCause(causeId);
			System.out.println("삭제 완료");
			return "redirect:/cause/list"+"/" + (Integer)session.getAttribute("page");
		}catch(Exception e) {
			e.printStackTrace();
			return "home";
		}
	}
	
	
}

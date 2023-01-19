package com.team4.myapp.board.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.team4.myapp.board.model.Board;
import com.team4.myapp.board.service.IBoardService;
import com.team4.myapp.reply.model.Reply;
import com.team4.myapp.reply.service.IReplyService;
import com.team4.myapp.interceptor.Auth;
import com.team4.myapp.interceptor.Role;


@Controller
public class BoardController {
	
	@Autowired
	IBoardService boardService;
	
	@Autowired
	IReplyService replyService;
	// 작성 폼 열기
	@RequestMapping(value = "/board/write/{boardType}", method = RequestMethod.GET)
	public String writeArticle(@PathVariable String boardType, Model model) {
		model.addAttribute("boardType", boardType);
		return "board/writeform";
	}

	@RequestMapping(value="/board/write", method=RequestMethod.POST)
	public String writeArticle(Board board, RedirectAttributes redirectAttrs) {
		try{
			board.setContent(board.getContent().replace("\r\n", "<br>"));
			board.setTitle(Jsoup.clean(board.getTitle(), Whitelist.basic()));
			board.setContent(Jsoup.clean(board.getContent(), Whitelist.basic()));
			MultipartFile mfile = board.getFile();
			if(mfile!=null && !mfile.isEmpty()) {
				board.setFileName(mfile.getOriginalFilename());
				board.setFileSize(mfile.getSize());
				board.setFileContentType(mfile.getContentType());
				board.setFileData(mfile.getBytes());
				boardService.insertFileArticle(board);
			}else {
				boardService.insertArticle(board);
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("");
			redirectAttrs.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/board/list/"+board.getBoardType();
	}
	
	@RequestMapping("/board/list/{boardType}/{page}")
	public String getListByCategory(@PathVariable String boardType, @PathVariable int page, HttpSession session, Model model) {
		session.setAttribute("page", page);
		model.addAttribute("boardType", boardType);
		
		List<Board> boardList = boardService.selectArticleListByCategory(boardType, page);
		model.addAttribute("boardList", boardList);

		// paging start
		int bbsCount = boardService.selectTotalArticleCountByCategoryId(boardType);
		int totalPage = 0;
		if(bbsCount > 0) {
			totalPage= (int)Math.ceil(bbsCount/10.0);
		}
		model.addAttribute("totalPageCount", totalPage);
		model.addAttribute("page", page);
		return "board/boardlist";
	}

	@RequestMapping("/board/list/{boardType}")
	public String getListByCategory(@PathVariable String boardType, HttpSession session, Model model) {
		return getListByCategory(boardType, 1, session, model);
	}

	@RequestMapping("/board/detail/{boardId}")
	public String getBoardDetails(@PathVariable int boardId, Model model) {		
		Board board = boardService.selectArticle(boardId);
		List <Reply> reply = replyService.selectReply(boardId);
		model.addAttribute("board", board);
		model.addAttribute("boardType", board.getBoardType());
		model.addAttribute("reply", reply);
		return "board/boarddetail";
	}
	
	@RequestMapping("/board/download/{boardId}")
	public ResponseEntity<byte[]> getFile(@PathVariable int boardId) {
		Board board= boardService.getFile(boardId);
		final HttpHeaders headers = new HttpHeaders();
		String[] mtypes = board.getFileContentType().split("/");
		headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
		headers.setContentLength(board.getFileSize());
		headers.setContentDispositionFormData("attachment", board.getFileName(), Charset.forName("UTF-8"));
		return new ResponseEntity<byte[]>(board.getFileData(), headers, HttpStatus.OK);
	}
	
	@RequestMapping("/board/download/{boardId}/cnt")
	public ResponseEntity<byte[]> getFileCount(@PathVariable int boardId) {
		Board board= boardService.getFileCount(boardId);
		final HttpHeaders headers = new HttpHeaders();
		String[] mtypes = board.getFileContentType().split("/");
		headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
		headers.setContentLength(board.getFileSize());
		headers.setContentDispositionFormData("attachment", board.getFileName(), Charset.forName("UTF-8"));
		return new ResponseEntity<byte[]>(board.getFileData(), headers, HttpStatus.OK);
	}
	
	@RequestMapping(value="/board/update/{boardId}", method=RequestMethod.GET)
	public String updateArticle(@PathVariable int boardId, Model model) {
		Board board = boardService.selectArticle(boardId);
		model.addAttribute("board", board);
		return "board/updateform";
	}

	@RequestMapping(value="/board/update", method=RequestMethod.POST)
	public String updateArticle(Board board, RedirectAttributes redirectAttrs) {
		try{
			board.setTitle(Jsoup.clean(board.getTitle(), Whitelist.basic()));
			board.setContent(Jsoup.clean(board.getContent(), Whitelist.basic()));
			MultipartFile mfile = board.getFile();
			if(mfile!=null && !mfile.isEmpty()) {
				//BoardUploadFile file = new BoardUploadFile();
				board.setFileName(mfile.getOriginalFilename());
				board.setFileSize(mfile.getSize());
				board.setFileContentType(mfile.getContentType());
				board.setFileData(mfile.getBytes());
			}
			boardService.updateArticle(board);
			
		}catch(Exception e){
			e.printStackTrace();
			redirectAttrs.addFlashAttribute("message", e.getMessage());
		}
		return "redirect:/board/detail/"+board.getBoardId();
	}
	
	@RequestMapping(value="/board/delete", method=RequestMethod.POST)
	public String deleteArticle(Board board, HttpSession session, Model model) {
		try {	
			boardService.deleteArticle(board.getBoardId());
			return "redirect:/board/list/" + board.getBoardType() + "/" + (Integer)session.getAttribute("page");			
		}catch(Exception e){
			model.addAttribute("message", e.getMessage());
			e.printStackTrace();
			return "error/runtime";
		}
	}
	
	@RequestMapping("/board/search/{boardType}/{page}")
	public String search(@RequestParam(required=false, defaultValue="") String keyword, @PathVariable String boardType, @PathVariable int page, HttpSession session, Model model) {
		try {
			List<Board> boardList = boardService.searchListByContentKeyword(keyword, boardType, page);
			model.addAttribute("boardList", boardList);
			session.setAttribute("page", page);
	
			// 검색 결과 페이징 처리
			int bbsCount = boardService.selectTotalArticleCountByKeyword(keyword, boardType);
			int totalPage = 0;

			if(bbsCount > 0) {
				totalPage= (int)Math.ceil(bbsCount/10.0);
			}
			model.addAttribute("totalPageCount", totalPage);
			model.addAttribute("page", page);
			model.addAttribute("keyword", keyword);
			model.addAttribute("boardType", boardType);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "board/boardsearch";
	}
	
	@RequestMapping("/board/like/{boardId}")
	public String addHeartCount(@PathVariable int boardId) {
		boardService.addHeartCount(boardId);
		return "redirect:/board/detail/"+boardId;
	}

}

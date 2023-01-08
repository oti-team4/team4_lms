package com.team4.myapp;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class TestBoardController {

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/board", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		return "board1";
	}
	
}
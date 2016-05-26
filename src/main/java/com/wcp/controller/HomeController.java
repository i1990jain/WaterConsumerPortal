package com.wcp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	// Loading the home Page
	@RequestMapping("/")
	public ModelAndView homePage() {
		System.out.println("yo");
		String message = "Water Consumer Portal";
		return new ModelAndView("login", "message", message);
	}

	/*
	 * @RequestMapping(method = RequestMethod.GET) public String getIndexPage()
	 * { return "welcome"; }
	 */

}

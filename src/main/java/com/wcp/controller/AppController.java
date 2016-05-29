package com.wcp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class AppController {

	// Loading the home Page

	@RequestMapping(method = RequestMethod.GET)
	public String getIndexPage() {
		return "index";
	}

	/*
	 * @RequestMapping(method = RequestMethod.GET) public String getIndexPage()
	 * { return "/login"; }
	 */

}

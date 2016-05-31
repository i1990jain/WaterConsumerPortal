package com.wcp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class PageTemplateController {

	@RequestMapping(value = "/login")
	public String getLogin() {
		return "pages/login";
	}

	@RequestMapping("/home")
	public String homePage() {
		System.out.println("loading user");
		return "pages/home";
	}

	@RequestMapping("/histogram")
	public String loadHistogram() {
		System.out.println("loading histogram");
		return "pages/histogram";
	}
}

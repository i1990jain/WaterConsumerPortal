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

	@RequestMapping("/register")
	public String register() {
		System.out.println("Loading register page");
		return "pages/register";
	}

	@RequestMapping("/registersuccess")
	public String registersuccess() {
		System.out.println("Loading registersuccess page");
		return "pages/registersuccess";
	}

	@RequestMapping("/mapview")
	public String mapview() {
		System.out.println("Loading mapview page");
		return "pages/mapview";
	}
	@RequestMapping("/nonregistereduserpage")
	public String nonregistereduserpage() {
		System.out.println("Loading nonregistereduserpage page");
		return "pages/nonregistereduserpage";
	}
}

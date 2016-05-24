package com.wcp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping("/")
	public ModelAndView homePage() {
		System.out.println("yo");
		String message = "<br><div style='text-align:center;'>" + "<h3> Water Consumer Portal</h3></div><br><br>";
		return new ModelAndView("welcome", "message", message);
	}
}

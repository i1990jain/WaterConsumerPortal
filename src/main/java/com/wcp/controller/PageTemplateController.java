package com.wcp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/app")
public class PageTemplateController {

	@RequestMapping(value = "/login")
	public String getComputersTemplate() {
		return "pages/login";
	}
}

package com.wcp.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.wcp.model.User;

@RestController
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView loginAction(@RequestBody User jsonString) {
		System.out.println("login yayy");
		String message = jsonString.getUsername() + "Welcome";
		return new ModelAndView("home", "message", message);
	}
}

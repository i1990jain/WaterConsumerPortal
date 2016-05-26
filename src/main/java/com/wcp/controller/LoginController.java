package com.wcp.controller;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wcp.model.User;

@RestController
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@Produces({ MediaType.APPLICATION_JSON })
	public String loginAction(@RequestBody User jsonString) {
		System.out.println("login yayy" + jsonString.getUsername());
		String message = jsonString.getUsername() + "Welcome";
		return "home";
	}
}

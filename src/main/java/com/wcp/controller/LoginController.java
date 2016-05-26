package com.wcp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.wcp.model.User;

@RestController
public class LoginController {

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<Map<String, Object>> loginAction(@RequestBody User jsonString) {
		System.out.println("login yayy" + jsonString.getUsername());
		Map<String, Object> map = new HashMap<>();
		String message = jsonString.getUsername() + "Welcome";
		map.put("username", jsonString.getUsername());
		map.put("result", "success");
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@RequestMapping("/home")
	public ModelAndView homePage() {
		System.out.println("loading user");
		String message = "Water Consumer Portal";
		return new ModelAndView("home", "message", message);
	}
}

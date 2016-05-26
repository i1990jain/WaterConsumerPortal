package com.wcp.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.wcp.model.User;
import com.wcp.service.LoginService;

@RestController
public class LoginController {

	@Autowired
	LoginService loginService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<Map<String, Object>> loginAction(@RequestBody User jsonString)
			throws MySQLIntegrityConstraintViolationException, SQLException, Exception {
		System.out.println("login yayy" + jsonString.getUsername());
		User user = new User();
		user.setUsername(jsonString.getUsername());
		user.setPassword(jsonString.getPassword());

		System.out.println("username: " + user.getUsername());
		System.out.println("password: " + user.getPassword());

		int result = loginService.userLogin(user);

		user.setOid(result);

		System.out.println("the result is " + result);

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

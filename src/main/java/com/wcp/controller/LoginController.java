package com.wcp.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.wcp.model.User;
import com.wcp.service.LoginService;

@Controller
public class LoginController {

	@Autowired
	LoginService loginService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<Map<String, Object>> loginAction(@RequestBody User user)
			throws MySQLIntegrityConstraintViolationException, SQLException, Exception {
		System.out.println("login yayy" + user.getUsername());
		System.out.println(user);

		System.out.println("username: " + user.getUsername());
		System.out.println("password: " + user.getPassword());

		int result = loginService.userLogin(user);
		if (result != 0) {
			user.setOid(result);

			System.out.println("the result is " + result);

			Map<String, Object> map = new HashMap<>();

			map.put("username", user.getUsername());
			map.put("token", result);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
			Map<String, Object> map = new HashMap<>();

			map.put("username", user.getUsername());

			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}
	}

}

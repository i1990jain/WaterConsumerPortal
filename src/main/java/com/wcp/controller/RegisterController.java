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
import com.wcp.service.RegisterService;
import com.wcp.datamodel.RegisterData;
@Controller
public class RegisterController {

	@Autowired
	RegisterService registerService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<Map<String, Object>> registerAction(@RequestBody RegisterData registerdata)
			throws MySQLIntegrityConstraintViolationException, SQLException, Exception {

		System.out.println("RegisterationCheckCOntroller");
		System.out.println("User Name :" + registerdata.getUserName());
		System.out.println("First Name :" + registerdata.getFirstName());
		System.out.println("Last Name: " + registerdata.getLastName());
		System.out.println("HouseholdID : "+ registerdata.getHouseholdID());
		System.out.println("ZipCode : "+ registerdata.getZipCode());
		System.out.println("Password : "+ registerdata.getPassword());
	

		Map<String, String> resultMap = new HashMap<>();
		Map<String, Object> map = new HashMap<>();

		resultMap = registerService.registration(registerdata);

		System.out.println("result from register service" + resultMap);
		if(resultMap.get("response").equals("Authorized")){

			map.put("smartmeter", resultMap.get("smartMeterID"));
			return new ResponseEntity<>(map, HttpStatus.OK);

		}else if (resultMap.get("response").equals("Unauthorized")){

			map.put("response", "Unauthorized");
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}else{
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}
	}		
	
}




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
		System.out.println("RegisterationCheck");
		System.out.println("First Name :" + registerdata.getFirstName());
		System.out.println("Last Name: " + registerdata.getLastName());
		System.out.println("HouseholdID : "+ registerdata.getHouseholdID());
		System.out.println("SmartmeterID : "+ registerdata.getSmartmterID());
		System.out.println("ZipCode : "+ registerdata.getZipCode());
		System.out.println("Password : "+ registerdata.getPassword());
		System.out.println("ConfirmPassword : "+ registerdata.getConfirmPassword());
		
		Map<String, String> resultMap = new HashMap<>();
		Map<String, Object> map = new HashMap<>();

		resultMap = registerService.registration(registerdata);

		System.out.println("result from register service" + resultMap);

		if (!resultMap.get("username").toString().equals("0")&& !resultMap.get("firstname").toString().equals("0")&& !resultMap.get("lastname").toString().equals("0")&& !resultMap.get("zipcode").toString().equals("0")&&!resultMap.get("email").toString().equals("0")&&!resultMap.get("password").toString().equals("0")&&!resultMap.get("confirmpassword").toString().equals("0")&& !resultMap.get("houseHoldId").toString().equals("0")) {

//			registerdata.setOid(Integer.parseInt(resultMap.get("oid")));

			System.out.println("the result is " + resultMap.get("oid"));

			map.put("username", registerdata.getUsername());
			map.put("firstname", registerdata.getUsername());
			map.put("lastname", registerdata.getUsername());
			
			return new ResponseEntity<>(map, HttpStatus.OK);
		} else if (!resultMap.get("username").toString().equals("0")&& !resultMap.get("firstname").toString().equals("0")&& !resultMap.get("lastname").toString().equals("0")&& !resultMap.get("zipcode").toString().equals("0")&&!resultMap.get("email").toString().equals("0")&&!resultMap.get("password").toString().equals("0")&&!resultMap.get("confirmpassword").toString().equals("0")
				&& resultMap.get("houseHoldId").toString().equals("0")) {

			map.put("username", registerdata.getUsername());
			map.put("response", "NoHouseHoldId");

			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}else if (resultMap.get("username").toString().equals("0")&& !resultMap.get("firstname").toString().equals("0")&& !resultMap.get("lastname").toString().equals("0")&& !resultMap.get("zipcode").toString().equals("0")&&!resultMap.get("email").toString().equals("0")&&!resultMap.get("password").toString().equals("0")&&!resultMap.get("confirmpassword").toString().equals("0")
				&& !resultMap.get("houseHoldId").toString().equals("0")) {
			map.put("response", "NoUsername");
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}else if (!resultMap.get("username").toString().equals("0")&& resultMap.get("firstname").toString().equals("0")&& !resultMap.get("lastname").toString().equals("0")&& !resultMap.get("zipcode").toString().equals("0")&&!resultMap.get("email").toString().equals("0")&&!resultMap.get("password").toString().equals("0")&&!resultMap.get("confirmpassword").toString().equals("0")
				&& !resultMap.get("houseHoldId").toString().equals("0")) {
			map.put("response", "NoFirstname");
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}else if (!resultMap.get("username").toString().equals("0")&& !resultMap.get("firstname").toString().equals("0")&& resultMap.get("lastname").toString().equals("0")&& !resultMap.get("zipcode").toString().equals("0")&&!resultMap.get("email").toString().equals("0")&&!resultMap.get("password").toString().equals("0")&&!resultMap.get("confirmpassword").toString().equals("0")
				&& !resultMap.get("houseHoldId").toString().equals("0")) {
			map.put("response", "Nolastname");
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}else if (!resultMap.get("username").toString().equals("0")&& !resultMap.get("firstname").toString().equals("0")&& !resultMap.get("lastname").toString().equals("0")&& resultMap.get("zipcode").toString().equals("0")&&!resultMap.get("email").toString().equals("0")&&!resultMap.get("password").toString().equals("0")&&!resultMap.get("confirmpassword").toString().equals("0")
				&& !resultMap.get("houseHoldId").toString().equals("0")) {
			map.put("response", "Nozipcode");
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}else if (!resultMap.get("username").toString().equals("0")&& !resultMap.get("firstname").toString().equals("0")&& !resultMap.get("lastname").toString().equals("0")&& !resultMap.get("zipcode").toString().equals("0")&&resultMap.get("email").toString().equals("0")&&!resultMap.get("password").toString().equals("0")&&!resultMap.get("confirmpassword").toString().equals("0")
				&& !resultMap.get("houseHoldId").toString().equals("0")) {
			map.put("response", "Noemailid");
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}else if (!resultMap.get("username").toString().equals("0")&& !resultMap.get("firstname").toString().equals("0")&& !resultMap.get("lastname").toString().equals("0")&& !resultMap.get("zipcode").toString().equals("0")&&!resultMap.get("email").toString().equals("0")&&resultMap.get("password").toString().equals("0")&&!resultMap.get("confirmpassword").toString().equals("0")
				&& !resultMap.get("houseHoldId").toString().equals("0")) {
			map.put("response", "Nopassword");
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}else if (!resultMap.get("username").toString().equals("0")&& !resultMap.get("firstname").toString().equals("0")&& !resultMap.get("lastname").toString().equals("0")&& !resultMap.get("zipcode").toString().equals("0")&&!resultMap.get("email").toString().equals("0")&&!resultMap.get("password").toString().equals("0")&&resultMap.get("confirmpassword").toString().equals("0")
				&& !resultMap.get("houseHoldId").toString().equals("0")) {
			map.put("response", "Noconfirmpassword");
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);
		}
		else {
			map.put("username", registerdata.getUsername());
			map.put("response", "Unauthorized");
			return new ResponseEntity<>(map, HttpStatus.UNAUTHORIZED);

		}

	}
}



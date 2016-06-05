package com.wcp.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
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
import com.wcp.datamodel.ReadingData;
import com.wcp.model.User;
import com.wcp.service.HistogramService;

@Controller
public class HomeController {

	@Autowired
	HistogramService histogramService;

	@RequestMapping(value = "/histogram", method = RequestMethod.POST)
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<Map<String, Object>> histogramData(@RequestBody User user)
			throws MySQLIntegrityConstraintViolationException, SQLException, Exception {
		Map<String, Object> map = new HashMap<>();
		List<ReadingData> meterReadingList = histogramService.dailyComsumption(user);

		map.put("result", meterReadingList);

		System.out.println(map);

		return new ResponseEntity<>(map, HttpStatus.OK);

	}

}
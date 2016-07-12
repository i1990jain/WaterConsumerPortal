package com.wcp.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.wcp.datamodel.DistrictData;
import com.wcp.datamodel.NewReading;
import com.wcp.datamodel.ReadingData;
import com.wcp.datamodel.UserData;
import com.wcp.model.User;
import com.wcp.service.DatabaseService;
import com.wcp.service.HistogramService;
import com.wcp.service.HomeService;

@Controller
public class HomeController {

	@Autowired
	HistogramService histogramService;

	@Autowired
	HomeService homeService;

	@Autowired
	DatabaseService databaseService;

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

	@RequestMapping(value = "/userdata", method = RequestMethod.POST)
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<Map<String, Object>> userData(@RequestBody User user)
			throws MySQLIntegrityConstraintViolationException, SQLException, Exception {
		Map<String, Object> map = new HashMap<>();
		UserData userData = homeService.getUserData(user);

		map.put("result", userData);

		System.out.println(map);

		return new ResponseEntity<>(map, HttpStatus.OK);

	}

	@RequestMapping(value = "/nbhavg", method = RequestMethod.POST)
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<Map<String, Object>> nbhAverage(@RequestBody DistrictData districtData)
			throws MySQLIntegrityConstraintViolationException, SQLException, Exception {
		Map<String, Object> map = new HashMap<>();
		Double result = databaseService.averageComsumption(districtData.getZipcode(), districtData.getDate());

		map.put("result", result);

		return new ResponseEntity<>(map, HttpStatus.OK);

	}

	@RequestMapping(value = "/addreading", method = RequestMethod.POST)
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<Map<String, Object>> nbhAverage(@RequestBody NewReading readingData)
			throws MySQLIntegrityConstraintViolationException, SQLException, Exception {
		Map<String, Object> map = new HashMap<>();
		Map<String, String> resultMap = new HashMap<>();
		System.out.println(readingData.getReadingDateTime());
		System.out.println(readingData.getTotalConsumptionAdjusted());
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
		DateTime dt = formatter.parseDateTime(readingData.getReadingDateTime());
		resultMap = databaseService.addReading(readingData.getOid(), dt,
				Double.valueOf(readingData.getTotalConsumptionAdjusted()));

		map.put("result", resultMap);

		return new ResponseEntity<>(map, HttpStatus.OK);

	}

}

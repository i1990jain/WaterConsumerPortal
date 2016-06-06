package com.wcp.service;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.wcp.model.User;
import com.wcp.datamodel.RegisterData;

@Service("registerService")
@Transactional
public class RegisterService {

	public Map<String, String> registration(RegisterData registerdata)
			throws MySQLIntegrityConstraintViolationException, SQLException, Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		String smartMeterID="" ;
		Integer houseHoldId = 0;
		Map<String, String> map = new HashMap<>();

		try {
			session.beginTransaction();
			System.out.println("RegisterationCheck");
			System.out.println("First Name :" + registerdata.getFirstName());
			System.out.println("Last Name: " + registerdata.getLastName());
			System.out.println("HouseholdID : "+ registerdata.getHouseholdID());
		//	System.out.println("SmartmeterID : "+ registerdata.getSmartmterID());
			System.out.println("ZipCode : "+ registerdata.getZipCode());
			System.out.println("Password : "+ registerdata.getPassword());
			System.out.println("ConfirmPassword : "+ registerdata.getConfirmPassword());

			String smartMeterCheck = "select smart_meter_id from smart_meter where (oid = (select smart_meter_oid from household where oid =householdid ) AND (building_oid = (select building_oid from household where oid =householdid )";

			System.out.println(smartMeterCheck);
			

			Query query = session.createQuery(smartMeterCheck);

			query.setParameter("householdid",registerdata.getHouseholdID());

			List result = query.list();
			System.out.println(result);
			System.out.println("resultset:" + result);

			Iterator iterator = result.iterator();
			while (iterator.hasNext()) {
				smartMeterID = (String) iterator.next();
				
			}
			registerdata.setSmartmeterID(smartMeterID);

			if (smartMeterID != null) {

				String register = "Insert into user(oid,username,email,firstname,lastname,password) values (oid,username,email,firstname,lastname,password)";

				System.out.println(register);
				
				Query registerquery = session.createQuery(register);

				registerquery.setParameter("oid",registerdata.getOid());
				registerquery.setParameter("username",registerdata.getUserName());
				registerquery.setParameter("email",registerdata.getEmail());
				registerquery.setParameter("firstname",registerdata.getFirstName());
				registerquery.setParameter("lastname",registerdata.getLastName());
				registerquery.setParameter("password",registerdata.getPassword());
				
				map.put("smartMeterID", smartMeterID);
				map.put("response", "Authorized");
				
				
			}else{
				map.put("response", "Unauthorized");
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			session.close();
		}
		return map;
	}
}


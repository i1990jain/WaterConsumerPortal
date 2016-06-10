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
import com.wcp.model.Household;
import com.wcp.model.NeutralUser;
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
		//	Integer houseHoldId = 0;
		Map<String, String> map = new HashMap<>();

		try {
			session.beginTransaction();
			System.out.println("RegisterationCheckService");
			System.out.println("User Name :" + registerdata.getUserName());
			System.out.println("First Name :" + registerdata.getFirstName());
			System.out.println("Last Name: " + registerdata.getLastName());
			System.out.println("HouseholdID : "+ registerdata.getHouseholdID());

			//	System.out.println("SmartmeterID : "+ registerdata.getSmartmterID());
			System.out.println("ZipCode : "+ registerdata.getZipCode());
			System.out.println("Password : "+ registerdata.getPassword());

			if(registerdata.getHouseholdID()!=0){

				String smartMeterCheck = "select smartMeterId from SmartMeter where (oid = (select smartMeter.oid from Household where oid=:householdOid))";

				System.out.println(smartMeterCheck);


				Query query = session.createQuery(smartMeterCheck);

				query.setParameter("householdOid",registerdata.getHouseholdID());

				List result = query.list();
				System.out.println(result);
				System.out.println("resultset:" + result);

				Iterator iterator = result.iterator();
				while (iterator.hasNext()) {
					smartMeterID = (String) iterator.next();

				}
				System.out.println(smartMeterID);

				registerdata.setSmartmeterID(smartMeterID);

				if (smartMeterID != null) {
					int oid = 0;
					String selectoid ="SELECT oid FROM User ORDER BY oid DESC";


					System.out.println(selectoid);
					Query selectoidquery = session.createQuery(selectoid);
					selectoidquery.setMaxResults(1);

					List result1 = selectoidquery.list();
					System.out.println(result1);
					System.out.println("resultset:" + result1);

					Iterator iterator1 = result1.iterator();
					while (iterator1.hasNext()) {
						oid = (Integer) iterator1.next();

					}
					oid =oid+1;

					System.out.println(oid);

					User user=new User();
					NeutralUser neutraluser = new NeutralUser();
					Household houseHold = new Household();


					user.setOid(oid);
					user.setUsername(registerdata.getUserName());
					user.setFirstName(registerdata.getFirstName());
					user.setLastName(registerdata.getLastName());
					user.setEmail(registerdata.getEmail());
					user.setPassword(registerdata.getPassword());
					session.save(user);

					//Commit the transaction
					session.getTransaction().commit();

					System.out.println("insert into user table completed");
					System.out.println(oid);

					/*		 //Neutral User    
		  						neutraluser.setUserOid(oid);		
										user.setOid(oid);
								houseHold.setOid(registerdata.getHouseholdID());
								neutraluser.setUser(user);



		  neutraluser.setHousehold(houseHold);		 
			session.save(neutraluser);
			//Commit the transaction
				 session.getTransaction().commit();

					 */				System.out.println("Insert completed");

					 map.put("smartMeterID", smartMeterID);
					 map.put("response", "Authorized");


				}else{
					map.put("response", "Unauthorized");

				}
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


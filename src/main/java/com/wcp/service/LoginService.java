package com.wcp.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.wcp.model.User;
import com.wcp.model.SmartMeter;
@Service("loginService")
@Transactional
public class LoginService {

	public Map<String, String> userLogin(User user)
			throws MySQLIntegrityConstraintViolationException, SQLException, Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		Integer userId = 0;
		Integer houseHoldId = 0;
		String smartMeterID="" ;
		Map<String, String> map = new HashMap<>();

		try {
			session.beginTransaction();
			String userLoginCheck = "select oid from User where username=:username and password=:password";

			System.out.println(userLoginCheck);
			System.out.println("username: " + user.getUsername());
			System.out.println("password: " + user.getPassword());
			Query query = session.createQuery(userLoginCheck);

			query.setParameter("username", user.getUsername());
			query.setParameter("password", user.getPassword());
			List result = query.list();
			System.out.println(result);
			System.out.println("resultset:" + result);

			Iterator iterator = result.iterator();
			while (iterator.hasNext()) {
				userId = (int) iterator.next();

			}

			if (userId != 0) {

				String householdCheck = "select household.oid from NeutralUser where user_oid=:userId";

				System.out.println(householdCheck);
				System.out.println("userId: " + userId);

				Query householdQuery = session.createQuery(householdCheck);

				householdQuery.setParameter("userId", userId);

				List houseHoldResult = householdQuery.list();

				System.out.println(houseHoldResult);
				System.out.println("HouseHoldresultset:" + houseHoldResult);

				for (Object id : houseHoldResult) {
					if (id != null) {
						houseHoldId = (int) id;
					}
				}

				/*
				 * if (houseHoldResult != null) { Iterator iterate =
				 * houseHoldResult.iterator(); while (iterate.hasNext()) {
				 * 
				 * } }
				 */
			}

			map.put("userId", userId.toString());
			map.put("houseHoldId", houseHoldId.toString());

	//Check for smartmeterId

			System.out.println("Check for smartMeterID");
			String smartMeterCheck = "select smartMeterId from SmartMeter where (oid = (select smartMeter.oid from Household where oid=:householdOid))";
			System.out.println(smartMeterCheck);
			Query smartmetercheckquery = session.createQuery(smartMeterCheck);
			query.setParameter("householdOid",houseHoldId);
			List result1 = query.list();
			System.out.println(result1);
			System.out.println("resultset:" + result1);
			Iterator iterator1 = result.iterator();
			while (iterator1.hasNext()) {
				smartMeterID = (String) iterator.next();
			}
			if (!smartMeterID.isEmpty()) {
				map.put("smartmeterId",smartMeterID);
			}else{
				map.put("response","NoSmartMeterid");

			}	
			System.out.println(smartMeterID);

			/////////////////////////////////////////////////////////////////////////////////////////////////////////
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			session.close();
		}
		return map;
	}
}

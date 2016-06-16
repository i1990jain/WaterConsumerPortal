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
import com.wcp.datamodel.RegisterData;
import com.wcp.model.Household;
import com.wcp.model.NeutralUser;
import com.wcp.model.User;

@Service("registerService")
@Transactional
public class RegisterService {

	public Map<String, String> registration(RegisterData registerdata)
			throws MySQLIntegrityConstraintViolationException, SQLException, Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		String smartMeterID = "";
		int householdid = 0;
		int a = 1;
		Map<String, String> map = new HashMap<>();

		try {
			session.beginTransaction();
			System.out.println("RegisterationCheckService");

			System.out.println("User Name :" + registerdata.getUserName());
			System.out.println("First Name :" + registerdata.getFirstName());
			System.out.println("Last Name: " + registerdata.getLastName());
			System.out.println("HouseholdID : " + registerdata.getHouseholdID());
			System.out.println("ZipCode : " + registerdata.getZipCode());
			System.out.println("Password : " + registerdata.getPassword());

			if ((registerdata.getHouseholdID() != 0)) {
				// Check for Household Id is valid or not
				String householdidcheck = "select oid from Household";
				System.out.println(householdidcheck);

				Query householdcheckquery = session.createQuery(householdidcheck);
				List householdresult = householdcheckquery.list();
				System.out.println(householdresult);
				System.out.println("resultset:" + householdresult);

				Iterator iterator = householdresult.iterator();
				while (iterator.hasNext()) {
					householdid = (Integer) iterator.next();
					if (householdid == registerdata.getHouseholdID()) {
						a = 0;
						break;
					} else {
						a = 1;
					}
				}

				// if householdid is valid register User

				if (a == 0) {
					int oid = 0;
					String selectoid = "SELECT oid FROM User ORDER BY oid DESC";
					System.out.println(selectoid);
					Query selectoidquery = session.createQuery(selectoid);
					selectoidquery.setMaxResults(1);
					List result1 = selectoidquery.list();
					System.out.println(result1);
					System.out.println("resultset:" + result1);
					Iterator iterator2 = result1.iterator();
					while (iterator2.hasNext()) {
						oid = (Integer) iterator2.next();
					}
					oid = oid + 1;
					System.out.println(oid);
					User user = new User();
					NeutralUser neutraluser = new NeutralUser();
					Household houseHold = new Household();
					houseHold.setOid(registerdata.getHouseholdID());
					user.setOid(oid);
					user.setUsername(registerdata.getUserName());
					user.setFirstName(registerdata.getFirstName());
					user.setLastName(registerdata.getLastName());
					user.setEmail(registerdata.getEmail());
					user.setPassword(registerdata.getPassword());

					session.save(user);
					neutraluser.setUser(user);
					neutraluser.setHousehold(houseHold);
					session.save(neutraluser);
					// Commit the transaction
					session.getTransaction().commit();

					System.out.println("Insert completed");

					map.put("response", "Authorized");

				} else {
					map.put("response", "Unauthorized");
					session.flush();

				}

			} else {
				map.put("repsonse", "No HouseholdId found");
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

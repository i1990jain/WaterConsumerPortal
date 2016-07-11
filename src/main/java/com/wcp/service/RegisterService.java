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
import com.wcp.model.ConsumerSegment;
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

		String smartMeterId = "";
		int buidlingID = 0;
		int householdid = 0;
		int checked = 0;
		User user = new User();
		NeutralUser neutraluser = new NeutralUser();
		Household houseHold = new Household();
		ConsumerSegment consumersegment = new ConsumerSegment();
		Map<String, String> map = new HashMap<>();

		try {
			session.beginTransaction();
			System.out.println("RegisterationCheckService");

			System.out.println("User Name :" + registerdata.getUserName());
			System.out.println("First Name :" + registerdata.getFirstName());
			System.out.println("Last Name: " + registerdata.getLastName());
			System.out.println("HouseholdID : " + registerdata.getHouseholdID());
			System.out.println("SmartMeter : " + registerdata.isSmartmeter());
			System.out.println("ZipCode : " + registerdata.getZipCode());
			System.out.println("Password : " + registerdata.getPassword());

			// Check for Household Id is valid or not
			String householdidcheck = "select oid from Household where oid =:household";
			System.out.println(householdidcheck);

			Query householdcheckquery = session.createQuery(householdidcheck);
			householdcheckquery.setParameter("household", registerdata.getHouseholdID());
			List householdresult = householdcheckquery.list();
			System.out.println(householdresult);
			System.out.println("resultset:" + householdresult);
			Iterator iterator = householdresult.iterator();
			while (iterator.hasNext()) {
				householdid = (Integer) iterator.next();
				/*
				 * if (householdid == registerdata.getHouseholdID()) { a = 0;
				 * break; } else { a = 1; }
				 */
			}

			// if householdID is valid register User
			if (householdid != 0) {
				// if the smartMeter is being not being checked.Check for
				// smartMeter and building Id before registration.

				if (!registerdata.isSmartmeter()) {
					// Check for smartmeterId
					System.out.println("Check for smartMeterID");
					String smartMeterCheck = "select smartMeterId from SmartMeter where (oid = (select smartMeter.oid from Household where oid=:householdOid))";
					System.out.println(smartMeterCheck);
					Query smartmetercheckquery = session.createQuery(smartMeterCheck);
					smartmetercheckquery.setParameter("householdOid", householdid);
					List result1 = smartmetercheckquery.list();
					System.out.println(result1);
					System.out.println("resultset:" + result1);
					Iterator iterator1 = result1.iterator();
					while (iterator1.hasNext()) {
						smartMeterId = (String) iterator1.next();
					}
					if (!smartMeterId.isEmpty()) {
						map.put("response", "SmartMeterIDFound");

						// Check for buildingID
						System.out.println("Check for BuildingID");
						String buildingIDCheck = "select building.oid from Household where oid=:householdOid";
						System.out.println(buildingIDCheck);
						Query buildingIDCheckQuery = session.createQuery(buildingIDCheck);
						buildingIDCheckQuery.setParameter("householdOid", householdid);
						List result2 = buildingIDCheckQuery.list();
						System.out.println(result2);
						System.out.println("resultset:" + result2);
						Iterator iterator2 = result2.iterator();
						while (iterator2.hasNext()) {
							buidlingID = (Integer) iterator2.next();
						}

						if (buidlingID == 0) {
							map.put("responsemsg",
									"No buildingIDFound.Please click the Checkbox to Register as Non-Metered User");
							map.put("response", "Unauthorized");
							checked = 0;
						} else {
							map.put("response", "BuidlingIDFound");
							checked = 1;
						}
						System.out.println(buidlingID);
					} else {
						map.put("responsemsg",
								"NoSmartMeterIDFound.Please click the Checkbox to Register as Non-Metered User");
						map.put("response", "Unauthorized");
						checked = 0;
					}
				} else {
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

					houseHold.setOid(registerdata.getHouseholdID());
					user.setOid(oid);
					user.setUsername(registerdata.getUserName());
					user.setFirstName(registerdata.getFirstName());
					user.setLastName(registerdata.getLastName());
					user.setEmail(registerdata.getEmail());
					user.setPassword(registerdata.getPassword());

					session.save(user);
					// Update the NeutralUsertable

					neutraluser.setUser(user);
					neutraluser.setHousehold(houseHold);
					session.save(neutraluser);
					// Update the consumer-segment
					consumersegment.setOid(oid);
					consumersegment.setName(registerdata.getFirstName());
					consumersegment.setDescription("Non-MeteredUser");
					session.save(consumersegment);

					// Commit the transaction
					session.getTransaction().commit();

					System.out.println("Insert completed");
					// Insert(registerdata);
					map.put("response", "Authorized");
				}

				if (checked == 1) {
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

					houseHold.setOid(registerdata.getHouseholdID());
					user.setOid(oid);
					user.setUsername(registerdata.getUserName());
					user.setFirstName(registerdata.getFirstName());
					user.setLastName(registerdata.getLastName());
					user.setEmail(registerdata.getEmail());
					user.setPassword(registerdata.getPassword());

					session.save(user);
					// Update the NeutralUsertable
					neutraluser.setUser(user);
					neutraluser.setHousehold(houseHold);
					session.save(neutraluser);

					// Commit the transaction
					session.getTransaction().commit();

					System.out.println("Insert completed");
					// Insert(registerdata);
					map.put("response", "Authorized");
				}
			} else {
				map.put("responsemsg", "No HouseholdId found.Put a Valid Household ID");
				map.put("response", "Unauthorized");
				session.flush();

			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			session.close();
		}
		return map;
	}
	/*
	 * void Insert(RegisterData registerdata){ SessionFactory sessionFactory =
	 * HibernateUtil.getSessionFactory();
	 * 
	 * Session session1 = sessionFactory.openSession();
	 * 
	 * int oid = 0; String selectoid = "SELECT oid FROM User ORDER BY oid DESC";
	 * System.out.println(selectoid); Query selectoidquery =
	 * session1.createQuery(selectoid); selectoidquery.setMaxResults(1); List
	 * result1 = selectoidquery.list(); System.out.println(result1);
	 * System.out.println("resultset:" + result1); Iterator iterator2 =
	 * result1.iterator(); while (iterator2.hasNext()) { oid = (Integer)
	 * iterator2.next(); } oid = oid + 1; System.out.println(oid); User user =
	 * new User(); NeutralUser neutraluser = new NeutralUser(); Household
	 * houseHold = new Household();
	 * houseHold.setOid(registerdata.getHouseholdID()); user.setOid(oid);
	 * user.setUsername(registerdata.getUserName());
	 * user.setFirstName(registerdata.getFirstName());
	 * user.setLastName(registerdata.getLastName());
	 * user.setEmail(registerdata.getEmail());
	 * user.setPassword(registerdata.getPassword());
	 * 
	 * session1.save(user); neutraluser.setUser(user);
	 * neutraluser.setHousehold(houseHold); session1.save(neutraluser); //Update
	 * the consumer-segment consumersegment.setOid(oid);
	 * consumersegment.setName(registerdata.getFirstName());
	 * consumersegment.setDescription("Non-MeteredUser");
	 * session.save(consumersegment); // Commit the transaction
	 * session1.getTransaction().commit();
	 * 
	 * System.out.println("Insert completed"); }
	 */
}

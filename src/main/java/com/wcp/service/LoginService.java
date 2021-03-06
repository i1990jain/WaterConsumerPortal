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
import com.wcp.datamodel.MeterData;
import com.wcp.model.Building;
import com.wcp.model.Household;
import com.wcp.model.SmartMeter;
import com.wcp.model.User;

@Service("loginService")
@Transactional
public class LoginService {

	public Map<String, String> userLogin(User user)
			throws MySQLIntegrityConstraintViolationException, SQLException, Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		Integer userId = 0;
		Integer houseHoldId = 0;
		String smartMeterId = "";
		int oid = 0;
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
				// Check for smartmeterId

				/*
				 * System.out.println("Check for smartMeterID"); String
				 * smartMeterCheck =
				 * "select smartMeterId from SmartMeter where (oid = (select smartMeter.oid from Household where oid=:householdOid))"
				 * ; System.out.println(smartMeterCheck); Query
				 * smartmetercheckquery = session.createQuery(smartMeterCheck);
				 * smartmetercheckquery.setParameter("householdOid",houseHoldId)
				 * ; List result1 = smartmetercheckquery.list();
				 * System.out.println(result1); System.out.println("resultset:"
				 * + result1); Iterator iterator1 = result1.iterator(); while
				 * (iterator1.hasNext()) { smartMeterId = (String)
				 * iterator1.next(); } if (!smartMeterId.isEmpty()) {
				 * map.put("response","SmartMeterIDFound"); }else{
				 * map.put("response","NoSmartMeterIDFound");
				 * 
				 * } System.out.println(smartMeterId);
				 */

				// Check for Non-Metered user
				System.out.println("Check for Non-meteredUser");
				String NonMeterUserCheck = "select oid from ConsumerSegment where oid =:userId ";
				System.out.println(NonMeterUserCheck);
				Query NonMeterUserCheckquery = session.createQuery(NonMeterUserCheck);
				NonMeterUserCheckquery.setParameter("userId", userId);
				List result2 = NonMeterUserCheckquery.list();
				System.out.println(result2);
				System.out.println("resultset:" + result2);
				Iterator iterator2 = result2.iterator();
				while (iterator2.hasNext()) {
					oid = (Integer) iterator2.next();
				}
				if (oid != 0) {
					System.out.println("Non-meteredUser found");
					map.put("response", "Non-MeteredUserFound");
				} else {
					System.out.println("MeteredUser found");
					map.put("response", "MeteredUserFound");
				}
			}
			System.out.println(houseHoldId);
			map.put("userId", userId.toString());
			map.put("houseHoldId", houseHoldId.toString());

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			session.close();
		}
		return map;
	}

	public Map<String, String> addmeterid(MeterData meterdata)
			throws MySQLIntegrityConstraintViolationException, SQLException, Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		Map<String, String> map = new HashMap<>();
		Integer householdid = null;
		Integer buildingid = null;
		try {
			session.beginTransaction();

			String gethouseholdID = "select household.oid from NeutralUser where userOid=:userOid";

			System.out.println(gethouseholdID);

			Query householdquery = session.createQuery(gethouseholdID);

			householdquery.setParameter("userOid", meterdata.getOid());

			List result = householdquery.list();
			System.out.println(result);
			System.out.println("resultset:" + result);

			Iterator iterator = result.iterator();
			while (iterator.hasNext()) {
				householdid = (int) iterator.next();

			}

			if (householdid != 0) {

				String getbuildingID = "select building.oid from Household where oid=:oid";

				System.out.println(getbuildingID);

				Query getbuildingIDquery = session.createQuery(getbuildingID);

				getbuildingIDquery.setParameter("oid", householdid);

				List buildingresult = getbuildingIDquery.list();
				System.out.println(buildingresult);
				System.out.println("resultset:" + buildingresult);

				Iterator buildingiterator = buildingresult.iterator();
				while (buildingiterator.hasNext()) {
					buildingid = (int) buildingiterator.next();

				}

				if (buildingid != 0) {
					int oid = 0;
					String selectoid = "SELECT oid FROM SmartMeter ORDER BY oid DESC";
					System.out.println(selectoid);
					Query selectoidquery = session.createQuery(selectoid);
					selectoidquery.setMaxResults(1);
					List result12 = selectoidquery.list();
					System.out.println(result12);
					System.out.println("resultset:" + result12);
					Iterator iterator2 = result12.iterator();
					while (iterator2.hasNext()) {
						oid = (Integer) iterator2.next();
					}
					oid = oid + 1;
					System.out.println(oid);

					SmartMeter obj = new SmartMeter();
					Building building = new Building();
					building.setOid(buildingid);
					obj.setBuilding(building);
					obj.setOid(oid);
					obj.setSmartMeterId(meterdata.getMeterid());

					session.save(obj);

					Household household = new Household();
					household.setBuilding(building);
					household.setSmartMeter(obj);
					household.setOid(householdid);

					session.update(household);

					session.getTransaction().commit();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			session.close();
		}

		return map;

	}
}

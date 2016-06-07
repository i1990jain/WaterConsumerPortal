package com.wcp.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wcp.datamodel.UserData;
import com.wcp.model.User;

@Service("homeService")
@Transactional
public class HomeService {

	public UserData getUserData(User user) {
		UserData userData = new UserData();
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		int houseHoldId = 0;
		int smartMeterOid = 0;
		Long consTypecount = null;
		int buildingId = 0;
		try {

			session.beginTransaction();

			String householdCheck = "select household.oid from NeutralUser where user_oid=:userId";

			Query householdQuery = session.createQuery(householdCheck);

			householdQuery.setParameter("userId", user.getOid());

			List houseHoldResult = householdQuery.list();

			for (Object id : houseHoldResult) {
				if (id != null) {
					houseHoldId = (int) id;
				}
			}

			// set houseHoldID
			userData.setHouseHoldId(Integer.toString(houseHoldId));
			System.out.println(userData.getHouseHoldId());

			String smartMeter = "select smartMeter.oid from Household where oid=:householdOid";

			Query smartMeterOidQuery = session.createQuery(smartMeter);

			smartMeterOidQuery.setParameter("householdOid", houseHoldId);

			List smartMeterOidQueryResult = smartMeterOidQuery.list();

			for (Object id : smartMeterOidQueryResult) {
				if (id != null) {
					smartMeterOid = (int) id;
				}
			}

			// setSmartMeterId
			userData.setSmartMeterId(Integer.toString(smartMeterOid));
			System.out.println(userData.getSmartMeterId());

			String consType = "select count(*) from Household where smart_meter_oid=:smartmeteroid";

			Query consTypeQuery = session.createQuery(consType);

			consTypeQuery.setParameter("smartmeteroid", userData.getSmartMeterId());

			List consTypeQueryResult = consTypeQuery.list();

			for (Object id : consTypeQueryResult) {
				if (id != null) {
					consTypecount = (Long) id;
				}
			}

			if (consTypecount > 1) {
				// setConsumptionType
				userData.setConsumptionType("common");
				System.out.println(userData.getConsumptionType());
			} else {
				// setConsumptionType
				userData.setConsumptionType("individual");
				System.out.println(userData.getConsumptionType());
			}

			String buildingSelect = "select building.oid from Household where oid=:householdOid";

			Query buildingSelectQuery = session.createQuery(buildingSelect);

			buildingSelectQuery.setParameter("householdOid", houseHoldId);

			List buildingSelectResult = buildingSelectQuery.list();

			for (Object id : buildingSelectResult) {
				if (id != null) {
					buildingId = (int) id;
				}
			}

			// setBuildingId
			userData.setBuildingId(Integer.toString(buildingId));
			System.out.println(userData.getBuildingId());

			String zipCountrySelect = "select d.zipcode,d.country from District d where oid=(select district.oid from Building where oid=:buildingoid)";

			Query zipCountrySelectQuery = session.createQuery(zipCountrySelect);

			zipCountrySelectQuery.setParameter("buildingoid", buildingId);

			List<Object[]> zipCountrySelectResult = zipCountrySelectQuery.list();

			for (Object[] object : zipCountrySelectResult) {

				// setzipCountry
				userData.setZipcode((String) object[0]);
				userData.setCountry((String) object[1]);

				System.out.println(object[0]);
				System.out.println(object[1]);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			session.close();
		}
		return userData;
	}

}

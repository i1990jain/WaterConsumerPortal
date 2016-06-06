package com.wcp.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.wcp.datamodel.ReadingData;
import com.wcp.model.MeterReading;
import com.wcp.model.User;

@Service("histogramService")
@Transactional
public class HistogramService {

	public List<ReadingData> dailyComsumption(User user)
			throws MySQLIntegrityConstraintViolationException, SQLException, Exception {
		System.out.println("useroID: " + user);
		// select household_oid from neutral_user where user_oid=50;
		int houseHoldId = getHouseholdId(user);
		System.out.println("householdID:  " + houseHoldId);
		// select smart_meter_oid from household where oid=290;
		int smartMeterId = getSmartMeterId(houseHoldId);
		System.out.println("smartMeterId:  " + smartMeterId);
		// select * from meter_reading where smart_meter_oid=31;

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		List<MeterReading> result = new ArrayList<MeterReading>();

		List<ReadingData> readingDataList = new ArrayList<ReadingData>();
		try {
			session.beginTransaction();
			String consumptionData = "from MeterReading m where smart_meter_oid =:smartMeterId ORDER BY m.readingDateTime";

			Query query = session.createQuery(consumptionData);

			query.setParameter("smartMeterId", smartMeterId);

			result = query.list();

			readingDataList = getReadingData(result);

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			session.close();
		}
		return readingDataList;
	}

	private List<ReadingData> getReadingData(List<MeterReading> input) {
		List<ReadingData> readingDataList = new ArrayList<ReadingData>();

		for (MeterReading reading : input) {
			ReadingData obj = new ReadingData();
			obj.setOid(reading.getOid());
			obj.setReadingDateTime(reading.getReadingDateTime());
			obj.setTotalConsumptionAdjusted(reading.getTotalConsumptionAdjusted());
			readingDataList.add(obj);
		}

		return readingDataList;
	}

	private int getSmartMeterId(int houseHoldId) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		int smartMeterOid = 0;
		try {

			session.beginTransaction();

			String smartMeter = "select smartMeter.oid from Household where oid=:householdOid";

			Query smartMeterOidQuery = session.createQuery(smartMeter);

			smartMeterOidQuery.setParameter("householdOid", houseHoldId);

			List smartMeterOidQueryResult = smartMeterOidQuery.list();

			for (Object id : smartMeterOidQueryResult) {
				if (id != null) {
					smartMeterOid = (int) id;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			session.close();
		}
		return smartMeterOid;

	}

	private int getHouseholdId(User user) {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		int houseHoldId = 0;
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

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			session.close();
		}
		return houseHoldId;
	}
}

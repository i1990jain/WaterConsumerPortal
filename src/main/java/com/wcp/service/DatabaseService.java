package com.wcp.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.wcp.model.MeterReading;
import com.wcp.model.User;

@Service("homePageService")
@Transactional

public class DatabaseService {
	// HomePage display
	// //////////////////////////////////////////////////////////////////////////////////

	public MeterReading dailyComsumption(User user)
			throws MySQLIntegrityConstraintViolationException, SQLException, Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		// int userId = 0;
		MeterReading meterReading = new MeterReading();
		try {
			session.beginTransaction();
			String sysDateConsumption = "select total_consumption from meter_reading where (reading_date_time = CURDATE()) AND oid = "
					+ user.getOid() + " order by reading_date_time; ";

			System.out.println(sysDateConsumption);
			Query query = session.createQuery(sysDateConsumption);

			List result = query.list();
			System.out.println(result);
			System.out.println("resultset:" + result);

			Iterator iterator = result.iterator();
			while (iterator.hasNext()) {
				BigDecimal a = (BigDecimal) iterator.next();
				meterReading.setTotalConsumption(a);

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			session.close();
		}
		return meterReading;
	}

	///////// According to Menu Button
	///////// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public MeterReading WeeklyAndMonthlyComsumption(User user, int b)
			throws MySQLIntegrityConstraintViolationException, SQLException, Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		// int userId = 0;
		MeterReading meterReading = new MeterReading();
		try {
			session.beginTransaction();
			String sysWeekConsumption = " SELECT * FROM meter_reading WHERE (DATE_SUB(CURDATE(), INTERVAL 7 DAY)) AND oid = "
					+ user.getOid() + " order by reading_date_time; ";
			String sysMonthConsumption = " SELECT * FROM meter_reading WHERE (DATE_SUB(CURDATE(), INTERVAL 1 MONTH)) AND oid = "
					+ user.getOid() + " order by reading_date_time; ";

			if (b == 1) {
				System.out.println(sysWeekConsumption);

				Query query = session.createQuery(sysWeekConsumption);

				List result = query.list();
				System.out.println(result);
				System.out.println("resultset:" + result);

				Iterator iterator = result.iterator();
				while (iterator.hasNext()) {
					BigDecimal a = (BigDecimal) iterator.next();
					meterReading.setTotalConsumption(a);
				}
			} else {
				System.out.println(sysMonthConsumption);

				Query query = session.createQuery(sysMonthConsumption);

				List result = query.list();
				System.out.println(result);
				System.out.println("resultset:" + result);

				Iterator iterator = result.iterator();
				while (iterator.hasNext()) {
					BigDecimal a = (BigDecimal) iterator.next();
					meterReading.setTotalConsumption(a);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			session.close();
		}
		return meterReading;
	}

	//////////// Average Consumption of the user and Average Consumption of the
	//////////// neighbourhood/////////////////////////////
	public MeterReading AverageComsumption(User user, int b)
			throws MySQLIntegrityConstraintViolationException, SQLException, Exception {

		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

		Session session = sessionFactory.openSession();

		// int userId = 0;
		MeterReading meterReading = new MeterReading();
		try {
			session.beginTransaction();
			String sysUserAvgConsumption = " SELECT * FROM meter_reading WHERE (DATE_SUB(CURDATE(), INTERVAL 7 DAY)) AND oid = "
					+ user.getOid() + " order by reading_date_time; ";
			String sysNeighAvgConsumption = " select * from meter_reading a JOIN household b where a.smart_meter_oid = b.smart_meter_oid AND b.building_oid IN (select building_oid from building where district_oid = (select district_oid from district where zipcode = 6652));";

			if (b == 1) {
				System.out.println(sysUserAvgConsumption);

				Query query = session.createQuery(sysUserAvgConsumption);

				List result = query.list();
				System.out.println(result);
				System.out.println("resultset:" + result);

				Iterator iterator = result.iterator();
				while (iterator.hasNext()) {
					BigDecimal a = (BigDecimal) iterator.next();
					meterReading.setTotalConsumption(a);
				}
			} else {
				System.out.println(sysNeighAvgConsumption);

				Query query = session.createQuery(sysNeighAvgConsumption);

				List result = query.list();
				System.out.println(result);
				System.out.println("resultset:" + result);

				Iterator iterator = result.iterator();
				while (iterator.hasNext()) {
					BigDecimal a = (BigDecimal) iterator.next();
					meterReading.setTotalConsumption(a);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			session.close();
		}
		return meterReading;
	}

}

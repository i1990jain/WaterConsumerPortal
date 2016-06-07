package com.wcp.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.wcp.datamodel.ReadingData;
import com.wcp.model.MeterReading;

@Service("databaseService")
@Transactional
public class DatabaseService {

	//////////// Average Consumption of the user and Average Consumption of the

	public Double averageComsumption(String zipcode)
			throws MySQLIntegrityConstraintViolationException, SQLException, Exception {
		Double nbhAverage = 0.0;
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		List result = new ArrayList();
		Session session = sessionFactory.openSession();

		// int userId = 0;
		MeterReading meterReading = new MeterReading();
		try {
			session.beginTransaction();
			List<Double> userAverage = new ArrayList();
			BigDecimal userTotal = new BigDecimal(0);
			Double nbhTotal = 0.0;

			// get smartmeter oid of buildings in the given zipcode
			String neighSmartmeters = " select sm.oid from SmartMeter sm where sm.building.oid IN (select blg.oid from Building blg where blg.district.oid IN (select oid from District where zipcode = :zipcode))";

			Query neighSmartmetersQuery = session.createQuery(neighSmartmeters);
			neighSmartmetersQuery.setParameter("zipcode", zipcode);
			result = neighSmartmetersQuery.list();

			for (Object id : result) {
				List<ReadingData> dailyreadingDataList = new ArrayList<ReadingData>();
				List<ReadingData> perDayDataList = new ArrayList<ReadingData>();
				String consumptionData = "from MeterReading m where smart_meter_oid =:smartMeterId ORDER BY m.readingDateTime";

				Query query = session.createQuery(consumptionData);

				query.setParameter("smartMeterId", id);

				List perDayresult = query.list();
				if (!perDayresult.isEmpty()) {
					dailyreadingDataList = getReadingData(perDayresult);

					perDayDataList = getAllReadings(dailyreadingDataList);

					for (ReadingData rd : perDayDataList) {

						userTotal = userTotal.add(rd.getTotalConsumptionAdjusted());

					}
					Double d = userTotal.doubleValue();
					userTotal = new BigDecimal(0);
					System.out.println(d / perDayDataList.size());
					userAverage.add(d / perDayDataList.size());

				}
			}

			for (Double val : userAverage) {
				nbhTotal = nbhTotal + val;
			}

			nbhAverage = nbhTotal / userAverage.size();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			session.close();
		}
		return nbhAverage;
	}

	private List<ReadingData> getAllReadings(List<ReadingData> readingDataList) {

		List<ReadingData> resultList = new ArrayList();
		int i = 0, j = 0;
		BigDecimal consumption;

		Date currentTempReadingDate = new Date();
		currentTempReadingDate = readingDataList.get(j).getReadingDateTime();

		String currentTempReadingDateString = String.valueOf(currentTempReadingDate.getDate())
				+ String.valueOf(currentTempReadingDate.getMonth()) + String.valueOf(currentTempReadingDate.getYear());

		Date nextTempReadingDate;
		String nextTempReadingDateString = null;

		for (ReadingData rd : readingDataList) {

			nextTempReadingDate = new Date();
			nextTempReadingDate = rd.getReadingDateTime();

			nextTempReadingDateString = String.valueOf(nextTempReadingDate.getDate())
					+ String.valueOf(nextTempReadingDate.getMonth()) + String.valueOf(nextTempReadingDate.getYear());

			if (!currentTempReadingDateString.equals(nextTempReadingDateString)) {
				consumption = readingDataList.get(i - 1).getTotalConsumptionAdjusted()
						.subtract(readingDataList.get(j).getTotalConsumptionAdjusted());

				ReadingData newrd = new ReadingData();
				newrd.setReadingDateTime(currentTempReadingDate);
				newrd.setTotalConsumptionAdjusted(consumption);

				resultList.add(newrd);
				j = i;
				currentTempReadingDate = new Date();
				currentTempReadingDate = readingDataList.get(i).getReadingDateTime();
				currentTempReadingDateString = String.valueOf(currentTempReadingDate.getDate())
						+ String.valueOf(currentTempReadingDate.getMonth())
						+ String.valueOf(currentTempReadingDate.getYear());

			}
			i++;

		}

		BigDecimal lastConsumption = new BigDecimal(0);
		j = 0;
		i = 0;
		for (ReadingData rd : readingDataList) {

			Date currentTempReadingDate1 = new Date();
			currentTempReadingDate1 = rd.getReadingDateTime();
			String currentTempReadingDateString1 = String.valueOf(currentTempReadingDate1.getDate())
					+ String.valueOf(currentTempReadingDate1.getMonth())
					+ String.valueOf(currentTempReadingDate1.getYear());

			if (j != readingDataList.size() - 1) {
				if (currentTempReadingDateString1.equals(nextTempReadingDateString)) {

					lastConsumption = lastConsumption.add(readingDataList.get(j + 1).getTotalConsumptionAdjusted()
							.subtract(readingDataList.get(i).getTotalConsumptionAdjusted()));

				}
			}
			j++;
			i++;

		}

		ReadingData newrd = new ReadingData();
		newrd.setReadingDateTime(readingDataList.get(j - 1).getReadingDateTime());
		newrd.setTotalConsumptionAdjusted(lastConsumption);

		resultList.add(newrd);
		return resultList;

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

}

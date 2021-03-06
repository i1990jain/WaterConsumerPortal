package com.wcp.model;
// Generated 21 May, 2016 11:31:35 PM by Hibernate Tools 5.1.0.Alpha1

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * MeterReading generated by hbm2java
 */
@Entity
@Table(name = "meter_reading", catalog = "smartmeter", uniqueConstraints = @UniqueConstraint(columnNames = {
		"reading_date_time", "smart_meter_oid" }))
public class MeterReading implements java.io.Serializable {

	private Integer oid;
	private SmartMeter smartMeter;
	private Date readingDateTime;
	private String company;
	private BigDecimal totalConsumption;
	private BigDecimal totalConsumptionAdjusted;

	public MeterReading() {
	}

	public MeterReading(SmartMeter smartMeter, Date readingDateTime, String company, BigDecimal totalConsumption,
			BigDecimal totalConsumptionAdjusted) {
		this.smartMeter = smartMeter;
		this.readingDateTime = readingDateTime;
		this.company = company;
		this.totalConsumption = totalConsumption;
		this.totalConsumptionAdjusted = totalConsumptionAdjusted;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "oid", unique = true, nullable = false)
	public Integer getOid() {
		return this.oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "smart_meter_oid")
	public SmartMeter getSmartMeter() {
		return this.smartMeter;
	}

	public void setSmartMeter(SmartMeter smartMeter) {
		this.smartMeter = smartMeter;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "reading_date_time", length = 0)
	public Date getReadingDateTime() {
		return this.readingDateTime;
	}

	public void setReadingDateTime(Date readingDateTime) {
		this.readingDateTime = readingDateTime;
	}

	@Column(name = "company")
	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Column(name = "total_consumption", scale = 3)
	public BigDecimal getTotalConsumption() {
		return this.totalConsumption;
	}

	public void setTotalConsumption(BigDecimal totalConsumption) {
		this.totalConsumption = totalConsumption;
	}

	@Column(name = "total_consumption_adjusted", scale = 3)
	public BigDecimal getTotalConsumptionAdjusted() {
		return this.totalConsumptionAdjusted;
	}

	public void setTotalConsumptionAdjusted(BigDecimal totalConsumptionAdjusted) {
		this.totalConsumptionAdjusted = totalConsumptionAdjusted;
	}

}

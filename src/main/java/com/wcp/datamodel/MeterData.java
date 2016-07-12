package com.wcp.datamodel;

import java.math.BigDecimal;
import java.util.Date;

public class MeterData {

	private Integer oid;

	private String meterid;

	private Date date;

	private BigDecimal totalConsumptionAdjusted;

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getMeterid() {
		return meterid;
	}

	public void setMeterid(String meterid) {
		this.meterid = meterid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getTotalConsumptionAdjusted() {
		return totalConsumptionAdjusted;
	}

	public void setTotalConsumptionAdjusted(BigDecimal totalConsumptionAdjusted) {
		this.totalConsumptionAdjusted = totalConsumptionAdjusted;
	}

}

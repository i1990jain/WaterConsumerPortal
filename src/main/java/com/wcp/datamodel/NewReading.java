package com.wcp.datamodel;

public class NewReading {

	private Integer oid;

	private String readingDateTime;

	private String totalConsumptionAdjusted;

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public String getReadingDateTime() {
		return readingDateTime;
	}

	public void setReadingDateTime(String readingDateTime) {
		this.readingDateTime = readingDateTime;
	}

	public String getTotalConsumptionAdjusted() {
		return totalConsumptionAdjusted;
	}

	public void setTotalConsumptionAdjusted(String totalConsumptionAdjusted) {
		this.totalConsumptionAdjusted = totalConsumptionAdjusted;
	}

}

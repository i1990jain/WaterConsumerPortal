package com.wcp.datamodel;

import java.math.BigDecimal;
import java.util.Date;

public class ReadingData {

	private Integer oid;

	private Date readingDateTime;

	private BigDecimal totalConsumptionAdjusted;

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Date getReadingDateTime() {
		return readingDateTime;
	}

	public void setReadingDateTime(Date readingDateTime) {
		this.readingDateTime = readingDateTime;
	}

	public BigDecimal getTotalConsumptionAdjusted() {
		return totalConsumptionAdjusted;
	}

	public void setTotalConsumptionAdjusted(BigDecimal totalConsumptionAdjusted) {
		this.totalConsumptionAdjusted = totalConsumptionAdjusted;
	}

}

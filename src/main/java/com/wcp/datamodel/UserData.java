package com.wcp.datamodel;

public class UserData {

	private String houseHoldId;
	private String smartMeterId;
	private String buildingId;
	private String consumptionType;
	private String zipcode;
	private String Country;

	public String getHouseHoldId() {
		return houseHoldId;
	}

	public void setHouseHoldId(String houseHoldId) {
		this.houseHoldId = houseHoldId;
	}

	public String getSmartMeterId() {
		return smartMeterId;
	}

	public void setSmartMeterId(String smartMeterId) {
		this.smartMeterId = smartMeterId;
	}

	public String getBuildingId() {
		return buildingId;
	}

	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}

	public String getConsumptionType() {
		return consumptionType;
	}

	public void setConsumptionType(String consumptionType) {
		this.consumptionType = consumptionType;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCountry() {
		return Country;
	}

	public void setCountry(String country) {
		Country = country;
	}

}

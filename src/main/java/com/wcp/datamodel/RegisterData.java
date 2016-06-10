package com.wcp.datamodel;

public class RegisterData implements java.io.Serializable {

	private String firstName;
	private String lastName;
	private String userName;
	private String zipCode;
	private int householdID;
	private String email;
	private String password;
	private String smartMeterID;

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public int getHouseholdID() {
		return householdID;
	}
	public void setHouseholdID(int householdID) {
		this.householdID = householdID;
	}
	public String getSmartMeterID() {
		return smartMeterID;
	}
	public void setSmartmeterID(String smartMeterID) {
		this.smartMeterID = smartMeterID;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}

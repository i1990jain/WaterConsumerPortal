package com.wcp.datamodel;

public class RegisterData {
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String confirmPassword;
	private int zipCode;
	private int householdID;
	private String smartmterID;
	private String username;
	private String oid;

	
	
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
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public int getZipCode() {
		return zipCode;
	}
	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}
	public int getHouseholdID() {
		return householdID;
	}
	public void setHouseholdID(int householdID) {
		this.householdID = householdID;
	}
	public String getSmartmterID() {
		return smartmterID;
	}
	public void setSmartmterID(String smartmterID) {
		this.smartmterID = smartmterID;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
}

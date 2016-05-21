package com.wcp.model;
// Generated 21 May, 2016 11:31:35 PM by Hibernate Tools 5.1.0.Alpha1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * NeutralUser generated by hbm2java
 */
@Entity
@Table(name = "neutral_user", catalog = "smartmeter")
public class NeutralUser implements java.io.Serializable {

	private Integer userOid;
	private Household household;
	private User user;
	private Date registrationDate;
	private String familyRole;
	private Boolean houseHolder;
	private String educationalLevel;
	private String incomeRate;
	private String currency;
	private Boolean public_;
	private String language;
	private String temperatureUnit;
	private String lengthUnit;

	public NeutralUser() {
	}

	public NeutralUser(User user) {
		this.user = user;
	}

	public NeutralUser(Household household, User user, Date registrationDate, String familyRole, Boolean houseHolder,
			String educationalLevel, String incomeRate, String currency, Boolean public_, String language,
			String temperatureUnit, String lengthUnit) {
		this.household = household;
		this.user = user;
		this.registrationDate = registrationDate;
		this.familyRole = familyRole;
		this.houseHolder = houseHolder;
		this.educationalLevel = educationalLevel;
		this.incomeRate = incomeRate;
		this.currency = currency;
		this.public_ = public_;
		this.language = language;
		this.temperatureUnit = temperatureUnit;
		this.lengthUnit = lengthUnit;
	}

	@GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "user"))
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "user_oid", unique = true, nullable = false)
	public Integer getUserOid() {
		return this.userOid;
	}

	public void setUserOid(Integer userOid) {
		this.userOid = userOid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "household_oid")
	public Household getHousehold() {
		return this.household;
	}

	public void setHousehold(Household household) {
		this.household = household;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "registration_date", length = 0)
	public Date getRegistrationDate() {
		return this.registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	@Column(name = "family_role")
	public String getFamilyRole() {
		return this.familyRole;
	}

	public void setFamilyRole(String familyRole) {
		this.familyRole = familyRole;
	}

	@Column(name = "house_holder")
	public Boolean getHouseHolder() {
		return this.houseHolder;
	}

	public void setHouseHolder(Boolean houseHolder) {
		this.houseHolder = houseHolder;
	}

	@Column(name = "educational_level")
	public String getEducationalLevel() {
		return this.educationalLevel;
	}

	public void setEducationalLevel(String educationalLevel) {
		this.educationalLevel = educationalLevel;
	}

	@Column(name = "income_rate")
	public String getIncomeRate() {
		return this.incomeRate;
	}

	public void setIncomeRate(String incomeRate) {
		this.incomeRate = incomeRate;
	}

	@Column(name = "currency")
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Column(name = "public")
	public Boolean getPublic_() {
		return this.public_;
	}

	public void setPublic_(Boolean public_) {
		this.public_ = public_;
	}

	@Column(name = "language")
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Column(name = "temperature_unit")
	public String getTemperatureUnit() {
		return this.temperatureUnit;
	}

	public void setTemperatureUnit(String temperatureUnit) {
		this.temperatureUnit = temperatureUnit;
	}

	@Column(name = "length_unit")
	public String getLengthUnit() {
		return this.lengthUnit;
	}

	public void setLengthUnit(String lengthUnit) {
		this.lengthUnit = lengthUnit;
	}

}

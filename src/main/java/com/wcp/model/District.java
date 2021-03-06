package com.wcp.model;
// Generated 21 May, 2016 11:31:35 PM by Hibernate Tools 5.1.0.Alpha1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * District generated by hbm2java
 */
@Entity
@Table(name = "district", catalog = "smartmeter")
public class District implements java.io.Serializable {

	private int oid;
	private String zipcode;
	private String country;
	private String city;
	private String name;
	private Set<Building> buildings = new HashSet<Building>(0);

	public District() {
	}

	public District(int oid) {
		this.oid = oid;
	}

	public District(int oid, String zipcode, String country, String city, String name, Set<Building> buildings) {
		this.oid = oid;
		this.zipcode = zipcode;
		this.country = country;
		this.city = city;
		this.name = name;
		this.buildings = buildings;
	}

	@Id

	@Column(name = "oid", unique = true, nullable = false)
	public int getOid() {
		return this.oid;
	}

	public void setOid(int oid) {
		this.oid = oid;
	}

	@Column(name = "zipcode")
	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	@Column(name = "country")
	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Column(name = "city")
	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "district")
	public Set<Building> getBuildings() {
		return this.buildings;
	}

	public void setBuildings(Set<Building> buildings) {
		this.buildings = buildings;
	}

}

package com.patient.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "patients")
public class Patient {
	
	@Id
	@Column(name = "id")
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "dob")
	private Date dob;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "number")
	private long number;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "drugId")
	private long drugId;
	
	@Column(name = "drugName")
	private String drugName;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public long getDrugId() {
		return drugId;
	}
	public void setDrugId(long drugId) {
		this.drugId = drugId;
	}
	public String getDrugName() {
		return drugName;
	}
	public void setDrugName(String drugName) {
		this.drugName = drugName;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	public Patient(long id, String name, String address, Date dob, String email, long number, String status,
			long drugId, String drugName) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.dob = dob;
		this.email = email;
		this.number = number;
		this.status = status;
		this.drugId = drugId;
		this.drugName = drugName;
	}
	public Patient() {
		super();
		// TODO Auto-generated constructor stub
	}

}

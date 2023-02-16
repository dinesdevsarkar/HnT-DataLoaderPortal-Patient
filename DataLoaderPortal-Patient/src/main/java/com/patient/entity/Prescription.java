package com.patient.entity;

public class Prescription {
	
	private long drugId;
	private String drugName;
	
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
	public Prescription(long drugId, String drugName) {
		super();
		this.drugId = drugId;
		this.drugName = drugName;
	}
	public Prescription() {
		super();
		// TODO Auto-generated constructor stub
	}

}

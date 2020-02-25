package com.finlabs.finexa.resources.model;

import java.util.Date;

public class FamilyMember {
	private int memberId;
	private int clientId;
	private String firstName;
	private String middleName;
	private String lastName;
	private int relation;
	private String otherRelation;
	private Date bDate;
	private String pan;
	private String aadhar;
	private String dependent;
	private String retired;

	public FamilyMember() {
		clientId = 0;
		firstName = "";
		middleName = "";
		lastName = "";
		relation = 0;
		otherRelation = "";
		bDate = new Date();
		pan = "";
		aadhar = "";
		dependent = "";
		retired = "";
	}

	public FamilyMember(int clientId, String firstName, String middleName, String lastName, int relation,
			String otherRelation, Date bDate, String pan, String aadhar, String dependent, String retired) {
		clientId = this.clientId;
		firstName = this.firstName;
		middleName = this.middleName;
		lastName = this.lastName;
		relation = this.relation;
		otherRelation = this.otherRelation;
		bDate = this.bDate;
		pan = this.pan;
		aadhar = this.aadhar;
		dependent = this.dependent;
		retired = this.retired;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getRelation() {
		return relation;
	}

	public void setRelation(int relation) {
		this.relation = relation;
	}

	public String getOtherRelation() {
		return otherRelation;
	}

	public void setOtherRelation(String otherRelation) {
		this.otherRelation = otherRelation;
	}

	public Date getBdate() {
		return bDate;
	}

	public void setBdate(Date bDate) {
		this.bDate = bDate;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getAadhar() {
		return aadhar;
	}

	public void setAadhar(String aadhar) {
		this.aadhar = aadhar;
	}

	public String getDependent() {
		return dependent;
	}

	public void setDependent(String dependent) {
		this.dependent = dependent;
	}

	public String getRetired() {
		return retired;
	}

	public void setRetired(String retired) {
		this.retired = retired;
	}
}

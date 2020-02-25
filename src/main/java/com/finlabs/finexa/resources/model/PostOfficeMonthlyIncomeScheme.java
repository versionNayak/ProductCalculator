package com.finlabs.finexa.resources.model;

import java.util.Date;
import java.util.List;

public class PostOfficeMonthlyIncomeScheme {
	private double interestReceived;
	private double totalInterestReceived;
	private Date maturityDate;
	private List<PostOfficeMonthlyIncomeSchemeLookup> postOfficeMISLookupList;
	private String maturityDisplayDate;
	private double interestRate;

	public double getInterestReceived() {
		return interestReceived;
	}

	public void setInterestReceived(double interestReceived) {
		this.interestReceived = interestReceived;
	}

	public double getTotalInterestReceived() {
		return totalInterestReceived;
	}

	public void setTotalInterestReceived(double totalInterestReceived) {
		this.totalInterestReceived = totalInterestReceived;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public List<PostOfficeMonthlyIncomeSchemeLookup> getPostOfficeMISLookupList() {
		return postOfficeMISLookupList;
	}

	public void setPostOfficeMISLookupList(List<PostOfficeMonthlyIncomeSchemeLookup> postOfficeMISLookupList) {
		this.postOfficeMISLookupList = postOfficeMISLookupList;
	}

	public String getMaturityDisplayDate() {
		return maturityDisplayDate;
	}

	public void setMaturityDisplayDate(String maturityDisplayDate) {
		this.maturityDisplayDate = maturityDisplayDate;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

}

package com.finlabs.finexa.resources.model;

import java.util.Date;
import java.util.List;

public class PONSC {
	private double depositAmount;
	private double annualInterest;
	private int term;
	private double compundFreq;
	private Date depositDate;
	private double maturityAmount;
	private double interestReceived;
	private double totalInterestReceived;
	private Date maturityDate;
	private List<PONSCLookup> poNScLookupList;
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

	public List<PONSCLookup> getPoNScLookupList() {
		return poNScLookupList;
	}

	public void setPoNScLookupList(List<PONSCLookup> poNScLookupList) {
		this.poNScLookupList = poNScLookupList;
	}

	public double getMaturityAmount() {
		return maturityAmount;
	}

	public double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public double getAnnualInterest() {
		return annualInterest;
	}

	public void setAnnualInterest(double annualInterest) {
		this.annualInterest = annualInterest;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public double getCompundFreq() {
		return compundFreq;
	}

	public void setCompundFreq(double compundFreq) {
		this.compundFreq = compundFreq;
	}

	public Date getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}

	public void setMaturityAmount(double maturityAmount) {
		this.maturityAmount = maturityAmount;
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

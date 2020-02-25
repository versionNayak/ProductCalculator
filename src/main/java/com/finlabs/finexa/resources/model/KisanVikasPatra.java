package com.finlabs.finexa.resources.model;

import java.util.Date;
import java.util.List;

public class KisanVikasPatra {
	private double depositAmount;
	private double annualInterest;
	private int term;
	private double compundFreq;
	private Date depositDate;
	private double maturityAmount;
	private double interestReceived;
	private double totalInterestReceived;
	private double interestCredited;
	private Date maturityDate;
	private List<KisanVikasPatraLookup> kisanVikasPatraLookupList;
	private String maturityDisplayDate;

	// auto populated display

	private double displayAnnualInterestRate;
	private int displayTermYear;
	private int displayTermMonth;
	private String displayCompundingFreq;

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

	public List<KisanVikasPatraLookup> getKisanVikasPatraLookupList() {
		return kisanVikasPatraLookupList;
	}

	public void setKisanVikasPatraLookupList(List<KisanVikasPatraLookup> kisanVikasPatraLookupList) {
		this.kisanVikasPatraLookupList = kisanVikasPatraLookupList;
	}

	public String getMaturityDisplayDate() {
		return maturityDisplayDate;
	}

	public void setMaturityDisplayDate(String maturityDisplayDate) {
		this.maturityDisplayDate = maturityDisplayDate;
	}

	public double getInterestCredited() {
		return interestCredited;
	}

	public void setInterestCredited(double interestCredited) {
		this.interestCredited = interestCredited;
	}

	public double getDisplayAnnualInterestRate() {
		return displayAnnualInterestRate;
	}

	public void setDisplayAnnualInterestRate(double displayAnnualInterestRate) {
		this.displayAnnualInterestRate = displayAnnualInterestRate;
	}

	public int getDisplayTermYear() {
		return displayTermYear;
	}

	public void setDisplayTermYear(int displayTermYear) {
		this.displayTermYear = displayTermYear;
	}

	public int getDisplayTermMonth() {
		return displayTermMonth;
	}

	public void setDisplayTermMonth(int displayTermMonth) {
		this.displayTermMonth = displayTermMonth;
	}

	public String getDisplayCompundingFreq() {
		return displayCompundingFreq;
	}

	public void setDisplayCompundingFreq(String displayCompundingFreq) {
		this.displayCompundingFreq = displayCompundingFreq;
	}

}

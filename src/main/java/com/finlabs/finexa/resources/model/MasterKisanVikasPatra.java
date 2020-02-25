package com.finlabs.finexa.resources.model;

import java.util.Date;

public class MasterKisanVikasPatra {
	private double interestRate;
	private int compundFreq;
	private int year;
	private int month;
	private Double investmentPeriod;
	private Date depositFromDate;
	private Date depositToDate;

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public int getCompundFreq() {
		return compundFreq;
	}

	public void setCompundFreq(int compundFreq) {
		this.compundFreq = compundFreq;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public Date getDepositFromDate() {
		return depositFromDate;
	}

	public void setDepositFromDate(Date depositFromDate) {
		this.depositFromDate = depositFromDate;
	}

	public Date getDepositToDate() {
		return depositToDate;
	}

	public void setDepositToDate(Date depositToDate) {
		this.depositToDate = depositToDate;
	}

	public Double getInvestmentPeriod() {
		return investmentPeriod;
	}

	public void setInvestmentPeriod(Double investmentPeriod) {
		this.investmentPeriod = investmentPeriod;
	}

}

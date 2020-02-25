package com.finlabs.finexa.resources.model;

public class BankFDTDRLookup {
	private int serialNo;
	private String referenceDate;
	private String referenceMonth;
	private String financialYear;
	private double amountDeposited;
	private double interestReceived;
	private double interestAccrued;
	private double totalInterestAccrued;
	private long daysToMaturity;

	public BankFDTDRLookup() {
		serialNo = 0;
		referenceDate = "";
		referenceMonth = "";
		financialYear = "";
		amountDeposited = 0.0;
		interestReceived = 0.0;
		totalInterestAccrued = 0.0;
		daysToMaturity = 0;
		interestAccrued = 0;
	}

	public BankFDTDRLookup(int serialNo, String referenceDate, String referenceMonth, String financialYear,
			double amountDeposited, double interestReceived, double totalInterestAccrued, long daysToMaturity,
			double interestAccrued) {
		serialNo = this.serialNo;
		referenceDate = this.referenceDate;
		referenceMonth = this.referenceMonth;
		financialYear = this.financialYear;
		amountDeposited = this.amountDeposited;
		interestReceived = this.interestReceived;
		totalInterestAccrued = this.totalInterestAccrued;
		daysToMaturity = this.daysToMaturity;
		interestAccrued = this.interestAccrued;
	}

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public String getReferenceDate() {
		return referenceDate;
	}

	public void setReferenceDate(String referenceDate) {
		this.referenceDate = referenceDate;
	}

	public String getReferenceMonth() {
		return referenceMonth;
	}

	public void setReferenceMonth(String referenceMonth) {
		this.referenceMonth = referenceMonth;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public double getAmountDeposited() {
		return amountDeposited;
	}

	public void setAmountDeposited(double amountDeposited) {
		this.amountDeposited = amountDeposited;
	}

	public double getInterestReceived() {
		return interestReceived;
	}

	public void setInterestReceived(double interestReceived) {
		this.interestReceived = interestReceived;
	}

	public double getTotalInterestAccrued() {
		return totalInterestAccrued;
	}

	public void setTotalInterestAccrued(double totalInterestAccrued) {
		this.totalInterestAccrued = totalInterestAccrued;
	}

	public long getDaysToMaturity() {
		return daysToMaturity;
	}

	public void setDaysToMaturity(long daysToMaturity) {
		this.daysToMaturity = daysToMaturity;
	}

	public double getInterestAccrued() {
		return interestAccrued;
	}

	public void setInterestAccrued(double interestAccrued) {
		this.interestAccrued = interestAccrued;
	}

	@Override
	public String toString() {
		return "BankFDTDRLookup [serialNo=" + serialNo + ", referenceDate=" + referenceDate + ", referenceMonth="
				+ referenceMonth + ", financialYear=" + financialYear + ", amountDeposited=" + amountDeposited
				+ ", interestReceived=" + interestReceived + ", totalInterestAccrued=" + totalInterestAccrued
				+ ", interestAccrued=" + interestAccrued + ", daysToMaturity=" + daysToMaturity + "]";
	}
}

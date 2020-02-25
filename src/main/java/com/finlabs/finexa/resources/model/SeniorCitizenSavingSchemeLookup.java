package com.finlabs.finexa.resources.model;

public class SeniorCitizenSavingSchemeLookup {
	private int serialNo;
	private String referenceDate;
	private String referenceMonth;
	private String financialYear;
	private double amountDeposited;
	private double interestReceived;
	private double totalInterestReceived;
	private long daysToMaturity;

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

	public double getTotalInterestReceived() {
		return totalInterestReceived;
	}

	public void setTotalInterestReceived(double totalInterestReceived) {
		this.totalInterestReceived = totalInterestReceived;
	}

	public long getDaysToMaturity() {
		return daysToMaturity;
	}

	public void setDaysToMaturity(long daysToMaturity) {
		this.daysToMaturity = daysToMaturity;
	}

}

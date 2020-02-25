package com.finlabs.finexa.resources.model;

public class PostOfficeMonthlyIncomeSchemeLookup {
	private int serialNo;
	private String referenceDate;
	private String referenceMonth;
	private String financialYear;
	private double amountDeposited;
	private double interestReceived;
	private double totalInterestReceived;
	private long daysToMaturity;

	public PostOfficeMonthlyIncomeSchemeLookup() {
		serialNo = 0;
		referenceDate = "";
		referenceMonth = "";
		financialYear = "";
		amountDeposited = 0.0;
		interestReceived = 0.0;
		totalInterestReceived = 0.0;
		daysToMaturity = 0;
	}

	public PostOfficeMonthlyIncomeSchemeLookup(int serialNo, String referenceDate, String referenceMonth,
			String financialYear, double amountDeposited, double interestReceived, double totalInterestReceived,
			long daysToMaturity) {
		serialNo = this.serialNo;
		referenceDate = this.referenceDate;
		referenceMonth = this.referenceMonth;
		financialYear = this.financialYear;
		amountDeposited = this.amountDeposited;
		interestReceived = this.interestReceived;
		totalInterestReceived = this.totalInterestReceived;
		daysToMaturity = this.daysToMaturity;
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

	@Override
	public String toString() {
		return "BankFDTDRLookup [serialNo=" + serialNo + ", referenceDate=" + referenceDate + ", referenceMonth="
				+ referenceMonth + ", financialYear=" + financialYear + ", amountDeposited=" + amountDeposited
				+ ", interestReceived=" + interestReceived + ", totalInterestReceived=" + totalInterestReceived
				+ ", daysToMaturity=" + daysToMaturity + "]";
	}
}

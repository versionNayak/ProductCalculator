package com.finlabs.finexa.resources.model;

public class KisanVikasPatraLookup {
	private int serialNo;
	private String referenceDate;
	private String referenceMonth;
	private String financialYear;
	private double yearOfInvestment;
	private double interestRate;
	private double openingBal;
	private double amountDeposited;
	private double interestReceived;
	private double totalInterestReceived;
	private double closingBalance;
	private long daysToMaturity;
	private double interestAccrued = 0.0;

	public double getInterestAccrued() {
		return interestAccrued;
	}

	public void setInterestAccrued(double interestAccrued) {
		this.interestAccrued = interestAccrued;
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

	public double getOpeningBal() {
		return openingBal;
	}

	public void setOpeningBal(double openingBal) {
		this.openingBal = openingBal;
	}

	public double getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(double closingBalance) {
		this.closingBalance = closingBalance;
	}

	public double getAmountDeposited() {
		return amountDeposited;
	}

	public void setAmountDeposited(double amountDeposited) {
		this.amountDeposited = amountDeposited;
	}

	public double getYearOfInvestment() {
		return yearOfInvestment;
	}

	public void setYearOfInvestment(double yearOfInvestment) {
		this.yearOfInvestment = yearOfInvestment;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

}

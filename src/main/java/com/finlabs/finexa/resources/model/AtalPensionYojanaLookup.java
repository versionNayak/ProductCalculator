package com.finlabs.finexa.resources.model;

public class AtalPensionYojanaLookup {
	private int serialNo;
	private String referenceDate;
	private String referenceMonth;
	private String financialYear;
	private double amountInvested;
	private double annuityAmount;
	private double amountToNominee;
	private int clientAge;

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

	public double getAmountInvested() {
		return amountInvested;
	}

	public void setAmountInvested(double amountInvested) {
		this.amountInvested = amountInvested;
	}

	public double getAnnuityAmount() {
		return annuityAmount;
	}

	public void setAnnuityAmount(double annuityAmount) {
		this.annuityAmount = annuityAmount;
	}

	public double getAmountToNominee() {
		return amountToNominee;
	}

	public void setAmountToNominee(double amountToNominee) {
		this.amountToNominee = amountToNominee;
	}

	public int getClientAge() {
		return clientAge;
	}

	public void setClientAge(int clientAge) {
		this.clientAge = clientAge;
	}

}

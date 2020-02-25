package com.finlabs.finexa.resources.model;

public class NPSLookup {
	private int serialNo;
	private String referenceDate;
	private int yearCount;
	private String financialYear;
	private int clientAge;
	private double openingBal;
	private double employeeCont;
	private double employerCont;
	private double assetClassEFundValue;
	private double assetClassCFundValue;
	private double assetClassGFundValue;
	private double npsReturnEarn;
	private double npsClosingBalance;

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

	public int getYearCount() {
		return yearCount;
	}

	public void setYearCount(int yearCount) {
		this.yearCount = yearCount;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public int getClientAge() {
		return clientAge;
	}

	public void setClientAge(int clientAge) {
		this.clientAge = clientAge;
	}

	public double getOpeningBal() {
		return openingBal;
	}

	public void setOpeningBal(double openingBal) {
		this.openingBal = openingBal;
	}

	public double getEmployeeCont() {
		return employeeCont;
	}

	public void setEmployeeCont(double employeeCont) {
		this.employeeCont = employeeCont;
	}

	public double getEmployerCont() {
		return employerCont;
	}

	public void setEmployerCont(double employerCont) {
		this.employerCont = employerCont;
	}

	public double getAssetClassEFundValue() {
		return assetClassEFundValue;
	}

	public void setAssetClassEFundValue(double assetClassEFundValue) {
		this.assetClassEFundValue = assetClassEFundValue;
	}

	public double getAssetClassCFundValue() {
		return assetClassCFundValue;
	}

	public void setAssetClassCFundValue(double assetClassCFundValue) {
		this.assetClassCFundValue = assetClassCFundValue;
	}

	public double getAssetClassGFundValue() {
		return assetClassGFundValue;
	}

	public void setAssetClassGFundValue(double assetClassGFundValue) {
		this.assetClassGFundValue = assetClassGFundValue;
	}

	public double getNpsClosingBalance() {
		return npsClosingBalance;
	}

	public void setNpsClosingBalance(double npsClosingBalance) {
		this.npsClosingBalance = npsClosingBalance;
	}

	public double getNpsReturnEarn() {
		return npsReturnEarn;
	}

	public void setNpsReturnEarn(double npsReturnEarn) {
		this.npsReturnEarn = npsReturnEarn;
	}

}

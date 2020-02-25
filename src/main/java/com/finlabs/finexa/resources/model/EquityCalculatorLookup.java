package com.finlabs.finexa.resources.model;

import java.util.Date;

public class EquityCalculatorLookup {
	private Date refDate;
	private String referenceMonth;
	private String financialYear;
	private double openingBal;
	private double amountInvested;
	private double noOfSharesPurchased;
	private double sharePrice;
	private double portfolioValue;
	private double ptpReturns;
	private double annualisedCagrReturns;
	private double investmentTenure;
	private double returnAbsolute;
	private double returnCagr;
	private double currentvalue;
	private double lastCurrentvalue;

	public Date getRefDate() {
		return refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
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

	public double getOpeningBal() {
		return openingBal;
	}

	public void setOpeningBal(double openingBal) {
		this.openingBal = openingBal;
	}

	public double getAmountInvested() {
		return amountInvested;
	}

	public void setAmountInvested(double amountInvested) {
		this.amountInvested = amountInvested;
	}

	public double getNoOfSharesPurchased() {
		return noOfSharesPurchased;
	}

	public void setNoOfSharesPurchased(double noOfSharesPurchased) {
		this.noOfSharesPurchased = noOfSharesPurchased;
	}

	public double getSharePrice() {
		return sharePrice;
	}

	public void setSharePrice(double sharePrice) {
		this.sharePrice = sharePrice;
	}

	public double getPortfolioValue() {
		return portfolioValue;
	}

	public void setPortfolioValue(double portfolioValue) {
		this.portfolioValue = portfolioValue;
	}

	public double getPtpReturns() {
		return ptpReturns;
	}

	public void setPtpReturns(double ptpReturns) {
		this.ptpReturns = ptpReturns;
	}

	public double getAnnualisedCagrReturns() {
		return annualisedCagrReturns;
	}

	public void setAnnualisedCagrReturns(double annualisedCagrReturns) {
		this.annualisedCagrReturns = annualisedCagrReturns;
	}

	public double getInvestmentTenure() {
		return investmentTenure;
	}

	public void setInvestmentTenure(double investmentTenure) {
		this.investmentTenure = investmentTenure;
	}

	public double getReturnAbsolute() {
		return returnAbsolute;
	}

	public void setReturnAbsolute(double returnAbsolute) {
		this.returnAbsolute = returnAbsolute;
	}

	public double getReturnCagr() {
		return returnCagr;
	}

	public void setReturnCagr(double returnCagr) {
		this.returnCagr = returnCagr;
	}

	public double getCurrentvalue() {
		return currentvalue;
	}

	public void setCurrentvalue(double currentvalue) {
		this.currentvalue = currentvalue;
	}

	public double getLastCurrentvalue() {
		return lastCurrentvalue;
	}

	public void setLastCurrentvalue(double lastCurrentvalue) {
		this.lastCurrentvalue = lastCurrentvalue;
	}
	

}

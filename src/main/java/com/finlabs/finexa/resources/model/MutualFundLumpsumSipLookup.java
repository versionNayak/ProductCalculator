package com.finlabs.finexa.resources.model;

import java.util.Date;
import java.util.List;

import com.finlabs.finexa.resources.util.Transaction;

public class MutualFundLumpsumSipLookup {
	private Date refDate;
	private String referenceMonth;
	private String financialYear;
	private String scheme;
	private double openingBal;
	private double amountInvested;
	private double unitsPurchased;
	private double closingBal;
	private double nav;
	private double portfolioValue;
	private double ptpReturns;
	private double annualisedCagrReturns;
	private double investmentTenure;
	private double xirr;
	private double xirrCalc;
	private int monthCounter;
    private double currentportfoliovalue;
    private double totalInvestmentValue;
    private double gainLoss;
    private double PTPReturn;
	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public Date getRefDate() {
		return refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public String getReferenceMonth() {
		return referenceMonth;
	}

	public void setReferenceMonth(String referenceMonth) {
		this.referenceMonth = referenceMonth;
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

	public double getUnitsPurchased() {
		return unitsPurchased;
	}

	public void setUnitsPurchased(double unitsPurchased) {
		this.unitsPurchased = unitsPurchased;
	}

	public double getClosingBal() {
		return closingBal;
	}

	public void setClosingBal(double closingBal) {
		this.closingBal = closingBal;
	}

	public double getNav() {
		return nav;
	}

	public void setNav(double nav) {
		this.nav = nav;
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

	public int getMonthCounter() {
		return monthCounter;
	}

	public void setMonthCounter(int monthCounter) {
		this.monthCounter = monthCounter;
	}

	public double getXirr() {
		return xirr;
	}

	public void setXirr(double xirr) {
		this.xirr = xirr;
	}

	public double getXirrCalc() {
		return xirrCalc;
	}

	public void setXirrCalc(double xirrCalc) {
		this.xirrCalc = xirrCalc;
	}

	

	public double getCurrentportfoliovalue() {
		return currentportfoliovalue;
	}

	public void setCurrentportfoliovalue(double currentportfoliovalue) {
		this.currentportfoliovalue = currentportfoliovalue;
	}

	public double getTotalInvestmentValue() {
		return totalInvestmentValue;
	}

	public void setTotalInvestmentValue(double totalInvestmentValue) {
		this.totalInvestmentValue = totalInvestmentValue;
	}

	public double getGainLoss() {
		return gainLoss;
	}

	public void setGainLoss(double gainLoss) {
		this.gainLoss = gainLoss;
	}

	public double getPTPReturn() {
		return PTPReturn;
	}

	public void setPTPReturn(double pTPReturn) {
		PTPReturn = pTPReturn;
	}

	@Override
	public String toString() {
		return "MutualFundLumpsum [refDate=" + refDate + ", financialYear=" + financialYear + ", referenceMonth="
				+ referenceMonth + ", monthCounter=" + monthCounter + ", openingBal=" + openingBal + ", amountInvested="
				+ amountInvested + ", unitsPurchased=" + unitsPurchased + ", closingBal=" + closingBal + ", nav=" + nav
				+ ", portfolioValue=" + portfolioValue + ", ptpReturns=" + ptpReturns + ", annualisedCagrReturns="
				+ annualisedCagrReturns + ", investmentTenure=" + investmentTenure + "]";
	}
}

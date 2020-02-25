package com.finlabs.finexa.resources.model;

import java.util.Date;
import java.util.List;

public class EquityCalculator {
	private int noOfShares;
	private double amountInvested;
	private Date dateOfPurchase;
	private double purchaseValue;
	private Date portfolioValueOnDate;
	private double navOnDate;
	private double returnAbsolute;
	private double returnCagr;
	private double portfolioValue;
	List<EquityCalculatorLookup> equityCalLookupList;

	public int getNoOfShares() {
		return noOfShares;
	}

	public void setNoOfShares(int noOfShares) {
		this.noOfShares = noOfShares;
	}

	public double getAmountInvested() {
		return amountInvested;
	}

	public void setAmountInvested(double amountInvested) {
		this.amountInvested = amountInvested;
	}

	public Date getDateOfPurchase() {
		return dateOfPurchase;
	}

	public void setDateOfPurchase(Date dateOfPurchase) {
		this.dateOfPurchase = dateOfPurchase;
	}

	public double getPurchaseValue() {
		return purchaseValue;
	}

	public void setPurchaseValue(double purchaseValue) {
		this.purchaseValue = purchaseValue;
	}

	public Date getPortfolioValueOnDate() {
		return portfolioValueOnDate;
	}

	public void setPortfolioValueOnDate(Date portfolioValueOnDate) {
		this.portfolioValueOnDate = portfolioValueOnDate;
	}

	public double getNavOnDate() {
		return navOnDate;
	}

	public void setNavOnDate(double navOnDate) {
		this.navOnDate = navOnDate;
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

	public double getPortfolioValue() {
		return portfolioValue;
	}

	public void setPortfolioValue(double portfolioValue) {
		this.portfolioValue = portfolioValue;
	}

	public List<EquityCalculatorLookup> getEquityCalLookupList() {
		return equityCalLookupList;
	}

	public void setEquityCalLookupList(List<EquityCalculatorLookup> equityCalLookupList) {
		this.equityCalLookupList = equityCalLookupList;
	}

}

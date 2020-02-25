package com.finlabs.finexa.resources.model;

import java.util.Date;
import java.util.List;

public class MutualFundLumpsumSip {
	private Date dateOfInvestment;
	private String fSchemaName;
	private double amountInvested;
	private int unitPurchased;
	private int mode;
	private int schemaIsinCode;
	private double sipAmount;
	private Date sipStartDate;
	private int sipInstallment;
	private int sipFrequency;
	private double totalInvestedAmount;
	private double portfolioValue;
	private double ptpReturns;
	private double cacgr;
	private double xirr;
	private Date sipEndDate;
	private String maturityDisplayDate;

	List<MutualFundLumpsumSipLookup> mutualFundLumpsumSipList;

	public double getTotalInvestedAmount() {
		return totalInvestedAmount;
	}

	public void setTotalInvestedAmount(double totalInvestedAmount) {
		this.totalInvestedAmount = totalInvestedAmount;
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

	public double getCacgr() {
		return cacgr;
	}

	public void setCacgr(double cacgr) {
		this.cacgr = cacgr;
	}

	public double getXirr() {
		return xirr;
	}

	public void setXirr(double xirr) {
		this.xirr = xirr;
	}

	public List<MutualFundLumpsumSipLookup> getMutualFundLumpsumSipList() {
		return mutualFundLumpsumSipList;
	}

	public void setMutualFundLumpsumSipList(List<MutualFundLumpsumSipLookup> mutualFundLumpsumSipList) {
		this.mutualFundLumpsumSipList = mutualFundLumpsumSipList;
	}

	public Date getSipEndDate() {
		return sipEndDate;
	}

	public void setSipEndDate(Date sipEndDate) {
		this.sipEndDate = sipEndDate;
	}

	public Date getDateOfInvestment() {
		return dateOfInvestment;
	}

	public void setDateOfInvestment(Date dateOfInvestment) {
		this.dateOfInvestment = dateOfInvestment;
	}

	public double getAmountInvested() {
		return amountInvested;
	}

	public void setAmountInvested(double amountInvested) {
		this.amountInvested = amountInvested;
	}

	public int getUnitPurchased() {
		return unitPurchased;
	}

	public void setUnitPurchased(int unitPurchased) {
		this.unitPurchased = unitPurchased;
	}

	public double getSipAmount() {
		return sipAmount;
	}

	public void setSipAmount(double sipAmount) {
		this.sipAmount = sipAmount;
	}

	public Date getSipStartDate() {
		return sipStartDate;
	}

	public void setSipStartDate(Date sipStartDate) {
		this.sipStartDate = sipStartDate;
	}

	public int getSipInstallment() {
		return sipInstallment;
	}

	public void setSipInstallment(int sipInstallment) {
		this.sipInstallment = sipInstallment;
	}

	public int getSipFrequency() {
		return sipFrequency;
	}

	public void setSipFrequency(int sipFrequency) {
		this.sipFrequency = sipFrequency;
	}

	public int getSchemaIsinCode() {
		return schemaIsinCode;
	}

	public void setSchemaIsinCode(int schemaIsinCode) {
		this.schemaIsinCode = schemaIsinCode;
	}

	public String getfSchemaName() {
		return fSchemaName;
	}

	public void setfSchemaName(String fSchemaName) {
		this.fSchemaName = fSchemaName;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String getMaturityDisplayDate() {
		return maturityDisplayDate;
	}

	public void setMaturityDisplayDate(String maturityDisplayDate) {
		this.maturityDisplayDate = maturityDisplayDate;
	}



	@Override
	public String toString() {
		return "MutualFundLumpsumSip [totalInvestedAmount=" + totalInvestedAmount + ", portfolioValue=" + portfolioValue
				+ ", ptpReturns=" + ptpReturns + ", cacgr=" + cacgr + ", xirr=" + xirr + "]";
	}
}

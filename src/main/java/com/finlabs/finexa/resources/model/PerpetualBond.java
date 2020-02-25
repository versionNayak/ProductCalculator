package com.finlabs.finexa.resources.model;

import java.util.Date;
import java.util.List;

public class PerpetualBond {
	private int serialNo;
	private String referenceDate;
	private String referenceMonth;
	private String financialYear;
	private double amountDeposited;

	private double couponReceived;
	private long totalCouponReceived;
	private Date daysToMaturity;
	private String viewMaturityDate;

	public String getViewMaturityDate() {
		return viewMaturityDate;
	}

	public void setViewMaturityDate(String viewMaturityDate) {
		this.viewMaturityDate = viewMaturityDate;
	}

	private long numberOfBondsPurchased;
	private double bondFaceValue;

	private double effectiveTimeToMaturity;

	private double interestCouponRate;
	private String term;

	private long coupounPayoutFrequency;

	private Date investmentDate;

	private double currentYield;

	private double currentValue;
	private String tenureYearsDays;
	private int totalMonths;

	public String getTenureYearsDays() {
		return tenureYearsDays;
	}

	public void setTenureYearsDays(String tenureYearsDays) {
		this.tenureYearsDays = tenureYearsDays;
	}

	private List<PerpetualBondLookup> bondDebentureLookupList;

	public double getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(double currentValue) {
		this.currentValue = currentValue;
	}

	public double getEffectiveTimeToMaturity() {
		return effectiveTimeToMaturity;
	}

	public void setEffectiveTimeToMaturity(double effectiveTimeToMaturity) {
		this.effectiveTimeToMaturity = effectiveTimeToMaturity;
	}

	public int getSerialNo() {
		return serialNo;
	}

	public long getNumberOfBondsPurchased() {
		return numberOfBondsPurchased;
	}

	public void setNumberOfBondsPurchased(long numberOfBondsPurchased) {
		this.numberOfBondsPurchased = numberOfBondsPurchased;
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

	public double getCouponReceived() {
		return couponReceived;
	}

	public void setCouponReceived(double couponReceived) {
		this.couponReceived = couponReceived;
	}

	public long getTotalCouponReceived() {
		return totalCouponReceived;
	}

	public void setTotalCouponReceived(long totalCouponReceived) {
		this.totalCouponReceived = totalCouponReceived;
	}

	public Date getDaysToMaturity() {
		return daysToMaturity;
	}

	public void setDaysToMaturity(Date daysToMaturity) {
		this.daysToMaturity = daysToMaturity;
	}

	public double getBondFaceValue() {
		return bondFaceValue;
	}

	public void setBondFaceValue(double bondFaceValue) {
		this.bondFaceValue = bondFaceValue;
	}

	public double getInterestCouponRate() {
		return interestCouponRate;
	}

	public void setInterestCouponRate(double interestCouponRate) {
		this.interestCouponRate = interestCouponRate;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public long getCoupounPayoutFrequency() {
		return coupounPayoutFrequency;
	}

	public void setCoupounPayoutFrequency(long coupounPayoutFrequency) {
		this.coupounPayoutFrequency = coupounPayoutFrequency;
	}

	public Date getInvestmentDate() {
		return investmentDate;
	}

	public void setInvestmentDate(Date investmentDate) {
		this.investmentDate = investmentDate;
	}

	public double getCurrentYield() {
		return currentYield;
	}

	public void setCurrentYield(double currentYield) {
		this.currentYield = currentYield;
	}

	public List<PerpetualBondLookup> getBondDebentureLookupList() {
		return bondDebentureLookupList;
	}

	public void setBondDebentureLookupList(List<PerpetualBondLookup> bondDebentureLookupList) {
		this.bondDebentureLookupList = bondDebentureLookupList;
	}

	public int getTotalMonths() {
		return totalMonths;
	}

	public void setTotalMonths(int totalMonths) {
		this.totalMonths = totalMonths;
	}

	@Override
	public String toString() {
		return "BondDebentures [serialNo=" + serialNo + ", referenceDate=" + referenceDate + ", referenceMonth="
				+ referenceMonth + ", financialYear=" + financialYear + ", amountDeposited=" + amountDeposited
				+ ", couponReceived=" + couponReceived + ", totalCouponReceived=" + totalCouponReceived
				+ ", daysToMaturity=" + daysToMaturity + "]";
	}
}

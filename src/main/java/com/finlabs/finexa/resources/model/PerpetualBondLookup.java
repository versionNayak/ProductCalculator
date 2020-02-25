package com.finlabs.finexa.resources.model;

public class PerpetualBondLookup {
	private int serialNo;
	private String referenceDate;
	private String referenceMonth;
	private String financialYear;
	private double bondAmountDeposited;
	private double couponReceived;
	private double totalCouponReceived;
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

	public double getBondAmountDeposited() {
		return bondAmountDeposited;
	}

	public void setBondAmountDeposited(double bondAmountDeposited) {
		this.bondAmountDeposited = bondAmountDeposited;
	}

	public long getDaysToMaturity() {
		return daysToMaturity;
	}

	public void setDaysToMaturity(long daysToMaturity) {
		this.daysToMaturity = daysToMaturity;
	}

	public double getCouponReceived() {
		return couponReceived;
	}

	public void setCouponReceived(double couponReceived) {
		this.couponReceived = couponReceived;
	}

	public double getTotalCouponReceived() {
		return totalCouponReceived;
	}

	public void setTotalCouponReceived(double totalCouponReceived) {
		this.totalCouponReceived = totalCouponReceived;
	}

	@Override
	public String toString() {
		return "BankFDTDRLookup [serialNo=" + serialNo + ", referenceDate=" + referenceDate + ", referenceMonth="
				+ referenceMonth + ", financialYear=" + financialYear + ", amountDeposited=" + bondAmountDeposited
				+ ", interestReceived=" + couponReceived + ", totalInterestReceived=" + totalCouponReceived
				+ ", daysToMaturity=" + daysToMaturity + "]";
	}
}

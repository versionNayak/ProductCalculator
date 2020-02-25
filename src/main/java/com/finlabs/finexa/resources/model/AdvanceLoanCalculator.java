package com.finlabs.finexa.resources.model;

import java.util.Date;

public class AdvanceLoanCalculator {
	private int serialNo;
	private int loanType;
	private double loanAmount;
	private double interestRate;
	private int loanTenure;
	private Date loanStartDate;
	private int originalEMiCount;
	private double originalEmiAmount;
	private Date loanEndDate;

	public int getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	public int getLoanType() {
		return loanType;
	}

	public void setLoanType(int loanType) {
		this.loanType = loanType;
	}

	public double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public int getLoanTenure() {
		return loanTenure;
	}

	public void setLoanTenure(int loanTenure) {
		this.loanTenure = loanTenure;
	}

	public Date getLoanStartDate() {
		return loanStartDate;
	}

	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	public int getOriginalEMiCount() {
		return originalEMiCount;
	}

	public void setOriginalEMiCount(int originalEMiCount) {
		this.originalEMiCount = originalEMiCount;
	}

	public double getOriginalEmiAmount() {
		return originalEmiAmount;
	}

	public void setOriginalEmiAmount(double originalEmiAmount) {
		this.originalEmiAmount = originalEmiAmount;
	}

	public Date getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
	}

	@Override
	public String toString() {
		return "AdvanceLoanCalculator [serialNo=" + serialNo + ", loanType=" + loanType + ", loanAmount=" + loanAmount
				+ ", interestRate=" + interestRate + ", loanTenure=" + loanTenure + ", loanStartDate=" + loanStartDate
				+ "]";
	}
}

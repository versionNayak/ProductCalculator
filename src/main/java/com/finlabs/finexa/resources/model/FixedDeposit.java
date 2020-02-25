package com.finlabs.finexa.resources.model;

import java.util.Date;

public class FixedDeposit {
	// private int clientId;
	private int financialAssetType;
	private String bankName;
	private int depositType;
	private Date depositDate;
	private double depositAmount;
	private double interestRate;
	private int compoundingFrequency;
	private String tenureYears;
	private int tenure;
	private int interestPayoutFrequency;
	private int rdDepositFrequency;
	private Date depositMaturityDate;

	@SuppressWarnings("unused")
	public FixedDeposit() {

		long time = System.currentTimeMillis();
		financialAssetType = 0;
		bankName = "";
		depositType = 0;
		Date depositDate = new Date();
		depositAmount = 0.0d;
		interestRate = 0.0d;
		compoundingFrequency = 0;
		tenureYears = "";
		tenure = 0;
		interestPayoutFrequency = 0;
		Date depositMaturityDate = new Date();

	}

	public FixedDeposit(int financialAssetType, String bankName, int depositType, Date depositDate,
			double depositAmount, double interestRate, int compoundingFrequency, String tenureYears, int tenure,
			int interestPayoutFrequency, Date depositmaturityDate) {

		financialAssetType = this.financialAssetType;
		bankName = this.bankName;
		depositType = this.depositType;
		depositDate = this.depositDate;
		depositAmount = this.depositAmount;
		interestRate = this.interestRate;
		compoundingFrequency = this.compoundingFrequency;
		tenureYears = this.tenureYears;
		tenure = this.tenure;
		interestPayoutFrequency = this.interestPayoutFrequency;
		depositmaturityDate = this.depositMaturityDate;

	}

	public int getFinancialAssetType() {
		return financialAssetType;
	}

	public void setFinancialAssetType(int financialAssetType) {
		this.financialAssetType = financialAssetType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public int getDepositType() {
		return depositType;
	}

	public void setDepositType(int depositType) {
		this.depositType = depositType;
	}

	public Date getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}

	public double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public int getCompoundingFrequency() {
		return compoundingFrequency;
	}

	public void setCompoundingFrequency(int compoundingFrequency) {
		this.compoundingFrequency = compoundingFrequency;
	}

	public String getTenureYears() {
		return tenureYears;
	}

	public void setTenureYears(String tenureYears) {
		this.tenureYears = tenureYears;
	}

	public int getTenure() {
		return tenure;
	}

	public void setTenure(int tenure) {
		this.tenure = tenure;
	}

	public int getInterestPayoutFrequency() {
		return interestPayoutFrequency;
	}

	public void setInterestPayoutFrequency(int interestPayoutFrequency) {
		this.interestPayoutFrequency = interestPayoutFrequency;
	}

	public Date getDepositMaturityDate() {
		return depositMaturityDate;
	}

	public void setDepositMaturityDate(Date depositMaturityDate) {
		this.depositMaturityDate = depositMaturityDate;
	}

	public int getRdDepositFrequency() {
		return rdDepositFrequency;
	}

	public void setRdDepositFrequency(int rdDepositFrequency) {
		this.rdDepositFrequency = rdDepositFrequency;
	}

	@Override
	public String toString() {
		return "FixedDeposit [financialAssetType=" + financialAssetType + ",bankName=" + bankName + ",depositType="
				+ depositType + ",depositDate=" + depositDate + ", depositAmount=" + depositAmount + " , interestRate="
				+ interestRate + ",compoundingFrequency=" + compoundingFrequency + ",tenureYears=" + tenureYears
				+ ", tenure=" + tenure + ",interestPayoutFrequency=" + interestPayoutFrequency
				+ ", depositMaturityDate=" + depositMaturityDate + "]";
	}
}

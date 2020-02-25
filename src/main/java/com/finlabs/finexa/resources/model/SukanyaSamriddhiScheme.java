package com.finlabs.finexa.resources.model;

import java.util.Date;
import java.util.List;

public class SukanyaSamriddhiScheme {
	private double depositAmount;
	private int amountDepositFreq;
	private double annualInterest;
	private int term;
	private double compundFreq;
	private Date depositDate;
	private double totalAmountDeposited;
	private double maturityAmount;
	private double totalInterestReceived;
	private Date depositMaturityDate;
	private Date paymentMaturityDate;
	private List<SukanyaSamriddhiSchemeLookup> sukanyaSamSchLookupList;
	private String maturityDisplayDate;
	private String paymentmaturityDisplayDate;
	
	//for database purpose
	private String financialYear;
	private double interestRate;

	public double getTotalAmountDeposited() {
		return totalAmountDeposited;
	}

	public void setTotalAmountDeposited(double totalAmountDeposited) {
		this.totalAmountDeposited = totalAmountDeposited;
	}

	public double getMaturityAmount() {
		return maturityAmount;
	}

	public void setMaturityAmount(double maturityAmount) {
		this.maturityAmount = maturityAmount;
	}

	public double getTotalInterestReceived() {
		return totalInterestReceived;
	}

	public void setTotalInterestReceived(double totalInterestReceived) {
		this.totalInterestReceived = totalInterestReceived;
	}



	public double getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(double depositAmount) {
		this.depositAmount = depositAmount;
	}

	public int getAmountDepositFreq() {
		return amountDepositFreq;
	}

	public void setAmountDepositFreq(int amountDepositFreq) {
		this.amountDepositFreq = amountDepositFreq;
	}

	public double getAnnualInterest() {
		return annualInterest;
	}

	public void setAnnualInterest(double annualInterest) {
		this.annualInterest = annualInterest;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public List<SukanyaSamriddhiSchemeLookup> getSukanyaSamSchLookupList() {
		return sukanyaSamSchLookupList;
	}

	public void setSukanyaSamSchLookupList(List<SukanyaSamriddhiSchemeLookup> sukanyaSamSchLookupList) {
		this.sukanyaSamSchLookupList = sukanyaSamSchLookupList;
	}

	public double getCompundFreq() {
		return compundFreq;
	}

	public void setCompundFreq(double compundFreq) {
		this.compundFreq = compundFreq;
	}

	public Date getDepositDate() {
		return depositDate;
	}

	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}

	public String getMaturityDisplayDate() {
		return maturityDisplayDate;
	}

	public void setMaturityDisplayDate(String maturityDisplayDate) {
		this.maturityDisplayDate = maturityDisplayDate;
	}

	public String getFinancialYear() {
		return financialYear;
	}

	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public Date getDepositMaturityDate() {
		return depositMaturityDate;
	}

	public void setDepositMaturityDate(Date depositMaturityDate) {
		this.depositMaturityDate = depositMaturityDate;
	}

	public Date getPaymentMaturityDate() {
		return paymentMaturityDate;
	}

	public void setPaymentMaturityDate(Date paymentMaturityDate) {
		this.paymentMaturityDate = paymentMaturityDate;
	}

	public String getPaymentmaturityDisplayDate() {
		return paymentmaturityDisplayDate;
	}

	public void setPaymentmaturityDisplayDate(String paymentmaturityDisplayDate) {
		this.paymentmaturityDisplayDate = paymentmaturityDisplayDate;
	}

}

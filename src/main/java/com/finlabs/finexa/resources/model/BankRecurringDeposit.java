package com.finlabs.finexa.resources.model;

import java.util.Date;
import java.util.List;

public class BankRecurringDeposit {
	private double depositAmount;
	private int amountDepositFreq;
	private double annualInterest;
	private int term;
	private double compundFreq;
	private Date depositDate;
	private double totalAmountDeposited;
	private double maturityAmount;
	private double totalInterestReceived;
	private Date maturityDate;
	private List<BankRecurringDepositLookup> bankRecurringLookupList;
	private String maturityDisplayDate;

	public String getMaturityDisplayDate() {
		return maturityDisplayDate;
	}

	public void setMaturityDisplayDate(String maturityDisplayDate) {
		this.maturityDisplayDate = maturityDisplayDate;
	}

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

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
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

	public List<BankRecurringDepositLookup> getBankRecurringLookupList() {
		return bankRecurringLookupList;
	}

	public void setBankRecurringLookupList(List<BankRecurringDepositLookup> bankRecurringLookupList) {
		this.bankRecurringLookupList = bankRecurringLookupList;
	}

}

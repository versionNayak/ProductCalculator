package com.finlabs.finexa.resources.model;

import java.util.Date;

public class SimpleLoanCalLookup {
	private int serialNumber;

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	private Date refDate;
	private String financialYear;
	private String referenceMonth;
	private int installmentNumber;
	private double begningBal;
	private double interestPayment;
	private double principalPayment;
	private double endingBalance;
	private double emiAmount;
	private double totalPrincipalPaid;
	private double totalInterestPaid;
	private int loanEndDays;
	private String displayDate;

	public int getLoanEndDays() {
		return loanEndDays;
	}

	public void setLoanEndDays(int loanEndDays) {
		this.loanEndDays = loanEndDays;
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

	public int getInstallmentNumber() {
		return installmentNumber;
	}

	public void setInstallmentNumber(int installmentNumber) {
		this.installmentNumber = installmentNumber;
	}

	public double getBegningBal() {
		return begningBal;
	}

	public void setBegningBal(double begningBal) {
		this.begningBal = begningBal;
	}

	public double getInterestPayment() {
		return interestPayment;
	}

	public void setInterestPayment(double interestPayment) {
		this.interestPayment = interestPayment;
	}

	public double getPrincipalPayment() {
		return principalPayment;
	}

	public void setPrincipalPayment(double principalPayment) {
		this.principalPayment = principalPayment;
	}

	public double getEndingBalance() {
		return endingBalance;
	}

	public void setEndingBalance(double endingBalance) {
		this.endingBalance = endingBalance;
	}

	public double getEmiAmount() {
		return emiAmount;
	}

	public void setEmiAmount(double emiAmount) {
		this.emiAmount = emiAmount;
	}

	public double getTotalPrincipalPaid() {
		return totalPrincipalPaid;
	}

	public void setTotalPrincipalPaid(double totalPrincipalPaid) {
		this.totalPrincipalPaid = totalPrincipalPaid;
	}

	public double getTotalInterestPaid() {
		return totalInterestPaid;
	}

	public void setTotalInterestPaid(double totalInterestPaid) {
		this.totalInterestPaid = totalInterestPaid;
	}

	public String getDisplayDate() {
		return displayDate;
	}

	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}

}

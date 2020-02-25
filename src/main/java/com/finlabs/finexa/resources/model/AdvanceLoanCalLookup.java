package com.finlabs.finexa.resources.model;

import java.util.Date;

public class AdvanceLoanCalLookup implements Cloneable {
	private Date refDate;
	private String displayRefDate;
	private String financialYear;
	private String referenceMonth;
	private int installmentNumber;
	private double begningBal;
	private double interestPayment;
	private double principalPayment;
	private double endingBalance;
	private double prePaymentAmount;
	private double emiAmount;
	private String interestRate;
	private String newInterestRate;
	private int changeInEMI;
	private double newEMI;
	private int changeInTenure;
	private long newTenure;
	private int outstandingTenure;
	private double totalPrincipalPaid;
	private double totalInterestPaid;

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

	public double getPrePaymentAmount() {
		return prePaymentAmount;
	}

	public void setPrePaymentAmount(double prePaymentAmount) {
		this.prePaymentAmount = prePaymentAmount;
	}

	public void setPrePaymentAmount(int prePaymentAmount) {
		this.prePaymentAmount = prePaymentAmount;
	}

	public double getEmiAmount() {
		return emiAmount;
	}

	public void setEmiAmount(double emiAmount) {
		this.emiAmount = emiAmount;
	}

	public String getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}

	public String getNewInterestRate() {
		return newInterestRate;
	}

	public void setNewInterestRate(String newInterestRate) {
		this.newInterestRate = newInterestRate;
	}

	public int getChangeInEMI() {
		return changeInEMI;
	}

	public void setChangeInEMI(int changeInEMI) {
		this.changeInEMI = changeInEMI;
	}

	public double getNewEMI() {
		return newEMI;
	}

	public void setNewEMI(double newEMI) {
		this.newEMI = newEMI;
	}

	public int getChangeInTenure() {
		return changeInTenure;
	}

	public void setChangeInTenure(int changeInTenure) {
		this.changeInTenure = changeInTenure;
	}

	public long getNewTenure() {
		return newTenure;
	}

	public void setNewTenure(long newTenure) {
		this.newTenure = newTenure;
	}

	public int getOutstandingTenure() {
		return outstandingTenure;
	}

	public void setOutstandingTenure(int outstandingTenure) {
		this.outstandingTenure = outstandingTenure;
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

	public String getDisplayRefDate() {
		return displayRefDate;
	}

	public void setDisplayRefDate(String displayRefDate) {
		this.displayRefDate = displayRefDate;
	}

	@Override
	public String toString() {
		return "AdvanceLoanCalLookup [refDate=" + refDate + ", financialYear=" + financialYear + ", referenceMonth="
				+ referenceMonth + ", installmentNumber=" + installmentNumber + ", begningBal=" + begningBal
				+ ", interestPayment=" + interestPayment + ", principalPayment=" + principalPayment + ", endingBalance="
				+ endingBalance + ", prePaymentAmount=" + prePaymentAmount + ", emiAmount=" + emiAmount
				+ ", interestRate=" + interestRate + ", newInterestRate=" + newInterestRate + ", changeInEMI="
				+ changeInEMI + ", newEMI=" + newEMI + ", changeInTenure=" + changeInTenure + ", newTenure=" + newTenure
				+ ", outstandingTenure=" + outstandingTenure + ", totalPrincipalPaid=" + totalPrincipalPaid
				+ ", totalInterestPaid=" + totalInterestPaid + "]";
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return (AdvanceLoanCalLookup) super.clone();
	}
}

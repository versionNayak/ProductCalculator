package com.finlabs.finexa.resources.model;

import java.util.Date;
import java.util.List;

public class SimpleLoanCalculator {
	private int loanCategory;
	private String loanCategoryLabel;
	private String loanDiscription;
	private String loanProvider;
	private int loanType;
	private String loanTypeLabel;
	private double loanAmount;
	private double interestRate;
	private int loanTenure;
	private Date loanStartDate;
	private int numberOfEMI;
	private double emiAmount;
	private Date loanEndDate;
	private int interestPaymentFrequency;
	private double totalInterestPaid;
	private List<SimpleLoanCalLookup> lookupList;
	private String loan_original_flag;
	private String displayDate;

	public int getNumberOfEMI() {
		return numberOfEMI;
	}

	public void setNumberOfEMI(int numberOfEMI) {
		this.numberOfEMI = numberOfEMI;
	}

	public double getEmiAmount() {
		return emiAmount;
	}

	public void setEmiAmount(double emiAmount) {
		this.emiAmount = emiAmount;
	}

	public Date getLoanEndDate() {
		return loanEndDate;
	}

	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
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

	public int getLoanCategory() {
		return loanCategory;
	}

	public void setLoanCategory(int loanCategory) {
		this.loanCategory = loanCategory;
	}

	public String getLoanDiscription() {
		return loanDiscription;
	}

	public void setLoanDiscription(String loanDiscription) {
		this.loanDiscription = loanDiscription;
	}

	public String getLoanProvider() {
		return loanProvider;
	}

	public void setLoanProvider(String loanProvider) {
		this.loanProvider = loanProvider;
	}

	public String getLoanCategoryLabel() {
		return loanCategoryLabel;
	}

	public void setLoanCategoryLabel(String loanCategoryLabel) {
		this.loanCategoryLabel = loanCategoryLabel;
	}

	public String getLoanTypeLabel() {
		return loanTypeLabel;
	}

	public void setLoanTypeLabel(String loanTypeLabel) {
		this.loanTypeLabel = loanTypeLabel;
	}

	public int getInterestPaymentFrequency() {
		return interestPaymentFrequency;
	}

	public void setInterestPaymentFrequency(int interestPaymentFrequency) {
		this.interestPaymentFrequency = interestPaymentFrequency;
	}

	public List<SimpleLoanCalLookup> getLookupList() {
		return lookupList;
	}

	public void setLookupList(List<SimpleLoanCalLookup> lookupList) {
		this.lookupList = lookupList;
	}

	public double getTotalInterestPaid() {
		return totalInterestPaid;
	}

	public void setTotalInterestPaid(double totalInterestPaid) {
		this.totalInterestPaid = totalInterestPaid;
	}

	public String getLoan_original_flag() {
		return loan_original_flag;
	}

	public void setLoan_original_flag(String loan_original_flag) {
		this.loan_original_flag = loan_original_flag;
	}

	public String getDisplayDate() {
		return displayDate;
	}

	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}

	@Override
	public String toString() {
		return "AdvanceLoanCalculator [loanCategory=" + loanCategory + ", loanDiscription=" + loanDiscription
				+ ", loanProvider=" + loanProvider + ", loanType=" + loanType + ", loanAmount=" + loanAmount
				+ ", interestRate=" + interestRate + ", loanTenure=" + loanTenure + ", loanStartDate=" + loanStartDate
				+ "]";
	}
}

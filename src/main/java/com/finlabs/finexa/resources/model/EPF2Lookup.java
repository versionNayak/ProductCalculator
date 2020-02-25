package com.finlabs.finexa.resources.model;

import java.util.Date;

public class EPF2Lookup {

	private int serialNumber;
	private Date refDate;
	private String financialYear;
	private String referenceMonth;
	private int clientAge;
	private double monBasicDA;
	private double openingBalEPF;
	private double openingBalEPS;
	private double employeeContEPF;
	private double employerContEPF;
	private double employerContEPS;
	private double interestRateEPF;
	private double interestEarnedEPF;
	private double totalInterestEarnedEPF;
	private double closingBalEPF;
	private double closingBalEPS;

	// For Cash flow display
	private String displayDate;

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
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

	public int getClientAge() {
		return clientAge;
	}

	public void setClientAge(int clientAge) {
		this.clientAge = clientAge;
	}

	public double getMonBasicDA() {
		return monBasicDA;
	}

	public void setMonBasicDA(double monBasicDA) {
		this.monBasicDA = monBasicDA;
	}

	public double getOpeningBalEPF() {
		return openingBalEPF;
	}

	public void setOpeningBalEPF(double openingBalEPF) {
		this.openingBalEPF = openingBalEPF;
	}

	public double getOpeningBalEPS() {
		return openingBalEPS;
	}

	public void setOpeningBalEPS(double openingBalEPS) {
		this.openingBalEPS = openingBalEPS;
	}

	public double getEmployeeContEPF() {
		return employeeContEPF;
	}

	public void setEmployeeContEPF(double employeeContEPF) {
		this.employeeContEPF = employeeContEPF;
	}

	public double getEmployerContEPF() {
		return employerContEPF;
	}

	public void setEmployerContEPF(double employerContEPF) {
		this.employerContEPF = employerContEPF;
	}

	public double getEmployerContEPS() {
		return employerContEPS;
	}

	public void setEmployerContEPS(double employerContEPS) {
		this.employerContEPS = employerContEPS;
	}

	public double getInterestRateEPF() {
		return interestRateEPF;
	}

	public void setInterestRateEPF(double interestRateEPF) {
		this.interestRateEPF = interestRateEPF;
	}

	public double getInterestEarnedEPF() {
		return interestEarnedEPF;
	}

	public void setInterestEarnedEPF(double interestEarnedEPF) {
		this.interestEarnedEPF = interestEarnedEPF;
	}

	public double getTotalInterestEarnedEPF() {
		return totalInterestEarnedEPF;
	}

	public void setTotalInterestEarnedEPF(double totalInterestEarnedEPF) {
		this.totalInterestEarnedEPF = totalInterestEarnedEPF;
	}

	public double getClosingBalEPF() {
		return closingBalEPF;
	}

	public void setClosingBalEPF(double closingBalEPF) {
		this.closingBalEPF = closingBalEPF;
	}

	public double getClosingBalEPS() {
		return closingBalEPS;
	}

	public void setClosingBalEPS(double closingBalEPS) {
		this.closingBalEPS = closingBalEPS;
	}

	public String getDisplayDate() {
		return displayDate;
	}

	public void setDisplayDate(String displayDate) {
		this.displayDate = displayDate;
	}

}

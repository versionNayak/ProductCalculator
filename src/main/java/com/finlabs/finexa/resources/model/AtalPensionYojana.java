package com.finlabs.finexa.resources.model;

import java.util.Date;
import java.util.List;

public class AtalPensionYojana {

	private Date clientDOB;
	private int clientAge;
	private int contrFreq;
	private double monthlyPenReq;
	private int retirementAge;
	private Date contrStartDate;
	private int lifeExptenYear;
	private double freqContrValue;
	private double totalContr;
	private double contrPeriod;
	private Date contrEndDate;
	private double amountToNominee;
	private double annuityReqLifeExpten;
	private List<AtalPensionYojanaLookup> atalPensionYojanaLookupList;
	private String maturityDisplayDate;

	public Date getClientDOB() {
		return clientDOB;
	}

	public void setClientDOB(Date clientDOB) {
		this.clientDOB = clientDOB;
	}

	public int getClientAge() {
		return clientAge;
	}

	public void setClientAge(int clientAge) {
		this.clientAge = clientAge;
	}

	public int getContrFreq() {
		return contrFreq;
	}

	public void setContrFreq(int contrFreq) {
		this.contrFreq = contrFreq;
	}

	public double getMonthlyPenReq() {
		return monthlyPenReq;
	}

	public void setMonthlyPenReq(double monthlyPenReq) {
		this.monthlyPenReq = monthlyPenReq;
	}

	public int getRetirementAge() {
		return retirementAge;
	}

	public void setRetirementAge(int retirementAge) {
		this.retirementAge = retirementAge;
	}

	public Date getContrStartDate() {
		return contrStartDate;
	}

	public void setContrStartDate(Date contrStartDate) {
		this.contrStartDate = contrStartDate;
	}

	public int getLifeExptenYear() {
		return lifeExptenYear;
	}

	public void setLifeExptenYear(int lifeExptenYear) {
		this.lifeExptenYear = lifeExptenYear;
	}

	public double getTotalContr() {
		return totalContr;
	}

	public void setTotalContr(double totalContr) {
		this.totalContr = totalContr;
	}

	public double getContrPeriod() {
		return contrPeriod;
	}

	public void setContrPeriod(double contrPeriod) {
		this.contrPeriod = contrPeriod;
	}

	public Date getContrEndDate() {
		return contrEndDate;
	}

	public void setContrEndDate(Date contrEndDate) {
		this.contrEndDate = contrEndDate;
	}

	public double getAmountToNominee() {
		return amountToNominee;
	}

	public void setAmountToNominee(double amountToNominee) {
		this.amountToNominee = amountToNominee;
	}

	public double getAnnuityReqLifeExpten() {
		return annuityReqLifeExpten;
	}

	public void setAnnuityReqLifeExpten(double annuityReqLifeExpten) {
		this.annuityReqLifeExpten = annuityReqLifeExpten;
	}

	public List<AtalPensionYojanaLookup> getAtalPensionYojanaLookupList() {
		return atalPensionYojanaLookupList;
	}

	public void setAtalPensionYojanaLookupList(List<AtalPensionYojanaLookup> atalPensionYojanaLookupList) {
		this.atalPensionYojanaLookupList = atalPensionYojanaLookupList;
	}

	public double getFreqContrValue() {
		return freqContrValue;
	}

	public void setFreqContrValue(double freqContrValue) {
		this.freqContrValue = freqContrValue;
	}

	public String getMaturityDisplayDate() {
		return maturityDisplayDate;
	}

	public void setMaturityDisplayDate(String maturityDisplayDate) {
		this.maturityDisplayDate = maturityDisplayDate;
	}

}

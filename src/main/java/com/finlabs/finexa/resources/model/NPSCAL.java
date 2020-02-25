package com.finlabs.finexa.resources.model;

import java.util.Date;
import java.util.List;

public class NPSCAL {
	private Date clientDOB;
	private double currNPSBal;
	private double empolyeeCont;
	private int empolyeeContFreq;
	private double empolyerCont;
	private int empolyerContFreq;
	private double annualIncrease;
	private double assetClassEAll;
	private double assetClassCAll;
	private double assetClassGAll;
	private double assetClassEExp;
	private double assetClassCExp;
	private double assetClassGExp;
	private double expectedReturns;
	private int empolyeeContAge;
	private int empolyerContAge;
	private int retirementAge;
	private int planType;
	private Date planningDate;
	private double totalEmployeeContribution;
	private double totalEmployerContribution;
	private double totalNpsCorpus;
	private double maxWithdrawCorpus;
	private double annuityAvailCorpus;
	private List<NPSLookup> npsLookupList;

	public Date getClientDOB() {
		return clientDOB;
	}

	public void setClientDOB(Date clientDOB) {
		this.clientDOB = clientDOB;
	}

	public double getCurrNPSBal() {
		return currNPSBal;
	}

	public void setCurrNPSBal(double currNPSBal) {
		this.currNPSBal = currNPSBal;
	}

	public double getEmpolyeeCont() {
		return empolyeeCont;
	}

	public void setEmpolyeeCont(double empolyeeCont) {
		this.empolyeeCont = empolyeeCont;
	}

	public int getEmpolyeeContFreq() {
		return empolyeeContFreq;
	}

	public void setEmpolyeeContFreq(int empolyeeContFreq) {
		this.empolyeeContFreq = empolyeeContFreq;
	}

	public double getEmpolyerCont() {
		return empolyerCont;
	}

	public void setEmpolyerCont(double empolyerCont) {
		this.empolyerCont = empolyerCont;
	}

	public int getEmpolyerContFreq() {
		return empolyerContFreq;
	}

	public void setEmpolyerContFreq(int empolyerContFreq) {
		this.empolyerContFreq = empolyerContFreq;
	}

	public double getAnnualIncrease() {
		return annualIncrease;
	}

	public void setAnnualIncrease(double annualIncrease) {
		this.annualIncrease = annualIncrease;
	}

	public double getAssetClassEAll() {
		return assetClassEAll;
	}

	public void setAssetClassEAll(double assetClassEAll) {
		this.assetClassEAll = assetClassEAll;
	}

	public double getAssetClassCAll() {
		return assetClassCAll;
	}

	public void setAssetClassCAll(double assetClassCAll) {
		this.assetClassCAll = assetClassCAll;
	}

	public double getAssetClassGAll() {
		return assetClassGAll;
	}

	public void setAssetClassGAll(double assetClassGAll) {
		this.assetClassGAll = assetClassGAll;
	}

	public double getAssetClassEExp() {
		return assetClassEExp;
	}

	public void setAssetClassEExp(double assetClassEExp) {
		this.assetClassEExp = assetClassEExp;
	}

	public double getAssetClassCExp() {
		return assetClassCExp;
	}

	public void setAssetClassCExp(double assetClassCExp) {
		this.assetClassCExp = assetClassCExp;
	}

	public double getAssetClassGExp() {
		return assetClassGExp;
	}

	public void setAssetClassGExp(double assetClassGExp) {
		this.assetClassGExp = assetClassGExp;
	}

	public double getExpectedReturns() {
		return expectedReturns;
	}

	public void setExpectedReturns(double expectedReturns) {
		this.expectedReturns = expectedReturns;
	}

	public int getEmpolyeeContAge() {
		return empolyeeContAge;
	}

	public void setEmpolyeeContAge(int empolyeeContAge) {
		this.empolyeeContAge = empolyeeContAge;
	}

	public int getEmpolyerContAge() {
		return empolyerContAge;
	}

	public void setEmpolyerContAge(int empolyerContAge) {
		this.empolyerContAge = empolyerContAge;
	}

	public int getRetirementAge() {
		return retirementAge;
	}

	public void setRetirementAge(int retirementAge) {
		this.retirementAge = retirementAge;
	}

	public Date getPlanningDate() {
		return planningDate;
	}

	public void setPlanningDate(Date planningDate) {
		this.planningDate = planningDate;
	}

	public List<NPSLookup> getNpsLookupList() {
		return npsLookupList;
	}

	public void setNpsLookupList(List<NPSLookup> npsLookupList) {
		this.npsLookupList = npsLookupList;
	}

	public int getPlanType() {
		return planType;
	}

	public void setPlanType(int planType) {
		this.planType = planType;
	}

	public double getTotalEmployeeContribution() {
		return totalEmployeeContribution;
	}

	public void setTotalEmployeeContribution(double totalEmployeeContribution) {
		this.totalEmployeeContribution = totalEmployeeContribution;
	}

	public double getTotalEmployerContribution() {
		return totalEmployerContribution;
	}

	public void setTotalEmployerContribution(double totalEmployerContribution) {
		this.totalEmployerContribution = totalEmployerContribution;
	}

	public double getTotalNpsCorpus() {
		return totalNpsCorpus;
	}

	public void setTotalNpsCorpus(double totalNpsCorpus) {
		this.totalNpsCorpus = totalNpsCorpus;
	}

	public double getMaxWithdrawCorpus() {
		return maxWithdrawCorpus;
	}

	public void setMaxWithdrawCorpus(double maxWithdrawCorpus) {
		this.maxWithdrawCorpus = maxWithdrawCorpus;
	}

	public double getAnnuityAvailCorpus() {
		return annuityAvailCorpus;
	}

	public void setAnnuityAvailCorpus(double annuityAvailCorpus) {
		this.annuityAvailCorpus = annuityAvailCorpus;
	}

}

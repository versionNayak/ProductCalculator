package com.finlabs.finexa.resources.model;

import java.util.Date;
import java.util.List;

public class Annuity2ProductCalculator {

	private Date clientDOB;
	private double pensionableCorpus;
	private double annuityRate;
	private double lifeExpectancySelf;
	private double lifeExpectancySpouse;
	private int annuityPayoutFreq;
	private Date annuityStartDate;
	private int annuityType;
	private int noOfYearsService;
	private double annuityGrowthRate;
	private double annuityT1TotalPenRec;
	private double annuityT1BackEndDt;
	private Date annuityT1EndDate;
	private double annuityT2TotalPenRec;
	private double annuityT2BackEndDt;
	private Date annuityT2EndDate;
	private double annuityT3TotalPenRec;
	private double annuityT3BackEndDt;
	private Date annuityT3EndDate;
	private double annuityT4TotalPenRec;
	private double annuityT4BackEndDt;
	private Date annuityT4EndDate;
	private double annuityT5TotalPenRec;
	private double annuityT5BackEndDt;
	private Date annuityT5EndDate;
	private double epsTotalPenRec;
	private double epsBackEndDt;

	private double monthlyBasicDA;
	private double monthlyPension;
	private Date epsEndDate;

	private String epsEndDateDisplayDate;
	private String annuityT1EndDateDisplayDate;
	private String annuityT2EndDateDisplayDate;
	private String annuityT3EndDateDisplayDate;
	private String annuityT4EndDateDisplayDate;
	private String annuityT5EndDateDisplayDate;

	private List<Annuity2ProductLookup> annuityLookupList;

	public Date getClientDOB() {
		return clientDOB;
	}

	public void setClientDOB(Date clientDOB) {
		this.clientDOB = clientDOB;
	}

	public double getPensionableCorpus() {
		return pensionableCorpus;
	}

	public void setPensionableCorpus(double pensionableCorpus) {
		this.pensionableCorpus = pensionableCorpus;
	}

	public double getAnnuityRate() {
		return annuityRate;
	}

	public void setAnnuityRate(double annuityRate) {
		this.annuityRate = annuityRate;
	}

	public double getLifeExpectancySelf() {
		return lifeExpectancySelf;
	}

	public void setLifeExpectancySelf(double lifeExpectancySelf) {
		this.lifeExpectancySelf = lifeExpectancySelf;
	}

	public double getLifeExpectancySpouse() {
		return lifeExpectancySpouse;
	}

	public void setLifeExpectancySpouse(double lifeExpectancySpouse) {
		this.lifeExpectancySpouse = lifeExpectancySpouse;
	}

	public int getAnnuityPayoutFreq() {
		return annuityPayoutFreq;
	}

	public void setAnnuityPayoutFreq(int annuityPayoutFreq) {
		this.annuityPayoutFreq = annuityPayoutFreq;
	}

	public Date getAnnuityStartDate() {
		return annuityStartDate;
	}

	public void setAnnuityStartDate(Date annuityStartDate) {
		this.annuityStartDate = annuityStartDate;
	}

	public int getAnnuityType() {
		return annuityType;
	}

	public void setAnnuityType(int annuityType) {
		this.annuityType = annuityType;
	}

	public double getAnnuityGrowthRate() {
		return annuityGrowthRate;
	}

	public void setAnnuityGrowthRate(double annuityGrowthRate) {
		this.annuityGrowthRate = annuityGrowthRate;
	}

	public double getAnnuityT1TotalPenRec() {
		return annuityT1TotalPenRec;
	}

	public void setAnnuityT1TotalPenRec(double annuityT1TotalPenRec) {
		this.annuityT1TotalPenRec = annuityT1TotalPenRec;
	}

	public double getAnnuityT1BackEndDt() {
		return annuityT1BackEndDt;
	}

	public void setAnnuityT1BackEndDt(double annuityT1BackEndDt) {
		this.annuityT1BackEndDt = annuityT1BackEndDt;
	}

	public Date getAnnuityT1EndDate() {
		return annuityT1EndDate;
	}

	public void setAnnuityT1EndDate(Date annuityT1EndDate) {
		this.annuityT1EndDate = annuityT1EndDate;
	}

	public double getAnnuityT2TotalPenRec() {
		return annuityT2TotalPenRec;
	}

	public void setAnnuityT2TotalPenRec(double annuityT2TotalPenRec) {
		this.annuityT2TotalPenRec = annuityT2TotalPenRec;
	}

	public double getAnnuityT2BackEndDt() {
		return annuityT2BackEndDt;
	}

	public void setAnnuityT2BackEndDt(double annuityT2BackEndDt) {
		this.annuityT2BackEndDt = annuityT2BackEndDt;
	}

	public Date getAnnuityT2EndDate() {
		return annuityT2EndDate;
	}

	public void setAnnuityT2EndDate(Date annuityT2EndDate) {
		this.annuityT2EndDate = annuityT2EndDate;
	}

	public double getAnnuityT3TotalPenRec() {
		return annuityT3TotalPenRec;
	}

	public void setAnnuityT3TotalPenRec(double annuityT3TotalPenRec) {
		this.annuityT3TotalPenRec = annuityT3TotalPenRec;
	}

	public double getAnnuityT3BackEndDt() {
		return annuityT3BackEndDt;
	}

	public void setAnnuityT3BackEndDt(double annuityT3BackEndDt) {
		this.annuityT3BackEndDt = annuityT3BackEndDt;
	}

	public Date getAnnuityT3EndDate() {
		return annuityT3EndDate;
	}

	public void setAnnuityT3EndDate(Date annuityT3EndDate) {
		this.annuityT3EndDate = annuityT3EndDate;
	}

	public double getAnnuityT4TotalPenRec() {
		return annuityT4TotalPenRec;
	}

	public void setAnnuityT4TotalPenRec(double annuityT4TotalPenRec) {
		this.annuityT4TotalPenRec = annuityT4TotalPenRec;
	}

	public double getAnnuityT4BackEndDt() {
		return annuityT4BackEndDt;
	}

	public void setAnnuityT4BackEndDt(double annuityT4BackEndDt) {
		this.annuityT4BackEndDt = annuityT4BackEndDt;
	}

	public Date getAnnuityT4EndDate() {
		return annuityT4EndDate;
	}

	public void setAnnuityT4EndDate(Date annuityT4EndDate) {
		this.annuityT4EndDate = annuityT4EndDate;
	}

	public double getAnnuityT5TotalPenRec() {
		return annuityT5TotalPenRec;
	}

	public void setAnnuityT5TotalPenRec(double annuityT5TotalPenRec) {
		this.annuityT5TotalPenRec = annuityT5TotalPenRec;
	}

	public double getAnnuityT5BackEndDt() {
		return annuityT5BackEndDt;
	}

	public void setAnnuityT5BackEndDt(double annuityT5BackEndDt) {
		this.annuityT5BackEndDt = annuityT5BackEndDt;
	}

	public Date getAnnuityT5EndDate() {
		return annuityT5EndDate;
	}

	public void setAnnuityT5EndDate(Date annuityT5EndDate) {
		this.annuityT5EndDate = annuityT5EndDate;
	}

	public double getEpsTotalPenRec() {
		return epsTotalPenRec;
	}

	public void setEpsTotalPenRec(double epsTotalPenRec) {
		this.epsTotalPenRec = epsTotalPenRec;
	}

	public double getEpsBackEndDt() {
		return epsBackEndDt;
	}

	public void setEpsBackEndDt(double epsBackEndDt) {
		this.epsBackEndDt = epsBackEndDt;
	}

	public Date getEpsEndDate() {
		return epsEndDate;
	}

	public void setEpsEndDate(Date epsEndDate) {
		this.epsEndDate = epsEndDate;
	}

	public double getMonthlyBasicDA() {
		return monthlyBasicDA;
	}

	public void setMonthlyBasicDA(double monthlyBasicDA) {
		this.monthlyBasicDA = monthlyBasicDA;
	}

	public double getMonthlyPension() {
		return monthlyPension;
	}

	public void setMonthlyPension(double monthlyPension) {
		this.monthlyPension = monthlyPension;
	}

	public List<Annuity2ProductLookup> getAnnuityLookupList() {
		return annuityLookupList;
	}

	public int getNoOfYearsService() {
		return noOfYearsService;
	}

	public void setNoOfYearsService(int noOfYearsService) {
		this.noOfYearsService = noOfYearsService;
	}

	public void setAnnuityLookupList(List<Annuity2ProductLookup> annuityLookupList) {
		this.annuityLookupList = annuityLookupList;
	}

	public String getEpsEndDateDisplayDate() {
		return epsEndDateDisplayDate;
	}

	public void setEpsEndDateDisplayDate(String epsEndDateDisplayDate) {
		this.epsEndDateDisplayDate = epsEndDateDisplayDate;
	}

	public String getAnnuityT1EndDateDisplayDate() {
		return annuityT1EndDateDisplayDate;
	}

	public void setAnnuityT1EndDateDisplayDate(String annuityT1EndDateDisplayDate) {
		this.annuityT1EndDateDisplayDate = annuityT1EndDateDisplayDate;
	}

	public String getAnnuityT2EndDateDisplayDate() {
		return annuityT2EndDateDisplayDate;
	}

	public void setAnnuityT2EndDateDisplayDate(String annuityT2EndDateDisplayDate) {
		this.annuityT2EndDateDisplayDate = annuityT2EndDateDisplayDate;
	}

	public String getAnnuityT3EndDateDisplayDate() {
		return annuityT3EndDateDisplayDate;
	}

	public void setAnnuityT3EndDateDisplayDate(String annuityT3EndDateDisplayDate) {
		this.annuityT3EndDateDisplayDate = annuityT3EndDateDisplayDate;
	}

	public String getAnnuityT4EndDateDisplayDate() {
		return annuityT4EndDateDisplayDate;
	}

	public void setAnnuityT4EndDateDisplayDate(String annuityT4EndDateDisplayDate) {
		this.annuityT4EndDateDisplayDate = annuityT4EndDateDisplayDate;
	}

	public String getAnnuityT5EndDateDisplayDate() {
		return annuityT5EndDateDisplayDate;
	}

	public void setAnnuityT5EndDateDisplayDate(String annuityT5EndDateDisplayDate) {
		this.annuityT5EndDateDisplayDate = annuityT5EndDateDisplayDate;
	}

}

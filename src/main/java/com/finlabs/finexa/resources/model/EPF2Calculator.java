package com.finlabs.finexa.resources.model;

import java.util.Date;
import java.util.List;

public class EPF2Calculator {

	private Date clientDOB;
	private double currEPF;
	private double currEPS;
	private double monBasicDA;
	private double expSalInc;
	private double expEPFO;
	private int conUptoAge;
	private int epfWiDAge;
	private Date planDate;
	private int salIncreaseMonth;
	private double epfBalWithdraw;
	private double epsBalClientAge;
	private double totalInterestEarned;
	private double employeeCont;
	private double employerCont;
	private double currentInterestRate;
	private List<EPF2Lookup> lookupList;

	public Date getClientDOB() {
		return clientDOB;
	}

	public void setClientDOB(Date clientDOB) {
		this.clientDOB = clientDOB;
	}

	public double getCurrEPF() {
		return currEPF;
	}

	public void setCurrEPF(double currEPF) {
		this.currEPF = currEPF;
	}

	public double getCurrEPS() {
		return currEPS;
	}

	public void setCurrEPS(double currEPS) {
		this.currEPS = currEPS;
	}

	public double getMonBasicDA() {
		return monBasicDA;
	}

	public void setMonBasicDA(double monBasicDA) {
		this.monBasicDA = monBasicDA;
	}

	public double getExpSalInc() {
		return expSalInc;
	}

	public void setExpSalInc(double expSalInc) {
		this.expSalInc = expSalInc;
	}

	public double getExpEPFO() {
		return expEPFO;
	}

	public void setExpEPFO(double expEPFO) {
		this.expEPFO = expEPFO;
	}

	public int getConUptoAge() {
		return conUptoAge;
	}

	public void setConUptoAge(int conUptoAge) {
		this.conUptoAge = conUptoAge;
	}

	public int getEpfWiDAge() {
		return epfWiDAge;
	}

	public void setEpfWiDAge(int epfWiDAge) {
		this.epfWiDAge = epfWiDAge;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public int getSalIncreaseMonth() {
		return salIncreaseMonth;
	}

	public void setSalIncreaseMonth(int salIncreaseMonth) {
		this.salIncreaseMonth = salIncreaseMonth;
	}

	public double getEpfBalWithdraw() {
		return epfBalWithdraw;
	}

	public void setEpfBalWithdraw(double epfBalWithdraw) {
		this.epfBalWithdraw = epfBalWithdraw;
	}

	public double getEpsBalClientAge() {
		return epsBalClientAge;
	}

	public void setEpsBalClientAge(double epsBalClientAge) {
		this.epsBalClientAge = epsBalClientAge;
	}

	public double getTotalInterestEarned() {
		return totalInterestEarned;
	}

	public void setTotalInterestEarned(double totalInterestEarned) {
		this.totalInterestEarned = totalInterestEarned;
	}

	public double getEmployeeCont() {
		return employeeCont;
	}

	public double getEmployerCont() {
		return employerCont;
	}

	public void setEmployerCont(double employerCont) {
		this.employerCont = employerCont;
	}

	public void setEmployeeCont(double employeeCont) {
		this.employeeCont = employeeCont;
	}

	public List<EPF2Lookup> getLookupList() {
		return lookupList;
	}

	public void setLookupList(List<EPF2Lookup> lookupList) {
		this.lookupList = lookupList;
	}

	public double getCurrentInterestRate() {
		return currentInterestRate;
	}

	public void setCurrentInterestRate(double currentInterestRate) {
		this.currentInterestRate = currentInterestRate;
	}

}
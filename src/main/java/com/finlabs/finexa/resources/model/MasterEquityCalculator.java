package com.finlabs.finexa.resources.model;

import java.util.Date;

public class MasterEquityCalculator {
	private Date date;
	private String isin;
	private double closingBal;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public double getClosingBal() {
		return closingBal;
	}

	public void setClosingBal(double closingBal) {
		this.closingBal = closingBal;
	}

}

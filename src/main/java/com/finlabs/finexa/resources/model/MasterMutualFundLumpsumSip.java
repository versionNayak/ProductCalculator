package com.finlabs.finexa.resources.model;

import java.util.Date;

public class MasterMutualFundLumpsumSip {
	private Date date;
	private Integer amfiCode;
	private String isin;
	private double nav;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getAmfiCode() {
		return amfiCode;
	}

	public void setAmfiCode(Integer amfiCode) {
		this.amfiCode = amfiCode;
	}

	public double getNav() {
		return nav;
	}

	public void setNav(double nav) {
		this.nav = nav;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

}

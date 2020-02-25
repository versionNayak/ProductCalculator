package com.finlabs.finexa.resources.model;

import java.util.Date;

public class GainLossMapDTO {
	
	private Date sellTransationDate;
	private String sellTransactionType;
	private double soldUnits;
	private double soldNav;
	private double soldAmt;
	
	private Date purchaseTransationDate;
	private String purchaseTransactionType;
	private double purchaseUnits;
	private double purchaseNav;
	private double purchaseAmt;
	
	public Date getSellTransationDate() {
		return sellTransationDate;
	}
	public void setSellTransationDate(Date sellTransationDate) {
		this.sellTransationDate = sellTransationDate;
	}
	public String getSellTransactionType() {
		return sellTransactionType;
	}
	public void setSellTransactionType(String sellTransactionType) {
		this.sellTransactionType = sellTransactionType;
	}
	public double getSoldUnits() {
		return soldUnits;
	}
	public void setSoldUnits(double soldUnits) {
		this.soldUnits = soldUnits;
	}
	public double getSoldNav() {
		return soldNav;
	}
	public void setSoldNav(double soldNav) {
		this.soldNav = soldNav;
	}
	public double getSoldAmt() {
		return soldAmt;
	}
	public void setSoldAmt(double soldAmt) {
		this.soldAmt = soldAmt;
	}
	public Date getPurchaseTransationDate() {
		return purchaseTransationDate;
	}
	public void setPurchaseTransationDate(Date purchaseTransationDate) {
		this.purchaseTransationDate = purchaseTransationDate;
	}
	public String getPurchaseTransactionType() {
		return purchaseTransactionType;
	}
	public void setPurchaseTransactionType(String purchaseTransactionType) {
		this.purchaseTransactionType = purchaseTransactionType;
	}
	public double getPurchaseUnits() {
		return purchaseUnits;
	}
	public void setPurchaseUnits(double purchaseUnits) {
		this.purchaseUnits = purchaseUnits;
	}
	public double getPurchaseNav() {
		return purchaseNav;
	}
	public void setPurchaseNav(double purchaseNav) {
		this.purchaseNav = purchaseNav;
	}
	public double getPurchaseAmt() {
		return purchaseAmt;
	}
	public void setPurchaseAmt(double purchaseAmt) {
		this.purchaseAmt = purchaseAmt;
	}
	
	
}

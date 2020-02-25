package com.finlabs.finexa.resources.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class GainCalculator {
	
	private Date transationDate;
	private String schemeName;
	private String transactionType;
	private String transactionCode;
	private String transactionDescription;
	private double buyQuantity;
	private double residualBuyQty;
	private double sellQuantity;
	private double rate;
	private double balanceQuantity;
	private double netQuantity;
	private double transAmt;
	private double differentialCost;
	private double cumSellQuantity;
	private double cumBuyQuantity;
	private double cumBuyCost;
	private int buySeq;
	private int sellSeq;
	private double capitalGain;
	private double dividendReinvestRealized;
	private double dividendReinvestUnrealized;
	private double dividendPayoutRealized;
	private double dividendPayoutUnrealized;
	private double dividendRate;
	private double xirr;
	private double xirrUnrealized;
	private double xirrTotal;
	
	
	
	public double getXirr() {
		return xirr;
	}
	public void setXirr(double xirr) {
		this.xirr = xirr;
	}
	@Override
	public String toString() {
		return "GainCalculator [transationDate=" + transationDate + ", schemeName=" + schemeName + ", transactionType="
				+ transactionType + ", transactionCode=" + transactionCode + ", transactionDescription="
				+ transactionDescription + ", buyQuantity=" + buyQuantity + ", residualBuyQty=" + residualBuyQty
				+ ", sellQuantity=" + sellQuantity + ", rate=" + rate + ", balanceQuantity=" + balanceQuantity
				+ ", netQuantity=" + netQuantity + ", transAmt=" + transAmt + ", differentialCost=" + differentialCost
				+ ", cumSellQuantity=" + cumSellQuantity + ", cumBuyQuantity=" + cumBuyQuantity + ", cumBuyCost="
				+ cumBuyCost + ", buySeq=" + buySeq + ", sellSeq=" + sellSeq + ", capitalGain=" + capitalGain
				+ ", dividendReinvestRealized=" + dividendReinvestRealized + ", dividendReinvestUnrealized="
				+ dividendReinvestUnrealized + ", dividendPayoutRealized=" + dividendPayoutRealized
				+ ", dividendPayoutUnrealized=" + dividendPayoutUnrealized + ", dividendRate=" + dividendRate
				+ ", xirr=" + xirr + ", gainLossMap=" + gainLossMap + "]";
	}
	private SortedMap<Date,List<GainLossMapDTO>> gainLossMap;
	
	public SortedMap<Date, List<GainLossMapDTO>> getGainLossMap() {
		return gainLossMap;
	}
	public void setGainLossMap(SortedMap<Date, List<GainLossMapDTO>> gainLossMap) {
		this.gainLossMap = gainLossMap;
	}
	public Date getTransationDate() {
		return transationDate;
	}
	public void setTransationDate(Date transationDate) {
		this.transationDate = transationDate;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public double getBuyQuantity() {
		return buyQuantity;
	}
	public void setBuyQuantity(double buyQuantity) {
		this.buyQuantity = buyQuantity;
	}
	public double getResidualBuyQty() {
		return residualBuyQty;
	}
	public void setResidualBuyQty(double residualBuyQty) {
		this.residualBuyQty = residualBuyQty;
	}
	public double getSellQuantity() {
		return sellQuantity;
	}
	public void setSellQuantity(double sellQuantity) {
		this.sellQuantity = sellQuantity;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public double getBalanceQuantity() {
		return balanceQuantity;
	}
	public void setBalanceQuantity(double balanceQuantity) {
		this.balanceQuantity = balanceQuantity;
	}
	public double getNetQuantity() {
		return netQuantity;
	}
	public void setNetQuantity(double netQuantity) {
		this.netQuantity = netQuantity;
	}
	public double getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(double transAmt) {
		this.transAmt = transAmt;
	}
	public double getDifferentialCost() {
		return differentialCost;
	}
	public void setDifferentialCost(double differentialCost) {
		this.differentialCost = differentialCost;
	}
	public double getCumSellQuantity() {
		return cumSellQuantity;
	}
	public void setCumSellQuantity(double cumSellQuantity) {
		this.cumSellQuantity = cumSellQuantity;
	}
	public double getCumBuyQuantity() {
		return cumBuyQuantity;
	}
	public void setCumBuyQuantity(double cumBuyQuantity) {
		this.cumBuyQuantity = cumBuyQuantity;
	}
	public double getCumBuyCost() {
		return cumBuyCost;
	}
	public void setCumBuyCost(double cumBuyCost) {
		this.cumBuyCost = cumBuyCost;
	}
	public int getBuySeq() {
		return buySeq;
	}
	public void setBuySeq(int buySeq) {
		this.buySeq = buySeq;
	}
	public int getSellSeq() {
		return sellSeq;
	}
	public void setSellSeq(int sellSeq) {
		this.sellSeq = sellSeq;
	}
	public double getCapitalGain() {
		return capitalGain;
	}
	public void setCapitalGain(double capitalGain) {
		this.capitalGain = capitalGain;
	}
	
	public double getDividendReinvestUnrealized() {
		return dividendReinvestUnrealized;
	}
	public void setDividendReinvestUnrealized(double dividendReinvestUnrealized) {
		this.dividendReinvestUnrealized = dividendReinvestUnrealized;
	}
	public double getDividendReinvestRealized() {
		return dividendReinvestRealized;
	}
	public void setDividendReinvestRealized(double dividendReinvestRealized) {
		this.dividendReinvestRealized = dividendReinvestRealized;
	}
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	public double getDividendPayoutRealized() {
		return dividendPayoutRealized;
	}
	public void setDividendPayoutRealized(double dividendPayoutRealized) {
		this.dividendPayoutRealized = dividendPayoutRealized;
	}
	public double getDividendPayoutUnrealized() {
		return dividendPayoutUnrealized;
	}
	public void setDividendPayoutUnrealized(double dividendPayoutUnrealized) {
		this.dividendPayoutUnrealized = dividendPayoutUnrealized;
	}
	public double getDividendRate() {
		return dividendRate;
	}
	public void setDividendRate(double dividendRate) {
		this.dividendRate = dividendRate;
	}
	public String getTransactionDescription() {
		return transactionDescription;
	}
	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}
	public double getXirrTotal() {
		return xirrTotal;
	}
	public void setXirrTotal(double xirrTotal) {
		this.xirrTotal = xirrTotal;
	}
	public double getXirrUnrealized() {
		return xirrUnrealized;
	}
	public void setXirrUnrealized(double xirrUnrealized) {
		this.xirrUnrealized = xirrUnrealized;
	}
	
}

package com.finlabs.finexa.resources.model;

import java.util.Date;
import java.util.List;

public class BankFDTDRPC {
	private double interestReceived;
	private double totalInterestReceived;
	private Date maturityDate;
	private List<BankFDTDRLookup> bankFdTdrLookupList;
	private String maturityDisplayDate;

	public double getInterestReceived() {
		return interestReceived;
	}

	public void setInterestReceived(double interestReceived) {
		this.interestReceived = interestReceived;
	}

	public double getTotalInterestReceived() {
		return totalInterestReceived;
	}

	public void setTotalInterestReceived(double totalInterestReceived) {
		this.totalInterestReceived = totalInterestReceived;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public List<BankFDTDRLookup> getBankFdTdrLookupList() {
		return bankFdTdrLookupList;
	}

	public void setBankFdTdrLookup(List<BankFDTDRLookup> bankFdTdrLookupList) {
		this.bankFdTdrLookupList = bankFdTdrLookupList;
	}

	public String getMaturityDisplayDate() {
		return maturityDisplayDate;
	}

	public void setMaturityDisplayDate(String maturityDisplayDate) {
		this.maturityDisplayDate = maturityDisplayDate;
	}

	public void setBankFdTdrLookupList(List<BankFDTDRLookup> bankFdTdrLookupList) {
		this.bankFdTdrLookupList = bankFdTdrLookupList;
	}

}

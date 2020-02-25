package com.finlabs.finexa.resources.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.finlabs.finexa.resources.exception.FinexaBussinessException;
import com.finlabs.finexa.resources.model.BankFDTDRLookup;
import com.finlabs.finexa.resources.model.BankFDTDRPC;
import com.finlabs.finexa.resources.util.FinanceUtil;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class BankFDTDRService {
	Calendar cDepositDate = Calendar.getInstance();
	Calendar cMaturityDate = Calendar.getInstance();
	Calendar cInterimDate = Calendar.getInstance();
	String strInterimDate = "";
	SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MMM-yyyy");

	String strRefDate = "";
	String strRefMonth = "";
	String fiscalYear = "";
	double amountDeposited = 0.0;
	double interestReceived = 0.0;
	double interestAccrued = 0.0;
	double totalInterestAccrued = 0.0;
	double totalInterestReceived = 0.0;
	long daysToMaturity = 0;
	Date dtLastDayOfMonth = new Date();
	Date dtMaturityDate = new Date();
	List<BankFDTDRLookup> bankFDTDRLookupList = new ArrayList<BankFDTDRLookup>();

	public BankFDTDRPC getFDTDROutput(double deposit, double rateOfInterest, String tenureType, int tenure,
			int interestFrequency, Date depositDate) {
		BankFDTDRPC bankFdTdrOutput = new BankFDTDRPC();
		int calculatedDays = 0;
		// int varTermYears = 0;
		int varTermMonths = 0;
		try {
			if (tenureType.equals("D")) {
				long[] yearmonths = new FinexaDateUtil().getYearCountByDay(depositDate, tenure);
				// varTermYears = (int) yearmonths[1];
				varTermMonths = (int) yearmonths[0] * 12 + (int) yearmonths[1];
				calculatedDays = (int) yearmonths[2];
			} else {
				varTermMonths = (int) tenure * 12;
				// varTermYears = (int) tenure;
			}

			int varInterestFreq = interestFrequency;
			int interestFreqConstant = varInterestFreq;
			int interestFreqConstantLookup = 12 / varInterestFreq;

			cDepositDate.setTime(depositDate);
			cMaturityDate.setTime(depositDate);
			cInterimDate.setTime(depositDate);

			cMaturityDate.add(Calendar.MONTH, varTermMonths);
			cMaturityDate.add(Calendar.DAY_OF_MONTH, calculatedDays);
			double varDeposit = deposit;
			double pv = -varDeposit;
			double varROI = rateOfInterest;
			double rate = (((double) varROI)) / interestFreqConstant;

			interestReceived = FinanceUtil.YEARFRAC(depositDate, cMaturityDate.getTime(), 1);
			double PMT = FinanceUtil.pmt(rate, interestReceived, pv, varDeposit, false);

			bankFdTdrOutput.setInterestReceived(PMT);

			bankFdTdrOutput.setMaturityDate(cMaturityDate.getTime());
			strInterimDate = sdfOutput.format(cDepositDate.getTime());
			// List<BankFDTDRLookup> bankFDTDRLookupList = new ArrayList<BankFDTDRLookup>();
			int serialNo = 0;
			while (true) {
				BankFDTDRLookup bankFDTDRLookup = new BankFDTDRLookup();

				Date dtInterimDate = cInterimDate.getTime();
				strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);
				try {
					dtLastDayOfMonth = sdfOutput.parse(strRefDate);
					long diff = cMaturityDate.getTime().getTime() - dtLastDayOfMonth.getTime();
					daysToMaturity = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				strRefMonth = strRefDate.substring(3);
				fiscalYear = FinexaDateUtil.getFiscalYear(dtInterimDate);

				if (dtLastDayOfMonth.after(cMaturityDate.getTime())
						|| dtLastDayOfMonth.equals(cMaturityDate.getTime())) {
					if (serialNo < varTermMonths) {
						interestAccrued = (PMT / interestFreqConstantLookup)
								+ ((varTermMonths % 1) * (PMT / interestFreqConstantLookup));

					} else {
						if (varTermMonths % 1 == 0) {
							interestAccrued = (PMT / interestFreqConstantLookup);

						} else {
							interestAccrued = (varTermMonths % 1) * PMT / ((double) interestFreqConstantLookup);
						}

					}
				} else {
					interestAccrued = PMT / ((double) interestFreqConstantLookup);
				}

				if (daysToMaturity <= 0) {
					amountDeposited = -deposit;
				} else {
					if (serialNo < 1) {
						amountDeposited = varDeposit;
						interestAccrued = 0;
						totalInterestAccrued = 0;
						interestReceived = 0;
					} else {
						amountDeposited = 0;
					}

				}
				totalInterestAccrued = totalInterestAccrued + interestAccrued;
				if (serialNo % interestFreqConstantLookup == 0) {
					if (serialNo > 0) {
						interestReceived = totalInterestAccrued - (totalInterestReceived);
					}
				} else {
					interestReceived = 0;
				}
				bankFDTDRLookup.setSerialNo(serialNo);
				bankFDTDRLookup.setReferenceDate(strRefDate);
				bankFDTDRLookup.setReferenceMonth(strRefMonth);
				bankFDTDRLookup.setFinancialYear(fiscalYear);
				bankFDTDRLookup.setAmountDeposited(amountDeposited);
				bankFDTDRLookup.setInterestAccrued(interestAccrued);
				bankFDTDRLookup.setTotalInterestAccrued(totalInterestAccrued);
				bankFDTDRLookup.setInterestReceived(interestReceived);
				bankFDTDRLookup.setDaysToMaturity(daysToMaturity);
				totalInterestReceived = totalInterestReceived + interestReceived;
				bankFDTDRLookupList.add(bankFDTDRLookup);
				cInterimDate.add(Calendar.MONTH, 1);
				dtInterimDate = cInterimDate.getTime();
				strInterimDate = sdfOutput.format(dtInterimDate);
				serialNo = serialNo + 1;
				if (dtLastDayOfMonth.after(cMaturityDate.getTime())
						|| dtLastDayOfMonth.equals(cMaturityDate.getTime())) {
					break;
				}
			}
		} catch (Exception exp) {
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_BANK_FDTDR,
					FinexaConstant.PRODUCT_CAL_BANK_FDTDR_CODE, FinexaConstant.PRODUCT_CAL_BANK_FDTDR_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		bankFdTdrOutput.setBankFdTdrLookup(bankFDTDRLookupList);
		return bankFdTdrOutput;
	}

}
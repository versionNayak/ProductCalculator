package com.finlabs.finexa.resources.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.formula.functions.FinanceLib;

import com.finlabs.finexa.resources.exception.FinexaBussinessException;
import com.finlabs.finexa.resources.model.BankFDSTDRCDCP;
import com.finlabs.finexa.resources.model.BankRecurringDeposit;
import com.finlabs.finexa.resources.model.BankRecurringDepositLookup;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class BankRecurringDespositService {
	Calendar cDepositDate = Calendar.getInstance();
	Calendar cMaturityDate = Calendar.getInstance();
	Calendar cInterimDate = Calendar.getInstance();
	String strInterimDate = "";
	SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MMM-yyyy");

	String strRefDate = "";
	String strRefMonth = "";
	String fiscalYear = "";
	double openingBal = 0.0;
	double interestAccured = 0.0;
	double totalInterestAccured = 0.0;
	double interestCredit = 0.0;
	double totalInterestCredit = 0.0;
	long daysToMaturity = 0;
	Date dtLastDayOfMonth = new Date();
	Date dtMaturityDate = new Date();
	int varTermMonths = 0;
	int lookupSerialNumber = 1;

	public BankFDSTDRCDCP calculateFDSTDRCDCP(double openingAmount, double interestRate, int days, int month,
			int payoutFrequency, Date depositDate) {
		BankFDSTDRCDCP bankFdTdr = new BankFDSTDRCDCP();
		int interestFreqConstantLookup = 12 / payoutFrequency;
		// varTermMonths = (int) year * 12 + month;
		try {
			long[] yearmonths = new FinexaDateUtil().getYearCountByDay(depositDate, days);
			varTermMonths = (int) yearmonths[0] * 12 + (int) yearmonths[1];
			double maturityAmount = FinanceLib.fv((interestRate) / interestFreqConstantLookup,
					(varTermMonths * interestFreqConstantLookup), 0.0, -openingAmount, true);
			bankFdTdr.setMaturityAmount(maturityAmount);
			totalInterestAccured = maturityAmount - openingAmount;
			bankFdTdr.setTotalInterestReceived(totalInterestAccured);
		} catch (Exception exp) {
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_BANK_RD,
					FinexaConstant.PRODUCT_CAL_BANK_RD_CODE, FinexaConstant.PRODUCT_CAL_BANK_RD_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return bankFdTdr;
	}

	public BankRecurringDeposit getRecurringDepositCalculatedList(double deposit, double rateOfInterest, int year,
			int month, int rdDepositFreq, int compundingFreq, Date depositDate) {

		BankRecurringDeposit bankRecurringDepositOutput = new BankRecurringDeposit();
		double amountDeposited = deposit;
		List<BankRecurringDepositLookup> bankFDTDRLookupList = new ArrayList<BankRecurringDepositLookup>();
		try {
			// varTermMonths = (int) year * 12 + month;
			long[] yearmonths = new FinexaDateUtil().getYearCountByDay(depositDate, year);
			varTermMonths = (int) yearmonths[0] * 12 + (int) yearmonths[1];
			openingBal = 0;
			double closingBal = 0;

			int rdDepositFreqConstantLookup = 12 / rdDepositFreq;
			int compundingFreqConstantLookup = 12 / compundingFreq;
			int rdDepositFreqInterestPaid = rdDepositFreq;
			int compundingFreqInterestPaid = compundingFreq;

			// String strDepositDate = depositDate;
			Date varDepositDate = depositDate;
			cDepositDate.setTime(varDepositDate);
			cInterimDate.setTime(varDepositDate);
			cMaturityDate.setTime(depositDate);
			cMaturityDate.add(Calendar.MONTH, varTermMonths);
			bankRecurringDepositOutput.setMaturityDate(cMaturityDate.getTime());
			strInterimDate = sdfOutput.format(cDepositDate.getTime());

			int serialNo = 0;

			while (true) {
				BankRecurringDepositLookup bankFDTDRLookup = new BankRecurringDepositLookup();
				if (serialNo == 0) {
					openingBal = 0;
				} else {
					openingBal = closingBal;
				}

				java.util.Date dtInterimDate = cInterimDate.getTime();
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

				if (serialNo % rdDepositFreqConstantLookup == 0) {
					if (serialNo < varTermMonths) {
						amountDeposited = deposit;
					} else {
						amountDeposited = 0;
					}
				}
				if (serialNo > 0) {
					interestAccured = (openingBal) * (rateOfInterest) / compundingFreqConstantLookup
							/ compundingFreqInterestPaid;
				}

				totalInterestAccured = totalInterestAccured + interestAccured;
				if (serialNo % compundingFreqConstantLookup == 0) {
					if (serialNo > 0) {
						interestCredit = totalInterestAccured - totalInterestCredit;
						totalInterestCredit = totalInterestCredit + interestCredit;
					}
				} else {
					interestCredit = 0;

				}
				closingBal = openingBal + interestCredit + amountDeposited;

				if (dtLastDayOfMonth.after(cMaturityDate.getTime())) {
					if (interestCredit == 0) {
						interestCredit = totalInterestAccured - totalInterestCredit;
						closingBal = openingBal + interestCredit + amountDeposited;
					}

				}
				bankFDTDRLookup.setSerialNo(lookupSerialNumber);
				bankFDTDRLookup.setReferenceDate(strRefDate);
				bankFDTDRLookup.setReferenceMonth(strRefMonth);
				bankFDTDRLookup.setFinancialYear(fiscalYear);
				bankFDTDRLookup.setOpeningBal(Math.round(openingBal));
				bankFDTDRLookup.setAmountDeposited(Math.round(amountDeposited));
				bankFDTDRLookup.setInterestAccrued(Math.round(interestAccured));
				bankFDTDRLookup.setTotalInterestAccrued(Math.round(totalInterestAccured));
				bankFDTDRLookup.setInterestCredited(interestCredit);
				bankFDTDRLookup.setClosingBalance(Math.round(closingBal));
				bankFDTDRLookup.setDaysToMaturity(daysToMaturity);
				bankFDTDRLookupList.add(bankFDTDRLookup);
				cInterimDate.add(Calendar.MONTH, 1);
				dtInterimDate = cInterimDate.getTime();
				strInterimDate = sdfOutput.format(dtInterimDate);
				if (dtLastDayOfMonth.after(cMaturityDate.getTime())) {
					break;
				}
				amountDeposited = 0;
				serialNo++;
				lookupSerialNumber++;
			}

			bankRecurringDepositOutput.setMaturityAmount(closingBal);
			bankRecurringDepositOutput
					.setTotalAmountDeposited(deposit * rdDepositFreqInterestPaid * (year + (month / (double) 12)));
			bankRecurringDepositOutput.setTotalInterestReceived(bankRecurringDepositOutput.getMaturityAmount()
					- bankRecurringDepositOutput.getTotalAmountDeposited());
		} catch (Exception exp) {
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_BANK_RD,
					FinexaConstant.PRODUCT_CAL_BANK_RD_CODE, FinexaConstant.PRODUCT_CAL_BANK_RD_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		bankRecurringDepositOutput.setBankRecurringLookupList(bankFDTDRLookupList);
		return bankRecurringDepositOutput;
	}

}

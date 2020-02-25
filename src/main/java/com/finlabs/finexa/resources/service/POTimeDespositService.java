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
import com.finlabs.finexa.resources.model.POTimeDeposit;
import com.finlabs.finexa.resources.model.POTimeDepositLookup;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class POTimeDespositService {

	Calendar cDepositDate = Calendar.getInstance();
	Calendar cMaturityDate = Calendar.getInstance();
	Calendar cInterimDate = Calendar.getInstance();
	String strInterimDate = "";
	SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MMM-yyyy");

	String strRefDate = "";
	String strRefMonth = "";
	String fiscalYear = "";
	double openingBal = 0.0;
	double interestAccured = 0.0;
	double totalInterestReceived = 0.0;
	double interestPaid = 0.0;
	double totalInterestCredit = 0.0;
	long daysToMaturity = 0;
	Date dtLastDayOfMonth = new Date();
	Date dtMaturityDate = new Date();
	double displayInterestRate = 0.0;

	public POTimeDeposit getTimeDepositCalculatedList(double deposit, int yearsDays, int compundingFreq,
			int rdDepositFreq, Date depositDate, double interestRate) {

		POTimeDeposit poTimeDeposit = new POTimeDeposit();
		try {
			double amountDeposited = deposit;
			int varTermMonths = 0;
			displayInterestRate = interestRate;
			varTermMonths = (int) yearsDays * 12;

			openingBal = 0;
			double closingBal = 0;
			int rdDepositFreqConstantLookup = 0;
			int compundingFreqConstantLookup = 0;
			int rdDepositFreqInterestPaid = rdDepositFreq;
			rdDepositFreqConstantLookup = 12 / rdDepositFreq;

			int compundingFreqInterestPaid = compundingFreq;
			compundingFreqConstantLookup = 12 / compundingFreq;

			cDepositDate.setTime(depositDate);
			cInterimDate.setTime(depositDate);
			cMaturityDate.setTime(depositDate);
			cMaturityDate.add(Calendar.MONTH, varTermMonths);
			poTimeDeposit.setMaturityDate(cMaturityDate.getTime());
			strInterimDate = sdfOutput.format(cDepositDate.getTime());

			poTimeDeposit.setInterestReceived(
					FinanceLib.fv(displayInterestRate / compundingFreqInterestPaid, 4, 0, -deposit, false) - deposit);

			poTimeDeposit.setTotalInterestReceived(
					poTimeDeposit.getInterestReceived() * rdDepositFreqInterestPaid * yearsDays);
			List<POTimeDepositLookup> poTimeDepositLookupList = new ArrayList<POTimeDepositLookup>();
			int serialNo = 0;

			poTimeDeposit.setInterestRate(displayInterestRate);
			cMaturityDate.set(Calendar.DAY_OF_MONTH, cMaturityDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			while (true) {
				POTimeDepositLookup poTimeDepositLookup = new POTimeDepositLookup();

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
				if (serialNo == 0) {
					openingBal = 0;
				} else {
					if (daysToMaturity <= 0) {
						amountDeposited = -deposit;
					} else {
						amountDeposited = 0;
					}
					openingBal = closingBal;
				}

				if (serialNo % compundingFreqConstantLookup == 0) {
					if (serialNo > 0) {
						interestAccured = (openingBal) * ((displayInterestRate) / compundingFreqInterestPaid);
					}
					// totalInterestCredit = totalInterestCredit + interestCredit;
				}

				if (serialNo % rdDepositFreqConstantLookup == 0) {
					if (serialNo > 0) {
						interestPaid = poTimeDeposit.getInterestReceived();
					} else {
						interestPaid = 0;
					}

				}

				totalInterestReceived = totalInterestReceived + interestPaid;

				closingBal = openingBal + interestAccured - interestPaid;
				closingBal = closingBal + amountDeposited;
				if (dtLastDayOfMonth.equals(cMaturityDate.getTime())) {

					serialNo = 0;
				} else {
					if (dtLastDayOfMonth.after(cMaturityDate.getTime())) {
						break;
					}
				}
				poTimeDepositLookup.setSerialNo(serialNo);
				poTimeDepositLookup.setReferenceDate(strRefDate);
				poTimeDepositLookup.setReferenceMonth(strRefMonth);
				poTimeDepositLookup.setFinancialYear(fiscalYear);
				poTimeDepositLookup.setOpeningBal(openingBal);
				poTimeDepositLookup.setAmountDeposited(amountDeposited);
				poTimeDepositLookup.setInterestAccrued(interestAccured);
				poTimeDepositLookup.setInterestPaid(interestPaid);
				poTimeDepositLookup.setClosingBalance(closingBal);
				poTimeDepositLookup.setDaysToMaturity(daysToMaturity);
				poTimeDepositLookup.setTotalInterestReceived(totalInterestReceived);
				poTimeDepositLookupList.add(poTimeDepositLookup);
				cInterimDate.add(Calendar.MONTH, 1);
				dtInterimDate = cInterimDate.getTime();
				strInterimDate = sdfOutput.format(dtInterimDate);

				serialNo++;
				interestAccured = 0;
				amountDeposited = 0;
				interestPaid = 0;
			}

			poTimeDeposit.setPoTimeDepositLookupList(poTimeDepositLookupList);
		} catch (Exception exp) {
			// TODO Auto-generated catch block
			FinexaBussinessException finexaBuss = new FinexaBussinessException(
					FinexaConstant.PRODUCT_CAL_PO_TIME_DEPOSIT, FinexaConstant.PRODUCT_CAL_PO_TIME_DEPOSIT_CODE,
					FinexaConstant.PRODUCT_CAL_PO_TIME_DEPOSIT_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}

		return poTimeDeposit;
	}

}

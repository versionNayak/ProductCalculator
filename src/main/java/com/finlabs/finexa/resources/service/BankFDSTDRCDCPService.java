package com.finlabs.finexa.resources.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.finlabs.finexa.resources.exception.FinexaBussinessException;
import com.finlabs.finexa.resources.model.BankFDSTDRCDCP;
import com.finlabs.finexa.resources.model.BankFDSTDRCDCPLookup;
import com.finlabs.finexa.resources.model.BankFDTDRLookup;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class BankFDSTDRCDCPService {

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
	double interestAccrued = 0.0;
	double totalInterestAccrued = 0.0;
	double interestCredited = 0.0;
	double totalInterestCredited = 0.0;
	long daysToMaturity = 0;
	Date dtLastDayOfMonth = new Date();
	Date dtMaturityDate = new Date();
	int varTermMonths = 0;
	List<BankFDTDRLookup> bankFDTDRLookupList = new ArrayList<BankFDTDRLookup>();

	public BankFDSTDRCDCP calculateFDSTDRCDCP(double openingAmount, double interestRate, String tenureType, int tenure,
			int payoutFrequency, Date depositDate) {
		BankFDSTDRCDCP bankFdTdr = new BankFDSTDRCDCP();
		try {
			cMaturityDate.setTime(depositDate);
			int calculatedDays = 0;
			if (tenureType.equals("D")) {
				long[] yearmonths = new FinexaDateUtil().getYearCountByDay(depositDate, tenure);
				varTermMonths = (int) yearmonths[0] * 12 + (int) yearmonths[1];
				calculatedDays = (int) yearmonths[2];
			} else {
				varTermMonths = (int) tenure * 12;
			}

			cMaturityDate.add(Calendar.MONTH, varTermMonths);
			cMaturityDate.add(Calendar.DAY_OF_MONTH, calculatedDays);
			bankFdTdr.setMaturityDate(cMaturityDate.getTime());
		} catch (Exception exp) {
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_BANK_FDSTDR,
					FinexaConstant.PRODUCT_CAL_BANK_FDSTDR_CODE, FinexaConstant.PRODUCT_CAL_BANK_FDSTDR_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return bankFdTdr;
	}

	public BankFDSTDRCDCP getFDSTDRCDCPOutputList(double deposit, double rateOfInterest, String tenureType, int tenure,
			int interestFrequency, Date depositDate) {

		BankFDSTDRCDCP bankFdTdrOutput = new BankFDSTDRCDCP();
		try {
			if (tenureType.equals("D")) {
				long[] yearmonths = new FinexaDateUtil().getYearCountByDay(depositDate, tenure);
				varTermMonths = (int) yearmonths[0] * 12 + (int) yearmonths[1];

			} else {
				varTermMonths = (int) tenure * 12;
			}
			int varInterestFreq = interestFrequency;
			openingBal = deposit;
			double closingBal = deposit;
			int interestFreqConstantLookup = 12 / varInterestFreq;
			int interestFreqInterestPaid = varInterestFreq;

			Date varDepositDate = depositDate;

			BankFDSTDRCDCP currentOverAllcal = this.calculateFDSTDRCDCP(deposit, rateOfInterest, tenureType, tenure,
					interestFrequency, depositDate);
			totalInterestAccrued = 0.0;
			double PMT = currentOverAllcal.getMaturityAmount();
			cDepositDate.setTime(varDepositDate);
			cInterimDate.setTime(varDepositDate);
			bankFdTdrOutput.setInterestReceived(PMT);
			bankFdTdrOutput.setMaturityDate(currentOverAllcal.getMaturityDate());
			strInterimDate = sdfOutput.format(cDepositDate.getTime());
			List<BankFDSTDRCDCPLookup> bankFDTDRLookupList = new ArrayList<BankFDSTDRCDCPLookup>();
			int serialNo = 0;
			while (true) {
				BankFDSTDRCDCPLookup bankFDTDRLookup = new BankFDSTDRCDCPLookup();

				java.util.Date dtInterimDate = cInterimDate.getTime();
				strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);

				dtLastDayOfMonth = sdfOutput.parse(strRefDate);
				long diff = currentOverAllcal.getMaturityDate().getTime() - dtLastDayOfMonth.getTime();
				daysToMaturity = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

				strRefMonth = strRefDate.substring(3);
				fiscalYear = FinexaDateUtil.getFiscalYear(dtInterimDate);
				openingBal = closingBal;
				if (serialNo > 0) {
					interestAccrued = openingBal * (rateOfInterest) / interestFreqConstantLookup
							/ interestFreqInterestPaid;
				}
				totalInterestAccrued = totalInterestAccrued + interestAccrued;
				if (serialNo % interestFreqConstantLookup == 0) {
					if (serialNo > 0) {
						interestCredited = totalInterestAccrued - totalInterestCredited;
						totalInterestCredited = totalInterestCredited + interestCredited;
					}

				} else {
					interestCredited = 0;
				}

				closingBal = closingBal + interestCredited;

				if (dtLastDayOfMonth.equals(currentOverAllcal.getMaturityDate())) {
					if (interestCredited == 0) {
						interestCredited = totalInterestAccrued - totalInterestCredited;
						closingBal = closingBal + interestCredited;
					}
					bankFdTdrOutput.setTotalInterestReceived(closingBal - deposit);
					bankFdTdrOutput.setMaturityAmount(closingBal);
				}
				if (dtLastDayOfMonth.after(currentOverAllcal.getMaturityDate())) {
					Calendar firstDayMonth = Calendar.getInstance();
					Calendar endDate = Calendar.getInstance();
					firstDayMonth.setTime(dtLastDayOfMonth);
					endDate.setTime(currentOverAllcal.getMaturityDate());
					firstDayMonth.set(Calendar.DAY_OF_MONTH, endDate.get(Calendar.DAY_OF_MONTH));

					if (firstDayMonth.getTime().equals(endDate.getTime())) {
						if (interestCredited == 0) {
							interestCredited = totalInterestAccrued - totalInterestCredited;
							closingBal = closingBal + interestCredited;
						}
						bankFdTdrOutput.setTotalInterestReceived(closingBal - deposit);
						bankFdTdrOutput.setMaturityAmount(closingBal);
					} else {
						break;
					}

				}
				bankFDTDRLookup.setSerialNo(serialNo);
				bankFDTDRLookup.setReferenceDate(strRefDate);
				bankFDTDRLookup.setReferenceMonth(strRefMonth);
				bankFDTDRLookup.setFinancialYear(fiscalYear);
				bankFDTDRLookup.setOpeningBal(openingBal);
				bankFDTDRLookup.setInterestAccrued(interestAccrued);
				bankFDTDRLookup.setTotalInterestReceived(totalInterestAccrued);
				bankFDTDRLookup.setInterestCredited(interestCredited);
				bankFDTDRLookup.setClosingBalance(closingBal);
				bankFDTDRLookup.setDaysToMaturity(daysToMaturity);

				bankFDTDRLookupList.add(bankFDTDRLookup);
				cInterimDate.add(Calendar.MONTH, 1);
				dtInterimDate = cInterimDate.getTime();
				strInterimDate = sdfOutput.format(dtInterimDate);
				serialNo = serialNo + 1;

			}

			bankFdTdrOutput.setBankFdTdrLookup(bankFDTDRLookupList);
		} catch (Exception exp) {
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_BANK_FDSTDR,
					FinexaConstant.PRODUCT_CAL_BANK_FDSTDR_CODE, FinexaConstant.PRODUCT_CAL_BANK_FDSTDR_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return bankFdTdrOutput;
	}

}
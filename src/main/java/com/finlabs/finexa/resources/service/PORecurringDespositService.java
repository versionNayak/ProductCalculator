package com.finlabs.finexa.resources.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.finlabs.finexa.resources.dao.MasterDAO;
import com.finlabs.finexa.resources.dao.MasterDAOImplementation;
import com.finlabs.finexa.resources.exception.FinexaBussinessException;
import com.finlabs.finexa.resources.model.MasterPORecurringDeposit;
import com.finlabs.finexa.resources.model.PORecurringDeposit;
import com.finlabs.finexa.resources.model.PORecurringDepositLookup;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class PORecurringDespositService {

	public static final String SHOW_INTERST = "/bankFDSTDRCDCP.jsp";
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
	double totalInterestAccrued = 0.0;
	double interestCredit = 0.0;
	double totalInterestCredit = 0.0;
	long daysToMaturity = 0;
	Date dtLastDayOfMonth = new Date();
	Date dtMaturityDate = new Date();
	double displayInterestRate = 0.0;

	public PORecurringDeposit getRecurringDepositCalculatedList(double deposit, int yearsDays, int rdDepositFreq,
			int compundingFreq, Date depositDate) {

		PORecurringDeposit poRecurringDepositOutput = new PORecurringDeposit();
		try {
			double amountDeposited = deposit;
			int varTermMonths = 0;

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
			poRecurringDepositOutput.setMaturityDate(cMaturityDate.getTime());
			strInterimDate = sdfOutput.format(cDepositDate.getTime());
			List<PORecurringDepositLookup> poRDLookupList = new ArrayList<PORecurringDepositLookup>();
			int serialNo = 0;
			MasterDAO mastersukDao = new MasterDAOImplementation();
			List<MasterPORecurringDeposit> masterPORecurringDepositList = mastersukDao.getMasterPORecurringDepositRates();
			boolean checkInterestRate = false;
			for (MasterPORecurringDeposit masterPORD : masterPORecurringDepositList) {
				if ((depositDate.after(masterPORD.getStartDate()) && depositDate.before(masterPORD.getEndDate()))
						|| depositDate.equals(masterPORD.getStartDate()) || depositDate.equals(masterPORD.getEndDate())) {
					displayInterestRate = masterPORD.getInterestRate();
					checkInterestRate = true;
				}
				if (checkInterestRate) {
					break;
				}

			}
			poRecurringDepositOutput.setInterestRate(displayInterestRate);
			while (true) {
				PORecurringDepositLookup poRDLookup = new PORecurringDepositLookup();

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
				openingBal = closingBal;
				if (serialNo % rdDepositFreqConstantLookup == 0) {
					if (serialNo < varTermMonths) {
						amountDeposited = deposit;
					}
				}
				if (serialNo > 0) {
					interestAccured = (openingBal)
							* ((displayInterestRate) / compundingFreqInterestPaid / compundingFreqConstantLookup);
				}
				totalInterestAccrued = totalInterestAccrued + interestAccured;
				if (serialNo % compundingFreqConstantLookup == 0) {
					if (serialNo > 0) {
						interestCredit = totalInterestAccrued - totalInterestCredit;
					}
					totalInterestCredit = totalInterestCredit + interestCredit;

				}

				closingBal = interestCredit + openingBal + amountDeposited;
				if (dtLastDayOfMonth.after(cMaturityDate.getTime())) {

					serialNo = 0;
				}
				poRDLookup.setSerialNo(serialNo);
				poRDLookup.setReferenceDate(strRefDate);
				poRDLookup.setReferenceMonth(strRefMonth);
				poRDLookup.setFinancialYear(fiscalYear);
				poRDLookup.setOpeningBal(openingBal);
				poRDLookup.setAmountDeposited(amountDeposited);
				poRDLookup.setInterestAccrued(interestAccured);
				poRDLookup.setTotalInterestAccrued(totalInterestAccrued);
				poRDLookup.setClosingBalance(closingBal);
				poRDLookup.setDaysToMaturity(daysToMaturity);
				poRDLookup.setInterestCredited(interestCredit);
				poRDLookupList.add(poRDLookup);
				cInterimDate.add(Calendar.MONTH, 1);
				dtInterimDate = cInterimDate.getTime();
				strInterimDate = sdfOutput.format(dtInterimDate);
				if (dtLastDayOfMonth.after(cMaturityDate.getTime())) {
					break;
				}
				serialNo++;
				interestAccured = 0;
				amountDeposited = 0;
				interestCredit = 0;
			}

			poRecurringDepositOutput.setMaturityAmount(closingBal);
			poRecurringDepositOutput.setTotalAmountDeposited(deposit * rdDepositFreqInterestPaid * yearsDays);
			poRecurringDepositOutput
					.setTotalInterestReceived(closingBal - poRecurringDepositOutput.getTotalAmountDeposited());
			poRecurringDepositOutput.setPoRecurringLookupList(poRDLookupList);
		} catch (Exception exp) {
			// TODO Auto-generated catch block
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_PO_RD,
					FinexaConstant.PRODUCT_CAL_PO_RD_CODE, FinexaConstant.PRODUCT_CAL_PO_RD_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}

		return poRecurringDepositOutput;
	}

}

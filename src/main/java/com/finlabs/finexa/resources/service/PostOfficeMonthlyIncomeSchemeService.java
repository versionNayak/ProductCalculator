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
import com.finlabs.finexa.resources.model.MasterPostOfficeMonthlyIncomeScheme;
import com.finlabs.finexa.resources.model.PostOfficeMonthlyIncomeScheme;
import com.finlabs.finexa.resources.model.PostOfficeMonthlyIncomeSchemeLookup;
import com.finlabs.finexa.resources.util.FinanceUtil;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;


public class PostOfficeMonthlyIncomeSchemeService {
	Calendar cDepositDate = Calendar.getInstance();
	Calendar cMaturityDate = Calendar.getInstance();
	Calendar cInterimDate = Calendar.getInstance();
	String strInterimDate = "";

	SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MMM-yyyy");

	String strRefDate = "";
	String strRefMonth = "";
	String fiscalYear = "";
	double amountDeposited = 0.0;
	double interestReceived = 0.0;
	double totalInterestReceived = 0.0;
	long daysToMaturity = 0;
	Date dtLastDayOfMonth = new Date();
	Date dtMaturityDate = new Date();
	double displayInterestRate = 0.0;

	public PostOfficeMonthlyIncomeScheme getPostOfficeMISCal(double deposit, int years, int interestFrequency,
			Date depositDate) {
		PostOfficeMonthlyIncomeScheme postOfficeMis = new PostOfficeMonthlyIncomeScheme();
		try {
			int varTermMonths = (int) years * 12;
			int varInterestFreq = interestFrequency;
			int interestFreqConstant = varInterestFreq;
			int interestFreqConstantLookup = 12 / varInterestFreq;
			double varDeposit = deposit;
			double pv = -varDeposit;

			MasterDAO mastersukDao = new MasterDAOImplementation();
			List<MasterPostOfficeMonthlyIncomeScheme> masterPOMISList = mastersukDao.getMasterPOMISRates();
			boolean checkInterestRate = false;
			for (MasterPostOfficeMonthlyIncomeScheme masterPOMIS : masterPOMISList) {
				if ((depositDate.after(masterPOMIS.getStartDate()) && depositDate.before(masterPOMIS.getEndDate()))
						|| depositDate.equals(masterPOMIS.getStartDate()) || depositDate.equals(masterPOMIS.getEndDate())) {
					displayInterestRate = masterPOMIS.getInterestRate();
					checkInterestRate = true;
				}
				if (checkInterestRate) {
					break;
				}

			}
			postOfficeMis.setInterestRate(displayInterestRate);
			double varROI = displayInterestRate;
			double r = (((double) varROI)) / interestFreqConstant;
			double PMT = FinanceUtil.pmt(r, years * interestFreqConstant, pv, varDeposit, false);
			double totalInterest = PMT * interestFreqConstant * years;
			cDepositDate.setTime(depositDate);
			cMaturityDate.setTime(depositDate);
			cInterimDate.setTime(depositDate);
			cMaturityDate.add(Calendar.MONTH, varTermMonths);
			postOfficeMis.setInterestReceived(PMT);
			postOfficeMis.setTotalInterestReceived(totalInterest);
			postOfficeMis.setMaturityDate(cMaturityDate.getTime());
			strInterimDate = sdfOutput.format(cDepositDate.getTime());
			List<PostOfficeMonthlyIncomeSchemeLookup> postOfficeMISList = new ArrayList<PostOfficeMonthlyIncomeSchemeLookup>();
			int serialNo = 0;
			while (true) {
				PostOfficeMonthlyIncomeSchemeLookup postOfficeMISLookup = new PostOfficeMonthlyIncomeSchemeLookup();

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
				if (serialNo % interestFreqConstantLookup == 0) {
					if (serialNo > 0) {
						interestReceived = PMT;
					}
				}
				totalInterestReceived = totalInterestReceived + interestReceived;

				if (daysToMaturity <= 0) {
					amountDeposited = (int) pv;
				} else {
					if (serialNo < 1) {
						amountDeposited = varDeposit;
					}
				}

				postOfficeMISLookup.setSerialNo(serialNo);
				postOfficeMISLookup.setReferenceDate(strRefDate);
				postOfficeMISLookup.setReferenceMonth(strRefMonth);
				postOfficeMISLookup.setFinancialYear(fiscalYear);
				postOfficeMISLookup.setAmountDeposited(amountDeposited);
				postOfficeMISLookup.setInterestReceived(interestReceived);
				postOfficeMISLookup.setTotalInterestReceived(totalInterestReceived);
				postOfficeMISLookup.setDaysToMaturity(daysToMaturity);

				postOfficeMISList.add(postOfficeMISLookup);
				cInterimDate.add(Calendar.MONTH, 1);
				dtInterimDate = cInterimDate.getTime();
				serialNo++;
				interestReceived = 0;
				amountDeposited = 0;
				strInterimDate = sdfOutput.format(dtInterimDate);
				if (dtLastDayOfMonth.after(cMaturityDate.getTime()) || dtLastDayOfMonth.equals(cMaturityDate.getTime())) {
					break;
				}
			}

			postOfficeMis.setPostOfficeMISLookupList(postOfficeMISList);
		} catch (Exception exp) {
			// TODO Auto-generated catch block
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_PO_MIS,
					FinexaConstant.PRODUCT_CAL_PO_MIS_CODE, FinexaConstant.PRODUCT_CAL_PO_MIS_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return postOfficeMis;
	}

}
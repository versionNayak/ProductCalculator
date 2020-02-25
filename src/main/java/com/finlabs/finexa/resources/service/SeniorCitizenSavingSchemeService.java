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
import com.finlabs.finexa.resources.model.MasterSeniorCitizenSavingScheme;
import com.finlabs.finexa.resources.model.SeniorCitizenSavingScheme;
import com.finlabs.finexa.resources.model.SeniorCitizenSavingSchemeLookup;
import com.finlabs.finexa.resources.util.FinanceUtil;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class SeniorCitizenSavingSchemeService {
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
	double totalInterestReceived = 0.0;
	long daysToMaturity = 0;
	Date dtLastDayOfMonth = new Date();
	Date dtMaturityDate = new Date();

	public SeniorCitizenSavingScheme getSeniorCitizenSaingSchemeCal(double deposit, double years, int interestFrequency,
			Date depositDate) {
		SeniorCitizenSavingScheme senoirCitizenSch = new SeniorCitizenSavingScheme();
		try {
			double varTermYears = years;
			int varTermMonths = (int) years * 12;
			int varInterestFreq = interestFrequency;
			int interestFreqConstant = varInterestFreq;
			int interestFreqConstantLookup = 12 / varInterestFreq;

			Date varDepositDate = depositDate;
			MasterDAO mastersukDao = new MasterDAOImplementation();
			List<MasterSeniorCitizenSavingScheme> masterSeniorCitizenList = mastersukDao
					.getSeniorCitizenSavingSchemeInterestRates();
			boolean checkInterestRate = false;
			double varDeposit = deposit;
			double pv = -varDeposit;
			double varROI = 0.0;
			for (MasterSeniorCitizenSavingScheme masterSeniorCitizenScheme : masterSeniorCitizenList) {
				if ((varDepositDate.after(masterSeniorCitizenScheme.getStartDate())
						&& varDepositDate.before(masterSeniorCitizenScheme.getEndDate()))
						|| varDepositDate.equals(masterSeniorCitizenScheme.getStartDate())
						|| varDepositDate.equals(masterSeniorCitizenScheme.getEndDate())) {
					varROI = masterSeniorCitizenScheme.getInterestRate();
					checkInterestRate = true;
				}
				if (checkInterestRate) {
					break;
				}

			}
			senoirCitizenSch.setInterestRate(varROI);
			double r = (((double) varROI)) / interestFreqConstant;
			double PMT = FinanceUtil.pmt(r, varTermYears * interestFreqConstant, pv, varDeposit, false);
			double totalInterest = PMT * interestFreqConstant * varTermYears;
			cDepositDate.setTime(varDepositDate);
			cMaturityDate.setTime(varDepositDate);
			cInterimDate.setTime(varDepositDate);
			cMaturityDate.add(Calendar.MONTH, varTermMonths);
			senoirCitizenSch.setInterestReceived(PMT);
			senoirCitizenSch.setTotalInterestReceived(totalInterest);
			senoirCitizenSch.setMaturityDate(cMaturityDate.getTime());
			strInterimDate = sdfOutput.format(cDepositDate.getTime());
			List<SeniorCitizenSavingSchemeLookup> seniorCitizenSavingSchemeLookupList = new ArrayList<SeniorCitizenSavingSchemeLookup>();
			int serialNo = 0;

			while (true) {
				SeniorCitizenSavingSchemeLookup seniorCitizenSavingSchemeLookup = new SeniorCitizenSavingSchemeLookup();

				Date dtInterimDate = cInterimDate.getTime();
				strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);
				try {
					dtLastDayOfMonth = sdfOutput.parse(strRefDate);
					long diff = cMaturityDate.getTime().getTime() - dtLastDayOfMonth.getTime();
					daysToMaturity = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				for (MasterSeniorCitizenSavingScheme masterSeniorCitizenScheme : masterSeniorCitizenList) {
					if ((varDepositDate.after(masterSeniorCitizenScheme.getStartDate())
							&& varDepositDate.before(masterSeniorCitizenScheme.getEndDate()))
							|| varDepositDate.equals(masterSeniorCitizenScheme.getStartDate())
							|| varDepositDate.equals(masterSeniorCitizenScheme.getEndDate())) {
						varROI = masterSeniorCitizenScheme.getInterestRate();
						checkInterestRate = true;
					}
					if (checkInterestRate) {
						break;
					}

				}

				strRefMonth = strRefDate.substring(3);
				fiscalYear = FinexaDateUtil.getFiscalYear(dtInterimDate);
				if (serialNo % interestFreqConstantLookup == 0) {
					if (serialNo > 0) {
						interestReceived = PMT;
						totalInterestReceived = totalInterestReceived + interestReceived;

					}
				} else {
					interestReceived = 0;
				}

				if (dtLastDayOfMonth.after(cMaturityDate.getTime()) || dtLastDayOfMonth.equals(cMaturityDate.getTime())) {

				}
				if (daysToMaturity <= 0) {
					amountDeposited = pv;
				} else {
					if (serialNo < 1) {
						amountDeposited = varDeposit;
					} else {
						amountDeposited = 0;
					}
				}

				seniorCitizenSavingSchemeLookup.setSerialNo(serialNo);
				seniorCitizenSavingSchemeLookup.setReferenceDate(strRefDate);
				seniorCitizenSavingSchemeLookup.setReferenceMonth(strRefMonth);
				seniorCitizenSavingSchemeLookup.setFinancialYear(fiscalYear);
				seniorCitizenSavingSchemeLookup.setAmountDeposited(amountDeposited);
				seniorCitizenSavingSchemeLookup.setInterestReceived(interestReceived);
				seniorCitizenSavingSchemeLookup.setTotalInterestReceived(totalInterestReceived);
				seniorCitizenSavingSchemeLookup.setDaysToMaturity(daysToMaturity);

				seniorCitizenSavingSchemeLookupList.add(seniorCitizenSavingSchemeLookup);
				cInterimDate.add(Calendar.MONTH, 1);
				dtInterimDate = cInterimDate.getTime();
				strInterimDate = sdfOutput.format(dtInterimDate);
				serialNo++;
				checkInterestRate = false;
				if (dtLastDayOfMonth.after(cMaturityDate.getTime()) || dtLastDayOfMonth.equals(cMaturityDate.getTime())) {
					break;
				}
			}

			senoirCitizenSch.setSeniorCitizenSavingSchemeLookupsList(seniorCitizenSavingSchemeLookupList);
		} catch (Exception exp) {
			// TODO Auto-generated catch block
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_SENIOR_CITIZEN_SCHEME,
					FinexaConstant.PRODUCT_CAL_SENIOR_CITIZEN_SCHEME_CODE, FinexaConstant.PRODUCT_CAL_SENIOR_CITIZEN_SCHEME_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return senoirCitizenSch;
	}

}
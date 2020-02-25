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
import com.finlabs.finexa.resources.model.NPSCAL;
import com.finlabs.finexa.resources.model.NPSLookup;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class NPSCalService {
	public static final int DEFAULT_RETIREMENT_AGE = 60;
	public static final int ALREADY_RETIRED = 0;
	Calendar npsPlanDate = Calendar.getInstance();
	Calendar employeeDepositLastDate = Calendar.getInstance();
	Calendar employerDepositLastDate = Calendar.getInstance();
	Calendar retirementAgeDate = Calendar.getInstance();
	String strInterimDate = "";
	SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MMM-yyyy");

	String strRefDate = "";
	String strRefMonth = "";
	String fiscalYear = "";
	double openingBal = 0.0;
	double interestAccured = 0.0;
	double totalInterestAccured = 0.0;
	double totalInterestCredit = 0.0;
	long daysToMaturity = 0;
	Date dtLastDayOfMonth = new Date();
	Date dtMaturityDate = new Date();
	int varTermMonths = 0;

	public NPSCAL calculateNPSValues(Date clientDOB, double currNPSBal, double empolyeeCont, int empolyeeContFreq,
			double empolyerCont, int empolyerContFreq, double assetClassEAll, double assetClassCAll,
			double assetClassGAll, int empolyeeContAge, int empolyerContAge, int retirementAge, int planType) {

		// retirement age to be defaulted to 60 in case it comes 0
		if (retirementAge == ALREADY_RETIRED) {
			retirementAge = DEFAULT_RETIREMENT_AGE;
		}
		NPSCAL apsCal = new NPSCAL();
		List<NPSLookup> npsLookupList = new ArrayList<NPSLookup>();
		try {
			int employeeFreqCount = 12 / empolyeeContFreq;

			int employerFreqCount = 12 / empolyerContFreq;

			openingBal = 0;
			employeeDepositLastDate.setTime(clientDOB);
			employerDepositLastDate.setTime(clientDOB);
			retirementAgeDate.setTime(clientDOB);
			employeeDepositLastDate.add(Calendar.MONTH, (int) (empolyeeContAge * 12));
			employerDepositLastDate.add(Calendar.MONTH, (int) ((empolyerContAge * 12)));
			employeeDepositLastDate.set(Calendar.MILLISECOND, 0);
			employeeDepositLastDate.set(Calendar.SECOND, 0);
			employeeDepositLastDate.set(Calendar.MINUTE, 0);
			employeeDepositLastDate.set(Calendar.HOUR, 0);
			employerDepositLastDate.set(Calendar.MILLISECOND, 0);
			employerDepositLastDate.set(Calendar.SECOND, 0);
			employerDepositLastDate.set(Calendar.MINUTE, 0);
			employerDepositLastDate.set(Calendar.HOUR, 0);

			npsPlanDate.set(Calendar.DAY_OF_MONTH, 1);
			npsPlanDate.set(Calendar.MONTH, 3);
			npsPlanDate.set(Calendar.YEAR, npsPlanDate.get(Calendar.YEAR));
			npsPlanDate.set(Calendar.MILLISECOND, 0);
			npsPlanDate.set(Calendar.SECOND, 0);
			npsPlanDate.set(Calendar.MINUTE, 0);
			npsPlanDate.set(Calendar.HOUR, 0);
			retirementAgeDate.add(Calendar.MONTH, (int) (retirementAge * 12));
			retirementAgeDate.set(Calendar.DAY_OF_MONTH, retirementAgeDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			retirementAgeDate.set(Calendar.MILLISECOND, 0);
			retirementAgeDate.set(Calendar.SECOND, 0);
			retirementAgeDate.set(Calendar.MINUTE, 0);
			retirementAgeDate.set(Calendar.HOUR, 0);
			int npsDisplaySerial = 0;
			int yearCount = 1;
			int clientAge = 1;
			double employeeContribution = 0;
			double employerContribution = 0;
			double assetEFundValue = 0;
			double assetCFundValue = 0;
			double assetGFundValue = 0;
			double npsReturnEarn = 0;
			double npsClosingBal = 0;
			double totalEmployeeContribution = 0;
			double totalEmployerContribution = 0;

			MasterDAO masterNPSDao = new MasterDAOImplementation();

			double assetClassEExp = masterNPSDao.getMasterPONSCRates(npsPlanDate.getTime(), "Asset Class E");
			double assetClassCExp = masterNPSDao.getMasterPONSCRates(npsPlanDate.getTime(), "Asset Class C");
			double assetClassGExp = masterNPSDao.getMasterPONSCRates(npsPlanDate.getTime(), "Asset Class G");

			apsCal.setAssetClassEExp(assetClassEExp);
			apsCal.setAssetClassCExp(assetClassCExp);
			apsCal.setAssetClassGExp(assetClassGExp);
			double expectedReturns = masterNPSDao.getMasterPONSCRates(npsPlanDate.getTime(), "Auto Plan");
			double annualIncrease = masterNPSDao.getMasterPONSCIncomeCagr(npsPlanDate.getTime(), "Salary Income");
			while (true) {

				NPSLookup npsLookup = new NPSLookup();
				if (npsDisplaySerial == 0) {
					openingBal = currNPSBal;
				} else {
					openingBal = npsClosingBal;
				}
				if (npsDisplaySerial > 0 && npsDisplaySerial % (12) == 0) {
					yearCount = yearCount + 1;
				}
				if (clientAge == retirementAge) {
					break;
				}

				if (planType == 2) {
					if ((npsDisplaySerial % employeeFreqCount) == 0) {
						if (npsPlanDate.getTime().compareTo(employeeDepositLastDate.getTime()) <= 0) {
							employeeContribution = empolyeeCont * Math.pow((1 + (annualIncrease)), (yearCount - 1));

						} else {
							employeeContribution = 0.0;

						}
					} else {
						employeeContribution = 0.0;

					}
					totalEmployeeContribution = totalEmployeeContribution + employeeContribution;
					if ((npsDisplaySerial % employerFreqCount) == 0) {
						if (npsPlanDate.getTime().compareTo(employerDepositLastDate.getTime()) <= 0) {
							employerContribution = empolyerCont * Math.pow((1 + (annualIncrease)), (yearCount - 1));
						} else {
							employerContribution = 0.0;
						}
					} else {
						employerContribution = 0.0;

					}
					totalEmployerContribution = totalEmployerContribution + employerContribution;
					if (npsDisplaySerial == 0) {
						assetEFundValue = (openingBal + employeeContribution + employerContribution)
								* (assetClassEAll / 100);
						assetCFundValue = (openingBal + employeeContribution + employerContribution)
								* (assetClassCAll / 100);
						assetGFundValue = (openingBal + employeeContribution + employerContribution)
								* (assetClassGAll / 100);
					} else {
						assetEFundValue = (openingBal + employeeContribution + employerContribution)
								* (assetClassEAll / 100) * (1 + ((assetClassEExp) / 12));
						assetCFundValue = (openingBal + employeeContribution + employerContribution)
								* (assetClassCAll / 100) * (1 + ((assetClassCExp) / 12));
						assetGFundValue = (openingBal + employeeContribution + employerContribution)
								* (assetClassGAll / 100) * (1 + ((assetClassGExp) / 12));
					}

					npsReturnEarn = (assetEFundValue + assetCFundValue + assetGFundValue)
							- (openingBal + employeeContribution + employerContribution);
					npsClosingBal = (assetEFundValue + assetCFundValue + assetGFundValue);

					npsLookup.setAssetClassEFundValue(assetEFundValue);
					npsLookup.setAssetClassCFundValue(assetCFundValue);
					npsLookup.setAssetClassGFundValue(assetGFundValue);

				} else {
					if ((npsDisplaySerial % employeeFreqCount) == 0) {
						if (npsPlanDate.getTime().compareTo(employeeDepositLastDate.getTime()) <= 0) {
							employeeContribution = empolyeeCont * Math.pow((1 + (annualIncrease)), (yearCount - 1));

						} else {
							employeeContribution = 0.0;

						}
					} else {
						employeeContribution = 0.0;

					}
					totalEmployeeContribution = totalEmployeeContribution + employeeContribution;
					if ((npsDisplaySerial % employerFreqCount) == 0) {
						if (npsPlanDate.getTime().compareTo(employerDepositLastDate.getTime()) <= 0) {
							employerContribution = empolyerCont * Math.pow((1 + (annualIncrease)), (yearCount - 1));
						} else {
							employerContribution = 0.0;
						}
					} else {
						employerContribution = 0.0;

					}
					totalEmployerContribution = totalEmployerContribution + employerContribution;
					npsReturnEarn = (openingBal + employeeContribution + employerContribution)
							* ((expectedReturns) / 12);
					npsClosingBal = (openingBal + employeeContribution + employerContribution) + npsReturnEarn;
				}
				java.util.Date dtInterimDate = npsPlanDate.getTime();
				strInterimDate = sdfOutput.format(npsPlanDate.getTime());
				strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);

				try {
					dtLastDayOfMonth = sdfOutput.parse(strRefDate);
					long diff = dtLastDayOfMonth.getTime() - clientDOB.getTime();
					daysToMaturity = (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
					clientAge = (int) (daysToMaturity / 365.25);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (retirementAgeDate.getTime().compareTo(dtLastDayOfMonth) == 0) {
					apsCal.setTotalNpsCorpus(npsClosingBal);
					apsCal.setMaxWithdrawCorpus(npsClosingBal * (60 / (double) 100));
				}
				fiscalYear = FinexaDateUtil.getFiscalYear(dtInterimDate);
				npsLookup.setSerialNo(npsDisplaySerial + 1);
				npsLookup.setReferenceDate(strRefDate);
				npsLookup.setYearCount(yearCount);
				npsLookup.setFinancialYear(fiscalYear);
				npsLookup.setClientAge(clientAge);
				npsLookup.setOpeningBal(Math.round(openingBal));
				npsLookup.setEmployeeCont(employeeContribution);
				npsLookup.setEmployerCont(employerContribution);

				npsLookup.setNpsReturnEarn(npsReturnEarn);
				npsLookup.setNpsClosingBalance(npsClosingBal);
				npsLookupList.add(npsLookup);
				npsPlanDate.add(Calendar.MONTH, 1);
				npsDisplaySerial++;
			}
			apsCal.setTotalEmployeeContribution(totalEmployeeContribution);
			apsCal.setTotalEmployerContribution(totalEmployerContribution);
			apsCal.setAnnuityAvailCorpus(apsCal.getTotalNpsCorpus() - apsCal.getMaxWithdrawCorpus());
		} catch (Exception exp) {
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_NPS,
					FinexaConstant.PRODUCT_CAL_NPS_CODE, FinexaConstant.PRODUCT_CAL_NPS_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		apsCal.setNpsLookupList(npsLookupList);
		return apsCal;
	}

}

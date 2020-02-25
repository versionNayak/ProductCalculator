package com.finlabs.finexa.resources.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.finlabs.finexa.resources.dao.MasterDAO;
import com.finlabs.finexa.resources.dao.MasterDAOImplementation;
import com.finlabs.finexa.resources.exception.FinexaBussinessException;
import com.finlabs.finexa.resources.model.AtalPensionYojana;
import com.finlabs.finexa.resources.model.AtalPensionYojanaLookup;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class AtalPensionYojanaService {
	public static final int DEFAULT_RETIREMENT_AGE = 60;
	public static final int ALREADY_RETIRED = 0;
	Calendar contrStartDateCal = Calendar.getInstance();
	Calendar contrLastDate = Calendar.getInstance();
	Calendar annuityAmountDate = Calendar.getInstance();
	Calendar amountToNomineeDate = Calendar.getInstance();
	String strInterimDate = "";
	SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MMM-yyyy");

	String strLastDayMonth = "";
	String strRefMonth = "";
	String fiscalYear = "";

	double amountInvested = 0.0;
	double annuityAmount = 0.0;
	double amountToNominee = 0.0;
	double freqContrAmount = 0.0;
	int clientAge = 0;

	public AtalPensionYojana getAtalPensionYojanaCal(Date clientDOB, int contrFreq, double monthlyPenReq,
			int retirementAge, Date contrStartDate, int lifeExptenYear) {
		
		
		// retirement age to be defaulted to 60 in case it comes 0
		if (retirementAge == ALREADY_RETIRED) {
			retirementAge = DEFAULT_RETIREMENT_AGE;
		}
		
		AtalPensionYojana atalPensionYojana = new AtalPensionYojana();

		List<AtalPensionYojanaLookup> atalPensionYojanaLookupList = new ArrayList<AtalPensionYojanaLookup>();
		int serialNo = 0;

		try {
			long diff = contrStartDate.getTime() - clientDOB.getTime();
			long daysToMaturity = (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
			clientAge = (int) (daysToMaturity / 365.25);
			atalPensionYojana.setClientAge(clientAge);
			int contrPeriod = retirementAge - clientAge;

			contrLastDate.setTime(contrStartDate);
			contrStartDateCal.setTime(contrStartDate);

			contrLastDate.add(Calendar.MONTH, (int) ((contrPeriod * 12)));
			contrLastDate.set(Calendar.DAY_OF_MONTH, contrLastDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			annuityAmountDate.setTime(contrLastDate.getTime());
			annuityAmountDate.add(Calendar.MONTH, (int) (((lifeExptenYear - retirementAge) * 12)));
			amountToNomineeDate.setTime(annuityAmountDate.getTime());
			amountToNomineeDate.add(Calendar.MONTH, 1);
			contrStartDateCal.set(Calendar.MILLISECOND, 0);
			contrStartDateCal.set(Calendar.SECOND, 0);
			contrStartDateCal.set(Calendar.MINUTE, 0);
			contrStartDateCal.set(Calendar.HOUR, 0);
			contrLastDate.set(Calendar.MILLISECOND, 0);
			contrLastDate.set(Calendar.SECOND, 0);
			contrLastDate.set(Calendar.MINUTE, 0);
			contrLastDate.set(Calendar.HOUR, 0);
			annuityAmountDate.set(Calendar.MILLISECOND, 0);
			annuityAmountDate.set(Calendar.SECOND, 0);
			annuityAmountDate.set(Calendar.MINUTE, 0);
			annuityAmountDate.set(Calendar.HOUR, 0);
			amountToNomineeDate.set(Calendar.MILLISECOND, 0);
			amountToNomineeDate.set(Calendar.SECOND, 0);
			amountToNomineeDate.set(Calendar.MINUTE, 0);
			amountToNomineeDate.set(Calendar.HOUR, 0);
			amountToNomineeDate.set(Calendar.DAY_OF_MONTH, amountToNomineeDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			// Master Data
			MasterDAO masteratalYojanaDao = new MasterDAOImplementation();
			freqContrAmount = masteratalYojanaDao.getMasterAtalPensionYojanaContribution(clientAge, contrPeriod,
					contrFreq, monthlyPenReq);
			Date dtLastDayOfMonth = contrStartDateCal.getTime();
			while (dtLastDayOfMonth.compareTo(amountToNomineeDate.getTime()) == -1) {
				AtalPensionYojanaLookup atalPensionYojanaLookup = new AtalPensionYojanaLookup();
				strInterimDate = sdfOutput.format(contrStartDate.getTime());
				String strRefMonth = String.valueOf(contrStartDateCal.get(Calendar.MONTH) + 1);
				String fiscalYear = FinexaDateUtil.getFiscalYear(contrStartDateCal.getTime());
				strLastDayMonth = FinexaDateUtil.getLastDayOfTheMonth(sdfOutput.format(contrStartDateCal.getTime()));

				dtLastDayOfMonth = sdfOutput.parse(strLastDayMonth);

				if (dtLastDayOfMonth.compareTo(contrLastDate.getTime()) == -1) {
					if (serialNo % (12 / contrFreq) == 0) {
						amountInvested = freqContrAmount;
					} else {
						amountInvested = 0;
					}
				} else {
					amountInvested = 0;
				}

				if (dtLastDayOfMonth.compareTo(contrLastDate.getTime()) == 1
						&& dtLastDayOfMonth.compareTo(annuityAmountDate.getTime()) == -1) {
					annuityAmount = monthlyPenReq;
				}

				if (dtLastDayOfMonth.compareTo(amountToNomineeDate.getTime()) == 0) {
					// Master Data;
					annuityAmount = 0;
					amountToNominee = masteratalYojanaDao.getAtalPensionYojanaCorpusValue(monthlyPenReq);
				}

				diff = dtLastDayOfMonth.getTime() - clientDOB.getTime();
				daysToMaturity = (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
				clientAge = (int) (daysToMaturity / 365.25);
				atalPensionYojanaLookup.setClientAge(clientAge);
				atalPensionYojanaLookup.setReferenceDate(strLastDayMonth);
				atalPensionYojanaLookup.setSerialNo(serialNo + 1);
				atalPensionYojanaLookup.setReferenceMonth(strRefMonth);
				atalPensionYojanaLookup.setFinancialYear(fiscalYear);
				atalPensionYojanaLookup.setAmountInvested(amountInvested);
				atalPensionYojanaLookup.setAnnuityAmount(annuityAmount);
				atalPensionYojanaLookup.setAmountToNominee(amountToNominee);

				atalPensionYojanaLookupList.add(atalPensionYojanaLookup);
				contrStartDateCal.add(Calendar.MONTH, 1);
				serialNo++;
			}

			atalPensionYojana.setFreqContrValue(freqContrAmount);
			atalPensionYojana
					.setTotalContr(freqContrAmount * contrFreq * (retirementAge - atalPensionYojana.getClientAge()));
			atalPensionYojana.setContrPeriod((retirementAge - atalPensionYojana.getClientAge()));
			atalPensionYojana.setContrEndDate(contrLastDate.getTime());
			atalPensionYojana.setAmountToNominee(amountToNominee);
			atalPensionYojana.setAnnuityReqLifeExpten((lifeExptenYear - retirementAge) * monthlyPenReq * 12);
			atalPensionYojana.setAtalPensionYojanaLookupList(atalPensionYojanaLookupList);

		} catch (Exception exp) {
			FinexaBussinessException finexaBuss = new FinexaBussinessException(
					FinexaConstant.PRODUCT_CAL_ATAL_PENSION_YOJANA, FinexaConstant.PRODUCT_CAL_ATAL_PENSION_YOJANA_CODE,
					FinexaConstant.PRODUCT_CAL_ATAL_PENSION_YOJANA_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return atalPensionYojana;
	}
}

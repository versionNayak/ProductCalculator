package com.finlabs.finexa.resources.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.functions.FinanceLib;

import com.finlabs.finexa.resources.dao.MasterDAO;
import com.finlabs.finexa.resources.dao.MasterDAOImplementation;
import com.finlabs.finexa.resources.exception.FinexaBussinessException;
import com.finlabs.finexa.resources.model.Annuity2ProductCalculator;
import com.finlabs.finexa.resources.model.Annuity2ProductLookup;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class Annuity2ProductService {

	Calendar annuityStartCal = Calendar.getInstance();
	Calendar annuityEndCalSelf = Calendar.getInstance();
	Calendar annuityEndCalSpouse = Calendar.getInstance();
	String strInterimDate = "";
	SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MMMM-yyyy");

	public Annuity2ProductCalculator getAnnuityProductValues(Date clientDOB, double pensionableCorpus,
			double annuityRate, double lifeExpectancySelf, double lifeExpectancySpouse, int annuityPayoutFreq,
			Date annuityStartDate, int annuityType, double annuityGrowthRate, int annuityId) {
		Annuity2ProductCalculator annuity2Cal = new Annuity2ProductCalculator();
		List<Annuity2ProductLookup> annuity2LookkupList = new ArrayList<Annuity2ProductLookup>();
		try {
			annuity2Cal.setAnnuityT2BackEndDt(pensionableCorpus);
			annuity2Cal.setAnnuityT4BackEndDt(pensionableCorpus);
			annuityStartCal.setTime(annuityStartDate);
			annuityEndCalSelf.setTime(clientDOB);
			annuityEndCalSpouse.setTime(clientDOB);
			annuityEndCalSelf.add(Calendar.MONTH, (int) (lifeExpectancySelf * 12));
			annuityEndCalSpouse.add(Calendar.MONTH, (int) ((lifeExpectancySpouse * 12)));
			annuityStartCal.set(Calendar.MILLISECOND, 0);
			annuityStartCal.set(Calendar.SECOND, 0);
			annuityStartCal.set(Calendar.MINUTE, 0);
			annuityStartCal.set(Calendar.HOUR, 0);
			annuityEndCalSpouse.set(Calendar.MILLISECOND, 0);
			annuityEndCalSpouse.set(Calendar.SECOND, 0);
			annuityEndCalSpouse.set(Calendar.MINUTE, 0);
			annuityEndCalSpouse.set(Calendar.HOUR, 0);
			String annuitySDateLastdayOfMonth = "";

			double an1pensionedRec = 0;
			double an1totalPensionedRec = 0;
			double an1corpusPaidBack = 0.0;

			double an2pensionedRec = 0;
			double an2totalPensionedRec = 0;
			double an2corpusPaidBack = 0.0;

			double an3pensionedRec = 0;
			double an3totalPensionedRec = 0;
			double an3corpusPaidBack = 0.0;

			double an4pensionedRec = 0;
			double an4totalPensionedRec = 0;
			// double an4corpusPaidBack = 0.0;

			double an5pensionedRec = 0;
			double an5totalPensionedRec = 0;
			double an5corpusPaidBack = 0.0;

			double epspensionedRec = 0;
			double epstotalPensionedRec = 0;

			double totalMonthlyBasicDA = 0;

			double monthlyPension = 0;
			int displaySerialNo = 1;
			int freqSerialNo = 1;
			int freqCount = 1;
			int freqPeriod = 1;
			int yearCount = 1;
			freqCount = annuityPayoutFreq;
			freqPeriod = 12 / annuityPayoutFreq;
			annuityEndCalSpouse.set(Calendar.DAY_OF_MONTH, annuityEndCalSpouse.getActualMaximum(Calendar.DAY_OF_MONTH));
			annuityEndCalSelf.set(Calendar.DAY_OF_MONTH, annuityEndCalSpouse.getActualMaximum(Calendar.DAY_OF_MONTH));
			annuity2Cal.setAnnuityT1EndDate(annuityEndCalSelf.getTime());
			annuity2Cal.setAnnuityT2EndDate(annuityEndCalSelf.getTime());

			MasterDAO masterClientEpfDao = new MasterDAOImplementation();

			if (annuityType == 6) {
				double epfValues[] = masterClientEpfDao.getClientBasicDAById(annuityId);
				Calendar planningDate = Calendar.getInstance();
				Calendar dobTillwithdrawelAge = Calendar.getInstance();
				dobTillwithdrawelAge.setTime(clientDOB);
				dobTillwithdrawelAge.add(Calendar.MONTH, (int) epfValues[2] * 12);
				// System.out.println(dobTillwithdrawelAge.getTime());
				// System.out.println(planningDate.getTime());
				totalMonthlyBasicDA = FinanceLib.fv(epfValues[1],
						(double) dobTillwithdrawelAge.get(Calendar.YEAR) - planningDate.get(Calendar.YEAR) - 1, 0.0,
						-epfValues[0], false) * 12;
				annuity2Cal.setMonthlyBasicDA(totalMonthlyBasicDA);
				monthlyPension = Math.min(15000, totalMonthlyBasicDA) * Math.min(35, epfValues[3]) / 70;
			}

			annuity2Cal.setMonthlyPension(monthlyPension);
			if (annuityEndCalSpouse.getTime().compareTo(annuityEndCalSelf.getTime()) > 0) {
				annuity2Cal.setAnnuityT3EndDate(annuityEndCalSpouse.getTime());
				annuity2Cal.setAnnuityT4EndDate(annuityEndCalSpouse.getTime());
				annuity2Cal.setEpsEndDate(annuityEndCalSpouse.getTime());
			} else {
				annuity2Cal.setAnnuityT3EndDate(annuityEndCalSelf.getTime());
				annuity2Cal.setAnnuityT4EndDate(annuityEndCalSelf.getTime());
				annuity2Cal.setEpsEndDate(annuityEndCalSelf.getTime());
			}
			annuity2Cal.setAnnuityT5EndDate(annuityEndCalSelf.getTime());
			// System.out.println(annuityStartCal.getTime());
			// System.out.println(annuityEndCalSelf.getTime());
			// System.out.println(annuityEndCalSpouse.getTime());
			while (annuityStartCal.getTime().compareTo(annuityEndCalSpouse.getTime()) <= 0) {
				Annuity2ProductLookup annuity2Lookkup = new Annuity2ProductLookup();
				strInterimDate = sdfOutput.format(annuityStartCal.getTime());
				String strRefMonth = String.valueOf(annuityStartCal.get(Calendar.MONTH) + 1);
				String fiscalYear = FinexaDateUtil.getFiscalYear(annuityStartCal.getTime());
				if (freqSerialNo % freqPeriod == 0 || displaySerialNo == 1) {
					// System.out.println(displaySerialNo);
					// System.out.println(annuityStartCal.getTime());
					// System.out.println(annuityEndCalSelf.getTime());
					if (annuityStartCal.getTime().compareTo(annuityEndCalSelf.getTime()) <= 0) {
						an1pensionedRec = pensionableCorpus * (annuityRate) / freqCount;
						an1totalPensionedRec = an1totalPensionedRec + an1pensionedRec;
						if (annuityStartCal.getTime().compareTo(annuityEndCalSelf.getTime()) == 0) {
							an1corpusPaidBack = annuity2Cal.getAnnuityT1BackEndDt();
							annuity2Cal.setAnnuityT1TotalPenRec(an1totalPensionedRec);
						}
						/***Code added By Debolina*** For Pension Cirrection ***/
						if(annuityType == FinexaConstant.ANNUITY_TYPE_1) {
							annuity2Lookkup.setAnnuityT1PensionRec(an1pensionedRec);
						} else {
							annuity2Lookkup.setAnnuityT1PensionRec(0);
						}
						
						annuity2Lookkup.setAnnuityT1CorpusPaidBk(an1corpusPaidBack);

						an2pensionedRec = pensionableCorpus * (annuityRate) / freqCount;
						an2totalPensionedRec = an2totalPensionedRec + an2pensionedRec;
						if (annuityStartCal.getTime().compareTo(annuityEndCalSelf.getTime()) == 0) {
							an2corpusPaidBack = annuity2Cal.getAnnuityT2BackEndDt();
							annuity2Cal.setAnnuityT2TotalPenRec(an2totalPensionedRec);
							annuity2Lookkup.setAnnuityT2CorpusPaidBk(an2corpusPaidBack);
						}
						if(annuityType == FinexaConstant.ANNUITY_TYPE_2) {
							annuity2Lookkup.setAnnuityT2PensionRec(an2pensionedRec);
						} else {
							annuity2Lookkup.setAnnuityT2PensionRec(0);
						}
						

					}

					an3pensionedRec = pensionableCorpus * (annuityRate) / freqCount;
					an3totalPensionedRec = an3totalPensionedRec + an3pensionedRec;
					an3corpusPaidBack = annuity2Cal.getAnnuityT3BackEndDt();
					if(annuityType == FinexaConstant.ANNUITY_TYPE_3) {
						annuity2Lookkup.setAnnuityT3PensionRec(an3pensionedRec);
					} else {
						annuity2Lookkup.setAnnuityT3PensionRec(0);
					}
					annuity2Lookkup.setAnnuityT3CorpusPaidBk(an3corpusPaidBack);

					an4pensionedRec = pensionableCorpus * (annuityRate) / freqCount;
					an4totalPensionedRec = an4totalPensionedRec + an4pensionedRec;
					// an4corpusPaidBack =
					// annuity2Lookkup.getAnnuityT3CorpusPaidBk();
					if(annuityType == FinexaConstant.ANNUITY_TYPE_4) {
						annuity2Lookkup.setAnnuityT4PensionRec(an4pensionedRec);
					} else {
						annuity2Lookkup.setAnnuityT4PensionRec(0);
					}
			

					an5pensionedRec = pensionableCorpus * (annuityRate) / freqCount
							* (Math.pow(1 + (annuityGrowthRate), yearCount - 1));
					an5totalPensionedRec = an5totalPensionedRec + an5pensionedRec;
					an5corpusPaidBack = annuity2Cal.getAnnuityT5BackEndDt();
					if(annuityType == FinexaConstant.ANNUITY_TYPE_5) {
						annuity2Lookkup.setAnnuityT5PensionRec(an5pensionedRec);
					} else {
						annuity2Lookkup.setAnnuityT5PensionRec(0);
					}
					
					annuity2Lookkup.setAnnuityT5CorpusPaidBk(an5corpusPaidBack);

					freqSerialNo = 0;
				}

				epspensionedRec = monthlyPension;
				epstotalPensionedRec = epstotalPensionedRec + epspensionedRec;

				annuity2Lookkup.setEpsAnnuityPensionRec(epspensionedRec);
				if (annuityStartCal.getTime().compareTo(annuityEndCalSpouse.getTime()) == 0) {
					annuity2Cal.setAnnuityT3TotalPenRec(an3totalPensionedRec);
					annuity2Cal.setAnnuityT4TotalPenRec(an4totalPensionedRec);
					annuity2Cal.setAnnuityT5TotalPenRec(an5totalPensionedRec);
					annuity2Cal.setEpsTotalPenRec(epstotalPensionedRec);
				}
				if (annuityStartCal.getTime().compareTo(annuityEndCalSpouse.getTime()) == 0) {
					annuity2Lookkup.setAnnuityT4CorpusPaidBk(pensionableCorpus);
				}
				annuitySDateLastdayOfMonth = FinexaDateUtil
						.getLastDayOfTheMonth(sdfOutput.format(annuityStartCal.getTime()));
				Date dtLastDayOfMonth = null;
				try {
					dtLastDayOfMonth = sdfOutput.parse(annuitySDateLastdayOfMonth);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				annuity2Lookkup.setSerialNumber(displaySerialNo);
				annuity2Lookkup.setRefDate(dtLastDayOfMonth);
				annuity2Lookkup.setReferenceMonth(strRefMonth);
				annuity2Lookkup.setFinancialYear(fiscalYear);
				freqSerialNo++;
				if (displaySerialNo % 12 == 0) {
					yearCount = yearCount + 1;
				}

				annuity2LookkupList.add(annuity2Lookkup);
				annuityStartCal.add(Calendar.MONTH, 1);
				annuityStartCal.set(Calendar.DAY_OF_MONTH, annuityStartCal.getActualMaximum(Calendar.DAY_OF_MONTH));
				displaySerialNo++;
				annuity2Cal.setAnnuityLookupList(annuity2LookkupList);

			}
		} catch (Exception exp) {
			FinexaBussinessException finexaBuss = new FinexaBussinessException(
					FinexaConstant.PRODUCT_CAL_ANNUITY_PRODUCT, FinexaConstant.PRODUCT_CAL_ANNUITY_PRODUCT_CODE,
					FinexaConstant.PRODUCT_CAL_ANNUITY_PRODUCT_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return annuity2Cal;
	}
}

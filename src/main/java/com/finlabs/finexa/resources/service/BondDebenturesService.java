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
import com.finlabs.finexa.resources.model.BankBondDebenturesLookup;
import com.finlabs.finexa.resources.model.BondDebentures;
import com.finlabs.finexa.resources.util.FinanceUtil;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class BondDebenturesService {

	Calendar cInvestmentDate = Calendar.getInstance();
	Calendar cDepositDate = Calendar.getInstance();
	Calendar cMaturityDate = Calendar.getInstance();
	Calendar cInterimDate = Calendar.getInstance();
	String strInterimDate = "";
	SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MMMM-yyyy");
	List<BondDebentures> bondDebenturesLookupList = new ArrayList<BondDebentures>();
	Calendar ctodaysDate = Calendar.getInstance();
	Calendar maturDate = Calendar.getInstance();

	public BondDebentures calculateDebenturesValue(String tenureType, Date investmentDate, String tenure,
			int coupounPayoutFrequency, double interestCouponRate, double bondFaceValue, int numberOfBondsPurchased,
			double currentYield) {
		int totalMonths = 0;
		Date maturityDate;
		int calculatedDays = 0;
		BondDebentures returnDebenture = new BondDebentures();
		try {
			if (tenureType.equals("D")) {
				long[] yearmonths = new FinexaDateUtil().getYearCountByDay(investmentDate, Integer.parseInt(tenure));
				totalMonths = (int) yearmonths[0] * 12 + (int) yearmonths[1];
				tenure = String.valueOf(
						((int) yearmonths[0]) + (yearmonths[1] / (double) 12) + (yearmonths[2] / (double) (365)));
				calculatedDays = (int) yearmonths[2];
			} else {
				totalMonths = Integer.parseInt(tenure) * 12;
			}
			returnDebenture.setTotalMonths(totalMonths);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(investmentDate);

			calendar.add(Calendar.MONTH, totalMonths);
			calendar.add(Calendar.DAY_OF_MONTH, calculatedDays);
			maturityDate = calendar.getTime();
			returnDebenture.setDaysToMaturity(maturityDate);
			ctodaysDate = Calendar.getInstance();
			ctodaysDate.setTime(investmentDate);
			maturDate = Calendar.getInstance();
			maturDate.setTime(maturityDate);

			double finalValue = FinanceUtil.YEARFRAC(ctodaysDate.getTime(), maturDate.getTime(), 1);

			double pmtValue = FinanceUtil.pmt(((interestCouponRate) / coupounPayoutFrequency),
					(finalValue) * coupounPayoutFrequency, -bondFaceValue, bondFaceValue, false)
					* numberOfBondsPurchased;
			returnDebenture.setCouponReceived(pmtValue);
			double totalCoupon = pmtValue * coupounPayoutFrequency * ((finalValue));
			returnDebenture.setTotalCouponReceived(totalCoupon);

			String fomartedDate = sdfOutput.format(calendar.getTime());

			returnDebenture.setDaysToMaturity(sdfOutput.parse(fomartedDate));
			SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
			String inputString1 = myFormat.format(calendar.getTime());
			Date date1 = myFormat.parse(inputString1);

			ctodaysDate = Calendar.getInstance();
			maturDate = Calendar.getInstance();
			maturDate.setTime(date1);

			finalValue = FinanceUtil.YEARFRAC(ctodaysDate.getTime(), maturDate.getTime(), 1);

			returnDebenture.setEffectiveTimeToMaturity(finalValue);

			double firstParam = ((currentYield / 100) / coupounPayoutFrequency);
			double secondParam = (finalValue * coupounPayoutFrequency);

			double thirdParam = (-interestCouponRate) * bondFaceValue
					* ((numberOfBondsPurchased) / Double.parseDouble(String.valueOf(coupounPayoutFrequency)));

			double fourthParam = -bondFaceValue * numberOfBondsPurchased;

			boolean lastparam = false;
			double currentValue = FinanceLib.pv(firstParam, secondParam, thirdParam, fourthParam, lastparam);
			returnDebenture.setTotalMonths(totalMonths);
			returnDebenture.setCurrentValue(Math.floor(currentValue * 100) / 100);

		} catch (Exception exp) {
			FinexaBussinessException finexaBuss = new FinexaBussinessException(
					FinexaConstant.PRODUCT_CAL_BOND_DEBENTURE, FinexaConstant.PRODUCT_CAL_BOND_DEBENTURE_CODE,
					FinexaConstant.PRODUCT_CAL_BOND_DEBENTURE_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}

		return returnDebenture;
	}

	public List<BankBondDebenturesLookup> getBondDebentures(long numberOfBonds, double faceValue, int interestFrequency,
			Date investmentDate, Date dtMaturityDate, double couponReceivedValue, int totalMonths) {
		List<BankBondDebenturesLookup> bondDebentureLookupList = new ArrayList<BankBondDebenturesLookup>();
		try {
			int interestFreqConstantLookup = 12 / interestFrequency;
			Date varDepositDate = investmentDate;

			cDepositDate.setTime(varDepositDate);
			cMaturityDate.setTime(varDepositDate);
			cInterimDate.setTime(varDepositDate);
			strInterimDate = sdfOutput.format(cDepositDate.getTime());
			double totalInterestReceived = 0;
			double interestReceived = 0;
			double totalBondMount = 0;
			String strRefDate = "";
			Date dtLastDayOfMonth = null;
			long daysToMaturity = 0;

			int serialNo = 0;
			while (serialNo < totalMonths + 1) {
				BankBondDebenturesLookup bondDebentureLookup = new BankBondDebenturesLookup();

				java.util.Date dtInterimDate = cInterimDate.getTime();
				strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);
				try {
					dtLastDayOfMonth = sdfOutput.parse(strRefDate);
					long diff = dtMaturityDate.getTime() - dtLastDayOfMonth.getTime();
					daysToMaturity = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String strRefMonth = strRefDate.substring(3);
				String fiscalYear = FinexaDateUtil.getFiscalYear(dtInterimDate);
				if (serialNo % interestFreqConstantLookup == 0) {
					if (serialNo > 0) {

						interestReceived = couponReceivedValue;
					}
				} else {
					interestReceived = 0;
				}
				totalInterestReceived = totalInterestReceived + interestReceived;

				if (serialNo < 1) {
					totalBondMount = faceValue * numberOfBonds;
				} else {
					totalBondMount = 0;
				}

				if (dtLastDayOfMonth.after(dtMaturityDate)) {
					totalBondMount = -faceValue * numberOfBonds;
				}

				bondDebentureLookup.setSerialNo(serialNo);
				bondDebentureLookup.setReferenceDate(strRefDate);
				bondDebentureLookup.setReferenceMonth(strRefMonth);
				bondDebentureLookup.setFinancialYear(fiscalYear);
				bondDebentureLookup.setBondAmountDeposited(totalBondMount);
				bondDebentureLookup.setCouponReceived(Math.round(interestReceived));
				bondDebentureLookup.setTotalCouponReceived(Math.round(totalInterestReceived));
				bondDebentureLookup.setDaysToMaturity(daysToMaturity);

				bondDebentureLookupList.add(bondDebentureLookup);
				cInterimDate.add(Calendar.MONTH, 1);
				dtInterimDate = cInterimDate.getTime();
				strInterimDate = sdfOutput.format(dtInterimDate);
				serialNo++;
				if (dtLastDayOfMonth.after(dtMaturityDate)) {
					break;
				}
			}
		} catch (Exception exp) {
			FinexaBussinessException finexaBuss = new FinexaBussinessException(
					FinexaConstant.PRODUCT_CAL_BOND_DEBENTURE, FinexaConstant.PRODUCT_CAL_BOND_DEBENTURE_CODE,
					FinexaConstant.PRODUCT_CAL_BOND_DEBENTURE_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}

		return bondDebentureLookupList;
	}

}
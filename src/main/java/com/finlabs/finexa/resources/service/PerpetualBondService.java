package com.finlabs.finexa.resources.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.finlabs.finexa.resources.exception.FinexaBussinessException;
import com.finlabs.finexa.resources.model.BondDebentures;
import com.finlabs.finexa.resources.model.PerpetualBond;
import com.finlabs.finexa.resources.model.PerpetualBondLookup;
import com.finlabs.finexa.resources.util.FinanceUtil;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class PerpetualBondService {

	Calendar cInvestmentDate = Calendar.getInstance();
	Calendar cDepositDate = Calendar.getInstance();
	Calendar cMaturityDate = Calendar.getInstance();
	Calendar cInterimDate = Calendar.getInstance();
	String strInterimDate = "";
	SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
	SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MMMM-yyyy");
	List<BondDebentures> bondDebenturesLookupList = new ArrayList<BondDebentures>();

	public PerpetualBond calculateDebenturesValue(String tenureType, Date investmentDate, String tenure,
			int coupounPayoutFrequency, double interestCouponRate, double bondFaceValue, int numberOfBondsPurchased,
			double currentYield) {
		int totalMonths = 0;
		Date maturityDate;
		PerpetualBond returnDebenture = new PerpetualBond();
		try {
			if (tenureType.equals("D")) {
				long[] yearmonths = new FinexaDateUtil().getYearCountByDay(investmentDate, Integer.parseInt(tenure));
				totalMonths = (int) yearmonths[0] * 12 + (int) yearmonths[1];
				tenure = String.valueOf((int) yearmonths[0] + (int) yearmonths[1] / 12);

			} else {
				totalMonths = Integer.parseInt(tenure) * 12;
			}
			returnDebenture.setTotalMonths(totalMonths);
			double pmtValue = FinanceUtil.pmt(((interestCouponRate) / (coupounPayoutFrequency)),
					(Double.parseDouble(tenure)) * (coupounPayoutFrequency), -bondFaceValue, bondFaceValue, false)
					* numberOfBondsPurchased;
			returnDebenture.setCouponReceived(pmtValue);
			double totalCoupon = pmtValue * coupounPayoutFrequency * ((Double.parseDouble(tenure)));
			returnDebenture.setTotalCouponReceived(Long.valueOf(Math.round(totalCoupon)));
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(investmentDate);
			calendar.add(Calendar.MONTH, totalMonths);
			maturityDate = calendar.getTime();
			returnDebenture.setDaysToMaturity(maturityDate);
			String fomartedDate = sdfOutput.format(calendar.getTime());

			returnDebenture.setDaysToMaturity(sdfOutput.parse(fomartedDate));
			SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");
			String inputString1 = myFormat.format(calendar.getTime());
			Date date1 = myFormat.parse(inputString1);

			Calendar ctodaysDate = Calendar.getInstance();
			Calendar maturDate = Calendar.getInstance();
			maturDate.setTime(date1);

			double finalValue = FinanceUtil.YEARFRAC(ctodaysDate.getTime(), maturDate.getTime(), 1);
			returnDebenture.setEffectiveTimeToMaturity(Double.parseDouble(new DecimalFormat(".##").format(finalValue)));
			double currentValue = ((interestCouponRate) * (bondFaceValue / (currentYield / 100))
					* numberOfBondsPurchased);
			returnDebenture.setTotalMonths(totalMonths);
			returnDebenture.setCurrentValue(Math.floor(currentValue * 100) / 100);

		} catch (Exception exp) {
			FinexaBussinessException finexaBuss = new FinexaBussinessException(
					FinexaConstant.PRODUCT_CAL_PERPETUAL_BOND, FinexaConstant.PRODUCT_CAL_PERPETUAL_BOND_CODE,
					FinexaConstant.PRODUCT_CAL_PERPETUAL_BOND_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return returnDebenture;
	}

	public List<PerpetualBondLookup> getBondDebentures(double bondDepositValue, int interestFrequency,
			Date investmentDate, Date dtMaturityDate, double couponReceivedValue, int totalMonths) {
		List<PerpetualBondLookup> bondDebentureLookupList = new ArrayList<PerpetualBondLookup>();
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
				PerpetualBondLookup bondDebentureLookup = new PerpetualBondLookup();

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
				}
				totalInterestReceived = totalInterestReceived + interestReceived;

				if (dtLastDayOfMonth.after(dtMaturityDate)) {
					interestReceived = 0;
					totalInterestReceived = 0;
					totalBondMount = -bondDepositValue;
				}

				else {
					if (serialNo < 1) {
						totalBondMount = bondDepositValue;
					} else {
						totalBondMount = 0;
					}

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
				serialNo = serialNo + 1;
				interestReceived = 0;
				if (dtLastDayOfMonth.after(dtMaturityDate)) {
					break;
				}
			}
		}

		catch (Exception exp) {
			FinexaBussinessException finexaBuss = new FinexaBussinessException(
					FinexaConstant.PRODUCT_CAL_PERPETUAL_BOND, FinexaConstant.PRODUCT_CAL_PERPETUAL_BOND_CODE,
					FinexaConstant.PRODUCT_CAL_PERPETUAL_BOND_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return bondDebentureLookupList;
	}

}
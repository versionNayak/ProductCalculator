package com.finlabs.finexa.resources.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.formula.functions.FinanceLib;

import com.finlabs.finexa.resources.exception.FinexaBussinessException;
import com.finlabs.finexa.resources.model.BankFDTDRLookup;
import com.finlabs.finexa.resources.model.ZeroCouponBond;
import com.finlabs.finexa.resources.model.ZeroCouponBondLookup;
import com.finlabs.finexa.resources.util.FinanceUtil;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class ZeroCouponService {

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
	int calculatedDays = 0;
	List<BankFDTDRLookup> bankFDTDRLookupList = new ArrayList<BankFDTDRLookup>();

	public ZeroCouponBond calculateZeroCouponBond(double openingAmount, double interestRate, String tenureType,
			int tenure, int payoutFrequency, Date depositDate) {
		ZeroCouponBond bankFdTdr = new ZeroCouponBond();
		try {
			
			int interestFreqConstantLookup = 12 / payoutFrequency;

			double maturityAmount = FinanceLib.fv((interestRate / 100) / interestFreqConstantLookup,
					(tenure * interestFreqConstantLookup), 0.0, -openingAmount, true);
			bankFdTdr.setMaturityAmount(maturityAmount);
			totalInterestAccrued = maturityAmount - openingAmount;
			bankFdTdr.setTotalInterestReceived(totalInterestAccrued);

			cMaturityDate.setTime(depositDate);
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
			// TODO Auto-generated catch block
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_ZERO_COUPON,
					FinexaConstant.PRODUCT_CAL_ZERO_COUPON_CODE, FinexaConstant.PRODUCT_CAL_ZERO_COUPON_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return bankFdTdr;
	}

	public ZeroCouponBond getZerocouponBondServiceLookupList(double deposit, double rateOfInterest, String tenureType,
			int tenure, int interestFrequency, Date depositDate, int noOfBonds, double bondFaceValue) {

		ZeroCouponBond bankFdTdrOutput = new ZeroCouponBond();

		try {
			if (tenureType.equals("D")) {
				long[] yearmonths = new FinexaDateUtil().getYearCountByDay(depositDate, tenure);
				varTermMonths = (int) yearmonths[0] * 12 + (int) yearmonths[1];
				calculatedDays = (int) yearmonths[2];

			} else {
				varTermMonths = (int) tenure * 12;
			}
			int varInterestFreq = interestFrequency;
			openingBal = deposit;
			double closingBal = deposit;
			int interestFreqConstantLookup = 12 / varInterestFreq;
			int interestFreqInterestPaid = varInterestFreq;
			Date varDepositDate = depositDate;

			ZeroCouponBond currentOverAllcal = this.calculateZeroCouponBond(deposit, rateOfInterest, tenureType, tenure,
					interestFrequency, depositDate);
			totalInterestAccrued = 0.0;
			double PMT = currentOverAllcal.getMaturityAmount();
			cDepositDate.setTime(varDepositDate);
			cInterimDate.setTime(varDepositDate);
			bankFdTdrOutput.setInterestReceived(PMT);
			bankFdTdrOutput.setMaturityDate(currentOverAllcal.getMaturityDate());
			strInterimDate = sdfOutput.format(cDepositDate.getTime());
			List<ZeroCouponBondLookup> bankFDTDRLookupList = new ArrayList<ZeroCouponBondLookup>();
			int serialNo = 0;
			while (true) {
				ZeroCouponBondLookup bankFDTDRLookup = new ZeroCouponBondLookup();

				java.util.Date dtInterimDate = cInterimDate.getTime();
				strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);
				try {
					dtLastDayOfMonth = sdfOutput.parse(strRefDate);
					long diff = currentOverAllcal.getMaturityDate().getTime() - dtLastDayOfMonth.getTime();
					daysToMaturity = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				strRefMonth = strRefDate.substring(3);
				fiscalYear = FinexaDateUtil.getFiscalYear(dtInterimDate);
				openingBal = closingBal;
				if (serialNo > 0) {
					interestAccrued = openingBal * (rateOfInterest / 100) / interestFreqConstantLookup
							/ interestFreqInterestPaid;
					totalInterestAccrued = totalInterestAccrued + interestAccrued;
				}
				if (serialNo % interestFreqConstantLookup == 0) {
					if (serialNo > 0) {
						interestCredited = totalInterestAccrued - totalInterestCredited;
						totalInterestCredited = totalInterestCredited + interestCredited;
					}
				} else {
					interestCredited = 0;
				}

				closingBal = closingBal + interestCredited;

				if (dtLastDayOfMonth.after(currentOverAllcal.getMaturityDate())) {
					if (interestCredited == 0) {
						interestCredited = totalInterestAccrued - totalInterestCredited;
						closingBal = closingBal + interestCredited;
					}
				}
				bankFDTDRLookup.setSerialNo(serialNo);
				bankFDTDRLookup.setReferenceDate(strRefDate);
				bankFDTDRLookup.setReferenceMonth(strRefMonth);
				bankFDTDRLookup.setFinancialYear(fiscalYear);
				bankFDTDRLookup.setOpeningBal(Math.round(openingBal));
				bankFDTDRLookup.setInterestAccrued(Math.round(interestAccrued));
				bankFDTDRLookup.setTotalInterestReceived(Math.round(totalInterestAccrued));
				bankFDTDRLookup.setInterestCredited(interestCredited);
				bankFDTDRLookup.setClosingBalance(Math.round(closingBal));
				bankFDTDRLookup.setDaysToMaturity(daysToMaturity);

				bankFDTDRLookupList.add(bankFDTDRLookup);
				cInterimDate.add(Calendar.MONTH, 1);
				dtInterimDate = cInterimDate.getTime();
				strInterimDate = sdfOutput.format(dtInterimDate);
				serialNo++;
				if (dtLastDayOfMonth.after(currentOverAllcal.getMaturityDate())) {
					break;
				}
			}
			bankFdTdrOutput.setTotalInterestReceived(openingBal - deposit);
			bankFdTdrOutput.setMaturityAmount(noOfBonds * bondFaceValue);
			bankFdTdrOutput.setTotalInterestReceived(bankFdTdrOutput.getMaturityAmount() - deposit);

			double finalValue = FinanceUtil.YEARFRAC(Calendar.getInstance().getTime(), cMaturityDate.getTime(), 1);
			bankFdTdrOutput.setEffectiveTimeToMaturity(Double.parseDouble(new DecimalFormat(".##").format(finalValue)));
			double currentValue = FinanceLib.pv((rateOfInterest / 100), finalValue, 0, -(noOfBonds * bondFaceValue), false);
			bankFdTdrOutput.setCurrentValue(currentValue);
			bankFdTdrOutput.setBankFdTdrLookup(bankFDTDRLookupList);
		} catch (Exception exp) {
			// TODO Auto-generated catch block
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_ZERO_COUPON,
					FinexaConstant.PRODUCT_CAL_ZERO_COUPON_CODE, "Failed to get Zero Coupon Bond Service Lookup List. Please see log for details.", exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return bankFdTdrOutput;
	}

}

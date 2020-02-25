package com.finlabs.finexa.resources.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.formula.functions.FinanceLib;

import com.finlabs.finexa.resources.dao.MasterDAO;
import com.finlabs.finexa.resources.dao.MasterDAOImplementation;
import com.finlabs.finexa.resources.exception.FinexaBussinessException;
import com.finlabs.finexa.resources.model.MasterPONSC;
import com.finlabs.finexa.resources.model.PONSC;
import com.finlabs.finexa.resources.model.PONSCLookup;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class PONSCService {

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
	double interestAccrued = 0.0;
	double totalInterestAccrued = 0.0;
	double interestCredited = 0.0;
	double totalInterestCredited = 0.0;
	long daysToMaturity = 0;
	Date dtLastDayOfMonth = new Date();
	Date dtMaturityDate = new Date();
	int varTermMonths = 0;
	double displayInterestRate = 0.0;

	public PONSC calculateFDSTDRCDCP(double openingAmount, double interestRate, int tenure, int payoutFrequency,
			Date depositDate) {
		PONSC poNsc = new PONSC();
		try {
			int interestFreqConstantLookup = 12 / payoutFrequency;

			double maturityAmount = FinanceLib.fv((interestRate) / interestFreqConstantLookup,
					(tenure * interestFreqConstantLookup), 0.0, -openingAmount, true);
			poNsc.setMaturityAmount(maturityAmount);
			totalInterestAccrued = maturityAmount - openingAmount;
			poNsc.setTotalInterestReceived(totalInterestAccrued);

			cMaturityDate.setTime(depositDate);

			varTermMonths = (int) tenure * 12;

			cMaturityDate.add(Calendar.MONTH, varTermMonths);
			poNsc.setMaturityDate(cMaturityDate.getTime());

		} catch (Exception exp) {
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_PO_NSC,
					FinexaConstant.PRODUCT_CAL_PO_NSC_CODE, FinexaConstant.PRODUCT_CAL_PO_NSC_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return poNsc;
	}

	public PONSC getPONSCCalculationList(double deposit, int tenure, int interestFrequency, Date depositDate) {

		PONSC poNSC = new PONSC();
		List<PONSCLookup> poNScLookupList = new ArrayList<PONSCLookup>();
		try {
			varTermMonths = (int) tenure * 12;
			int varInterestFreq = interestFrequency;
			openingBal = deposit;
			double closingBal = deposit;
			int interestFreqConstantLookup = 12 / varInterestFreq;
			int interestFreqInterestPaid = varInterestFreq;

			Date varDepositDate = depositDate;
			MasterDAO masterNscDao = new MasterDAOImplementation();
			List<MasterPONSC> masterMasterPONSCList = masterNscDao.getMasterPONSCRates();
			boolean checkInterestRate = false;
			for (MasterPONSC masterPONSC : masterMasterPONSCList) {
				if ((depositDate.after(masterPONSC.getPeriodStart()) && depositDate.before(masterPONSC.getPeriodEnd()))
						|| depositDate.equals(masterPONSC.getPeriodStart())
						|| depositDate.equals(masterPONSC.getPeriodEnd())) {
					displayInterestRate = masterPONSC.getInterestRate();
					checkInterestRate = true;
				}
				if (checkInterestRate) {
					break;
				}

			}
			poNSC.setInterestRate(displayInterestRate);
			PONSC currentOverAllcal = this.calculateFDSTDRCDCP(deposit, displayInterestRate, tenure, interestFrequency,
					depositDate);
			totalInterestAccrued = 0.0;
			double PMT = currentOverAllcal.getMaturityAmount();
			cDepositDate.setTime(varDepositDate);
			cInterimDate.setTime(varDepositDate);
			poNSC.setInterestReceived(PMT);
			poNSC.setMaturityDate(currentOverAllcal.getMaturityDate());
			strInterimDate = sdfOutput.format(cDepositDate.getTime());

			int serialNo = 0;

			while (true) {
				PONSCLookup poNscLookup = new PONSCLookup();

				java.util.Date dtInterimDate = cInterimDate.getTime();
				strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);

				dtLastDayOfMonth = sdfOutput.parse(strRefDate);
				long diff = currentOverAllcal.getMaturityDate().getTime() - dtLastDayOfMonth.getTime();
				daysToMaturity = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

				strRefMonth = strRefDate.substring(3);
				fiscalYear = FinexaDateUtil.getFiscalYear(dtInterimDate);
				openingBal = closingBal;
				if (serialNo > 0) {
					interestAccrued = openingBal * (displayInterestRate) / interestFreqConstantLookup
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
					poNSC.setMaturityAmount(closingBal);
					poNSC.setTotalInterestReceived(poNSC.getMaturityAmount() - deposit);
				}
				poNscLookup.setSerialNo(serialNo);
				poNscLookup.setReferenceDate(strRefDate);
				poNscLookup.setReferenceMonth(strRefMonth);
				poNscLookup.setFinancialYear(fiscalYear);
				poNscLookup.setOpeningBal(Math.round(openingBal));
				poNscLookup.setInterestAccrued(Math.round(interestAccrued));
				poNscLookup.setTotalInterestReceived(Math.round(totalInterestAccrued));
				poNscLookup.setInterestCredited(interestCredited);
				poNscLookup.setClosingBalance(Math.round(closingBal));
				poNscLookup.setDaysToMaturity(daysToMaturity);

				poNScLookupList.add(poNscLookup);
				cInterimDate.add(Calendar.MONTH, 1);
				dtInterimDate = cInterimDate.getTime();
				strInterimDate = sdfOutput.format(dtInterimDate);
				if (dtLastDayOfMonth.after(currentOverAllcal.getMaturityDate())) {
					break;
				}
				serialNo = serialNo + 1;
			}
		} catch (Exception exp) {
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_PO_NSC,
					FinexaConstant.PRODUCT_CAL_PO_NSC_CODE, FinexaConstant.PRODUCT_CAL_PO_NSC_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		poNSC.setPoNScLookupList(poNScLookupList);

		return poNSC;
	}

}
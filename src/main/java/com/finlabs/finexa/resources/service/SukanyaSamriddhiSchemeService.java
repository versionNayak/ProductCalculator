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
import com.finlabs.finexa.resources.model.MasterSukanyaSamriddhiScheme;
import com.finlabs.finexa.resources.model.SukanyaSamriddhiScheme;
import com.finlabs.finexa.resources.model.SukanyaSamriddhiSchemeLookup;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class SukanyaSamriddhiSchemeService {
	Calendar cDepositDate = Calendar.getInstance();
	Calendar cMaturityDate = Calendar.getInstance();
	Calendar paymentMaturityDate = Calendar.getInstance();
	Calendar cInterimDate = Calendar.getInstance();
	String strInterimDate = "";
	SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MMM-yyyy");

	String strRefDate = "";
	String strRefMonth = "";
	String fiscalYear = "";
	double openingBal = 0.0;
	double interestAccured = 0.0;
	double interestCredited = 0.0;
	double totalInterestCredited = 0.0;
	double totalInterestAccured = 0.0;
	double displayInterestRate = 0.0;
	double calInterestRate = 0.0;
	double totalInterestCredit = 0.0;
	long daysToMaturity = 0;
	Date dtLastDayOfMonth = new Date();
	Date dtMaturityDate = new Date();
	int varTermMonths = 0;
	int lookupSerialNumber = 1;

	public SukanyaSamriddhiScheme getSukanyaSamriddhiSchemeList(double deposit, String tenureType, int paymenttenure,
			int rdDepositFreq, int compundingFreq, Date depositDate, int maturitytenure) {

		SukanyaSamriddhiScheme sukanyaSamSCh = new SukanyaSamriddhiScheme();
		try {
			double amountDeposited = deposit;
			varTermMonths = (int) maturitytenure * 12;
			openingBal = 0;
			double closingBal = 0;
			int rdDepositFreqInterestPaid = rdDepositFreq;
			int rdDepositFreqConstantLookup = 12 / rdDepositFreq;

			int compundingFreqInterestPaid = compundingFreq;
			int compundingFreqConstantLookup = 12 / compundingFreq;
			Date varDepositDate = depositDate;
			cDepositDate.setTime(varDepositDate);
			cInterimDate.setTime(varDepositDate);
			cMaturityDate.setTime(depositDate);
			paymentMaturityDate.setTime(depositDate);
			cMaturityDate.add(Calendar.MONTH, varTermMonths);
			varTermMonths = (int) paymenttenure * 12;
			paymentMaturityDate.add(Calendar.MONTH, varTermMonths);

			sukanyaSamSCh.setDepositMaturityDate(cMaturityDate.getTime());
			sukanyaSamSCh.setPaymentMaturityDate(paymentMaturityDate.getTime());
			strInterimDate = sdfOutput.format(cDepositDate.getTime());
			List<SukanyaSamriddhiSchemeLookup> sukanyaSamSChLookupList = new ArrayList<SukanyaSamriddhiSchemeLookup>();
			int serialNo = 0;

			MasterDAO mastersukDao = new MasterDAOImplementation();
			List<MasterSukanyaSamriddhiScheme> sukanyaSamriddhiSchemeList = mastersukDao.getSukanyaSamridhiInterestRate();
			boolean checkInterestRate = false;
			while (true) {
				SukanyaSamriddhiSchemeLookup sukanyaSamSChLookup = new SukanyaSamriddhiSchemeLookup();

				if (serialNo > 0) {
					openingBal = closingBal;
				}

				java.util.Date dtInterimDate = cInterimDate.getTime();
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
				for (MasterSukanyaSamriddhiScheme mastersukanyaScheme : sukanyaSamriddhiSchemeList) {
					if ((dtLastDayOfMonth.after(mastersukanyaScheme.getStartDate())
							&& dtLastDayOfMonth.before(mastersukanyaScheme.getEndDate()))
							|| dtLastDayOfMonth.equals(mastersukanyaScheme.getStartDate())
							|| dtLastDayOfMonth.equals(mastersukanyaScheme.getEndDate())) {
						displayInterestRate = mastersukanyaScheme.getInterestRate();
						checkInterestRate = true;
					}
					if (checkInterestRate) {
						break;
					}

				}

				if (cInterimDate.getTime().before(paymentMaturityDate.getTime())) {
					if (serialNo % rdDepositFreqConstantLookup == 0) {
						amountDeposited = deposit;
					}
				}

				interestAccured = (openingBal)
						* ((displayInterestRate) / compundingFreqInterestPaid / compundingFreqConstantLookup);
				totalInterestAccured = totalInterestAccured + interestAccured;
				if (serialNo % compundingFreqConstantLookup == 0) {
					if (serialNo > 0) {
						interestCredited = totalInterestAccured - totalInterestCredited;
						totalInterestCredited = interestCredited + totalInterestCredited;
					} else {
						interestCredited = 0;

					}
				}
				closingBal = openingBal + interestCredited + amountDeposited;

				if (dtLastDayOfMonth.after(cMaturityDate.getTime()) || dtLastDayOfMonth.equals(cMaturityDate.getTime())) {
					amountDeposited = 0;

					// totalInterestAccured = 0;
					sukanyaSamSCh.setMaturityAmount(closingBal);

				}
				sukanyaSamSChLookup.setSerialNo(lookupSerialNumber);
				sukanyaSamSChLookup.setReferenceDate(strRefDate);
				sukanyaSamSChLookup.setReferenceMonth(strRefMonth);
				sukanyaSamSChLookup.setFinancialYear(fiscalYear);
				sukanyaSamSChLookup.setOpeningBal(openingBal);
				sukanyaSamSChLookup.setAmountDeposited(amountDeposited);
				sukanyaSamSChLookup.setInterestAccrued(interestAccured);
				sukanyaSamSChLookup.setInterestCredited(interestCredited);
				sukanyaSamSChLookup.setTotalInterestAccrued(totalInterestAccured);
				sukanyaSamSChLookup.setInterestRate(displayInterestRate);
				sukanyaSamSChLookup.setClosingBalance(closingBal);
				sukanyaSamSChLookup.setDaysToMaturity(daysToMaturity);
				sukanyaSamSChLookupList.add(sukanyaSamSChLookup);
				cInterimDate.add(Calendar.MONTH, 1);
				dtInterimDate = cInterimDate.getTime();
				strInterimDate = sdfOutput.format(dtInterimDate);
				if (dtLastDayOfMonth.after(cMaturityDate.getTime()) || dtLastDayOfMonth.equals(cMaturityDate.getTime())) {
					break;
				}
				amountDeposited = 0;
				interestCredited = 0;
				serialNo++;
				checkInterestRate = false;
				lookupSerialNumber++;
			}

			sukanyaSamSCh.setTotalAmountDeposited(deposit * rdDepositFreqInterestPaid * paymenttenure);
			sukanyaSamSCh
					.setTotalInterestReceived(sukanyaSamSCh.getMaturityAmount() - sukanyaSamSCh.getTotalAmountDeposited());
			sukanyaSamSCh.setSukanyaSamSchLookupList(sukanyaSamSChLookupList);
		} catch (Exception exp) {
			// TODO Auto-generated catch block
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_SUKANYA,
					FinexaConstant.PRODUCT_CAL_SUKANYA_CODE, FinexaConstant.PRODUCT_CAL_SUKANYA_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return sukanyaSamSCh;
	}

}
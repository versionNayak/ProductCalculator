package com.finlabs.finexa.resources.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.functions.FinanceLib;

import com.finlabs.finexa.resources.exception.FinexaBussinessException;
import com.finlabs.finexa.resources.model.SimpleLoanCalLookup;
import com.finlabs.finexa.resources.model.SimpleLoanCalculator;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class SimpleLoanCalNonEMIBasedService {

	Calendar calInstance = Calendar.getInstance();
	Calendar generalCal = Calendar.getInstance();
	String strInterimDate = "";
	SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MMMM-yyyy");

	public SimpleLoanCalculator calculateNonEmiDetails(SimpleLoanCalculator simpleLoanCal) {
		try {
			double emiPaidAmount = 0.0;
			double overallInterestPaid = 0.0;
			int interestPaymentFreq = 0;
			calInstance.setTime(simpleLoanCal.getLoanStartDate());
			calInstance.add(Calendar.MONTH, simpleLoanCal.getLoanTenure());
			simpleLoanCal.setLoanEndDate(calInstance.getTime());
			interestPaymentFreq = simpleLoanCal.getInterestPaymentFrequency();
			emiPaidAmount = FinanceLib.pmt(((simpleLoanCal.getInterestRate()) / interestPaymentFreq),
					(double) ((simpleLoanCal.getLoanTenure())) * interestPaymentFreq, -simpleLoanCal.getLoanAmount(),
					simpleLoanCal.getLoanAmount(), true);
			overallInterestPaid = emiPaidAmount * interestPaymentFreq * (simpleLoanCal.getLoanTenure() / (double) 12);
			simpleLoanCal.setEmiAmount(emiPaidAmount);
			simpleLoanCal.setTotalInterestPaid(Double.parseDouble(new DecimalFormat(".##").format(overallInterestPaid)));
			
		} catch (Exception exp) {
			// TODO Auto-generated catch block
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_SIMPLE_LOAN_NON_EMI,
					FinexaConstant.PRODUCT_CAL_SIMPLE_LOAN_NON_EMI_CODE, FinexaConstant.PRODUCT_CAL_SIMPLE_LOAN_NON_EMI_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		
		return simpleLoanCal;

	}

	public List<SimpleLoanCalLookup> getNonEMIDetails(double begningBal, int interestFrequency, Date refDate,
			Date loanEndDate, double interestPaidAmount, int totalMonths) {
		List<SimpleLoanCalLookup> simpleLoanNonEMICalList = new ArrayList<SimpleLoanCalLookup>();
		try {
			generalCal.setTime(refDate);
			double interestReceived = 0;
			double begBalance = 0.0;
			double endBalance = 0.0;
			double principleAmount = 0.0;
			double totalPrincipleAmount = 0.0;
			double totalInterestAmount = 0.0;
			String strRefDate = "";
			Date dtLastDayOfMonth = null;
			long daysToMaturity = 0;
			long totalDaysForMaturity = 0;
			interestFrequency = 12 / interestFrequency;
			int serialNo = 1;
			Date lastMonRef = new Date();
			while (generalCal.getTime().compareTo(loanEndDate) == -1) {
				SimpleLoanCalLookup simpleCalLookup = new SimpleLoanCalLookup();
				if (serialNo == 1) {
					strInterimDate = sdfOutput.format(generalCal.getTime());
					strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);
					String strRefMonth = strRefDate.substring(3);
					String fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
					if (serialNo % interestFrequency == 0) {
						interestReceived = interestPaidAmount;
					} else {
						interestReceived = 0;
					}
					begBalance = begningBal;
					endBalance = begBalance - principleAmount;
					totalInterestAmount = interestReceived;
					try {
						dtLastDayOfMonth = sdfOutput.parse(strRefDate);
						daysToMaturity = FinexaDateUtil.getDays(loanEndDate, generalCal.getTime());
						totalDaysForMaturity = FinexaDateUtil.getDays(dtLastDayOfMonth, refDate);

					} catch (Exception e) {
						e.printStackTrace();
					}
					totalDaysForMaturity = daysToMaturity - totalDaysForMaturity;
					daysToMaturity = totalDaysForMaturity;
					simpleCalLookup.setSerialNumber(serialNo);
					simpleCalLookup.setRefDate(dtLastDayOfMonth);
					simpleCalLookup.setDisplayDate(sdfOutput.format(dtLastDayOfMonth));
					simpleCalLookup.setReferenceMonth(strRefMonth);
					simpleCalLookup.setFinancialYear(fiscalYear);
					simpleCalLookup.setBegningBal(begBalance);
					simpleCalLookup.setInterestPayment(interestReceived);
					simpleCalLookup.setEndingBalance(endBalance);
					simpleCalLookup.setTotalInterestPaid(totalInterestAmount);
					simpleCalLookup.setLoanEndDays((int) totalDaysForMaturity);

				} else {
					lastMonRef = dtLastDayOfMonth;
					generalCal.add(Calendar.MONTH, 1);
					strInterimDate = sdfOutput.format(generalCal.getTime());
					strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);
					String strRefMonth = strRefDate.substring(3);
					String fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
					if (serialNo % interestFrequency == 0) {
						interestReceived = interestPaidAmount;
					} else {
						interestReceived = 0;
					}
					try {
						dtLastDayOfMonth = sdfOutput.parse(strRefDate);
						totalDaysForMaturity = FinexaDateUtil.getDays(dtLastDayOfMonth, lastMonRef);

					} catch (Exception e) {
						e.printStackTrace();
					}
					totalDaysForMaturity = daysToMaturity - totalDaysForMaturity;
					daysToMaturity = totalDaysForMaturity;
					endBalance = begBalance - principleAmount;
					totalInterestAmount = totalInterestAmount + interestReceived;
					simpleCalLookup.setSerialNumber(serialNo);
					simpleCalLookup.setRefDate(dtLastDayOfMonth);
					simpleCalLookup.setDisplayDate(sdfOutput.format(dtLastDayOfMonth));
					simpleCalLookup.setReferenceMonth(strRefMonth);
					simpleCalLookup.setFinancialYear(fiscalYear);
					simpleCalLookup.setBegningBal(begBalance);
					simpleCalLookup.setInterestPayment(Math.round(interestReceived));
					simpleCalLookup.setEndingBalance(endBalance);
					simpleCalLookup.setTotalInterestPaid(Math.round(totalInterestAmount));
					simpleCalLookup.setLoanEndDays((int) totalDaysForMaturity);

					if (generalCal.getTime().compareTo(loanEndDate) == 0) {
						strInterimDate = sdfOutput.format(generalCal.getTime());
						strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);
						strRefMonth = strRefDate.substring(3);
						fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
						try {
							dtLastDayOfMonth = sdfOutput.parse(strRefDate);
						} catch (Exception e) {
							e.printStackTrace();
						}
						totalDaysForMaturity = daysToMaturity;
						principleAmount = begningBal;
						totalPrincipleAmount = totalPrincipleAmount + principleAmount;
						totalInterestAmount = totalInterestAmount + interestReceived;
						simpleCalLookup.setRefDate(dtLastDayOfMonth);
						simpleCalLookup.setDisplayDate(sdfOutput.format(dtLastDayOfMonth));
						simpleCalLookup.setReferenceMonth(strRefMonth);
						simpleCalLookup.setFinancialYear(fiscalYear);
						simpleCalLookup.setBegningBal(begBalance);
						simpleCalLookup.setInterestPayment(0);
						simpleCalLookup.setPrincipalPayment(Math.round(principleAmount));
						simpleCalLookup.setTotalPrincipalPaid(Math.round(totalPrincipleAmount));
						simpleCalLookup.setEndingBalance(0);
						simpleCalLookup.setLoanEndDays((int) totalDaysForMaturity);
						simpleCalLookup.setSerialNumber(0);
						simpleCalLookup.setTotalInterestPaid(0);
						simpleLoanNonEMICalList.add(simpleCalLookup);
						break;
					}

				}
				simpleLoanNonEMICalList.add(simpleCalLookup);
				serialNo++;
			}
		} catch (Exception exp) {
			// TODO Auto-generated catch block
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_SIMPLE_LOAN_NON_EMI,
					FinexaConstant.PRODUCT_CAL_SIMPLE_LOAN_NON_EMI_CODE, "Failed to get Non EMI details. Please see log for details.", exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}

		return simpleLoanNonEMICalList;
	}
}

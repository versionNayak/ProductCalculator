package com.finlabs.finexa.resources.service;

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

public class SimpleLoanCalEMIBasedService {

	Calendar calInstance = Calendar.getInstance();
	Calendar generalCal = Calendar.getInstance();
	String strInterimDate = "";
	SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MMMM-yy");
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yy");
	Date refrenceDate = null;

	public SimpleLoanCalculator calculateEMIBasedLoanValue(String loan_original_flag, double interestRate,
			double loanAmount, Date loanStartDate, double emiAmount, int loanTenure) {
		int numberEMIs = 0;
		SimpleLoanCalculator simpleLoanCalculator = new SimpleLoanCalculator();
		try {
			if (!loan_original_flag.equalsIgnoreCase("Y")) {

				//new code
				calInstance.setTime(loanStartDate);
				double actualNoOfEMI = Math.ceil(FinanceLib.nper((interestRate / 12), -emiAmount, loanAmount, 0.0, false));
				numberEMIs = (int) actualNoOfEMI;
				simpleLoanCalculator.setNumberOfEMI(numberEMIs);
				refrenceDate = calInstance.getTime();
				calInstance.add(Calendar.MONTH, numberEMIs);
				simpleLoanCalculator.setLoanEndDate(calInstance.getTime());
				simpleLoanCalculator.setEmiAmount(emiAmount);
				simpleLoanCalculator.setNumberOfEMI(numberEMIs);
				
				
				
				System.out.println("loanStartDate "+loanStartDate);
				System.out.println("calInstance "+calInstance.getTime());
				System.out.println("numberEMIs "+numberEMIs);
				System.out.println("emiAmount "+emiAmount);
			
			} else {
				numberEMIs = loanTenure * 12;
				simpleLoanCalculator.setNumberOfEMI(numberEMIs);
				simpleLoanCalculator.setEmiAmount(FinanceLib.pmt((interestRate) / 12, numberEMIs, -loanAmount, 0, false));

				calInstance.setTime(loanStartDate);
				refrenceDate = calInstance.getTime();
				calInstance.add(Calendar.MONTH, numberEMIs);
				simpleLoanCalculator.setLoanEndDate(calInstance.getTime());

			}
		} catch (Exception exp) {
			// TODO Auto-generated catch block
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_SIMPLE_LOAN_EMI,
					FinexaConstant.PRODUCT_CAL_SIMPLE_LOAN_EMI_CODE, FinexaConstant.PRODUCT_CAL_SIMPLE_LOAN_EMI_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}

		return simpleLoanCalculator;
	}
	
	public SimpleLoanCalculator calculateEMIBasedLoanValue(String loan_original_flag, double interestRate,
			double loanAmount, Date loanStartDate, Date loanStartDateout, double emiAmount, int loanTenure) {
		int numberEMIs = 0;
		SimpleLoanCalculator simpleLoanCalculator = new SimpleLoanCalculator();
		try {
			if (!loan_original_flag.equalsIgnoreCase("Y")) {

				//new code
				calInstance.setTime(loanStartDateout);
				double actualNoOfEMI = Math.ceil(FinanceLib.nper((interestRate / 12), -emiAmount, loanAmount, 0.0, false));
				numberEMIs = (int) actualNoOfEMI;
				simpleLoanCalculator.setNumberOfEMI(numberEMIs);
				refrenceDate = calInstance.getTime();
				calInstance.add(Calendar.MONTH, numberEMIs);
				simpleLoanCalculator.setLoanEndDate(calInstance.getTime());
				simpleLoanCalculator.setEmiAmount(emiAmount);
				simpleLoanCalculator.setNumberOfEMI(numberEMIs);
				
				
				
				System.out.println("loanStartDate "+loanStartDate);
				System.out.println("calInstance "+calInstance.getTime());
				System.out.println("numberEMIs "+numberEMIs);
				System.out.println("emiAmount "+emiAmount);
			
			} else {
				numberEMIs = loanTenure * 12;
				simpleLoanCalculator.setNumberOfEMI(numberEMIs);
				simpleLoanCalculator.setEmiAmount(FinanceLib.pmt((interestRate) / 12, numberEMIs, -loanAmount, 0, false));

				calInstance.setTime(loanStartDate);
				refrenceDate = calInstance.getTime();
				calInstance.add(Calendar.MONTH, numberEMIs);
				simpleLoanCalculator.setLoanEndDate(calInstance.getTime());

			}
		} catch (Exception exp) {
			// TODO Auto-generated catch block
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_SIMPLE_LOAN_EMI,
					FinexaConstant.PRODUCT_CAL_SIMPLE_LOAN_EMI_CODE, FinexaConstant.PRODUCT_CAL_SIMPLE_LOAN_EMI_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}

		return simpleLoanCalculator;
	}


	public List<SimpleLoanCalLookup> getEMIBasedLoanList(int numberEMis, Date loanStartDate, double loanAmount,
			double emiAmount, double interestRate, String loan_original_flag) {
		List<SimpleLoanCalLookup> simpleLoanNonEMICalList = new ArrayList<SimpleLoanCalLookup>();
		try {
			double begningBalance = 0.0;
			double endingBalance = 0.0;
			double interestPayment = 0.0;
			double principlePayment = 0.0;
			double totalInterestPayment = 0.0;
			double totalPrinciplePayment = 0.0;
			if (!loan_original_flag.equalsIgnoreCase("Y")) {
				//new code as per new requirement
				//loanStartDate = new Date();
				//System.out.println("loanStartDate "+loanStartDate);
			}

			int count = 1;
             System.out.println("count "+count);
             System.out.println("numberEMis "+numberEMis);
			while (count <= numberEMis) {
				SimpleLoanCalLookup simpleLookUp = new SimpleLoanCalLookup();

				if (count == 1) {
					calInstance.setTime(loanStartDate);
					refrenceDate = calInstance.getTime();
					calInstance.setTime(refrenceDate);
					simpleLookUp.setRefDate(calInstance.getTime());
					simpleLookUp.setDisplayDate(sdf.format(simpleLookUp.getRefDate()));
					String fiscalYear = FinexaDateUtil.getFiscalYear(refrenceDate);
					//System.out.println("refrenceDate "+refrenceDate);
					//System.out.println("fiscalYear "+fiscalYear);
					simpleLookUp.setFinancialYear(fiscalYear);
					String refDate = sdf.format(refrenceDate);
					String strRefMonth = refDate.substring(3);
					simpleLookUp.setReferenceMonth(strRefMonth);
					simpleLookUp.setInstallmentNumber(count);
					begningBalance = loanAmount;
					simpleLookUp.setBegningBal(begningBalance);
					simpleLookUp.setEmiAmount(emiAmount);
					System.out.println("emiAmount 444  "+emiAmount);
					interestPayment = (simpleLookUp.getBegningBal() * ((interestRate) / 12));
					simpleLookUp.setInterestPayment(interestPayment);
					principlePayment = (simpleLookUp.getEmiAmount() - interestPayment);
					simpleLookUp.setPrincipalPayment(principlePayment);
					endingBalance = begningBalance - principlePayment;
					simpleLookUp.setEndingBalance(endingBalance);
					simpleLookUp.setSerialNumber(count);
					if (loan_original_flag.equalsIgnoreCase("Y")) {
						totalPrinciplePayment = simpleLookUp.getPrincipalPayment();
						totalInterestPayment = simpleLookUp.getInterestPayment();
						simpleLookUp.setTotalPrincipalPaid(totalPrinciplePayment);
						simpleLookUp.setTotalInterestPaid(totalInterestPayment);
					}

				} else {
					calInstance.setTime(refrenceDate);
					calInstance.add(Calendar.MONTH, 1);
					simpleLookUp.setRefDate(calInstance.getTime());
					simpleLookUp.setDisplayDate(sdf.format(simpleLookUp.getRefDate()));
					refrenceDate = simpleLookUp.getRefDate();
					String fiscalYear = FinexaDateUtil.getFiscalYear(calInstance.getTime());
					//System.out.println("calInstance.getTime() "+calInstance.getTime());
					//System.out.println("else fiscalYear "+fiscalYear);
					simpleLookUp.setFinancialYear(fiscalYear);
					String refDate = sdf.format(simpleLookUp.getRefDate());
					String strRefMonth = refDate.substring(3);
					simpleLookUp.setReferenceMonth(strRefMonth);
					simpleLookUp.setInstallmentNumber(count);
					begningBalance = endingBalance;
					simpleLookUp.setBegningBal(begningBalance);
					interestPayment = (simpleLookUp.getBegningBal() * ((interestRate) / 12));
					simpleLookUp.setInterestPayment(interestPayment);
					principlePayment = (emiAmount - interestPayment);
					simpleLookUp.setPrincipalPayment(principlePayment);
					endingBalance = begningBalance - principlePayment;
					simpleLookUp.setEmiAmount(emiAmount);
					simpleLookUp.setEndingBalance(endingBalance);
					simpleLookUp.setSerialNumber(count);
					if (loan_original_flag.equalsIgnoreCase("Y")) {
						totalInterestPayment = totalInterestPayment + interestPayment;
						totalPrinciplePayment = totalPrinciplePayment + principlePayment;
						simpleLookUp.setTotalPrincipalPaid(totalPrinciplePayment);
						simpleLookUp.setTotalInterestPaid(totalInterestPayment);
					}

				}

				simpleLoanNonEMICalList.add(simpleLookUp);
				count++;
			}
		} catch (Exception exp) {
			// TODO Auto-generated catch block
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_SIMPLE_LOAN_EMI,
					FinexaConstant.PRODUCT_CAL_SIMPLE_LOAN_EMI_CODE, "Failed to get EMI based Loan List. Please see log for details.", exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return simpleLoanNonEMICalList;

	}
}

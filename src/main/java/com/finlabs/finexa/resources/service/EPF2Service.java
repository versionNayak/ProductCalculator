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
import com.finlabs.finexa.resources.model.EPF2Calculator;
import com.finlabs.finexa.resources.model.EPF2Lookup;
import com.finlabs.finexa.resources.model.MasterEPF2Calculator;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class EPF2Service {

	Calendar planningDateCal = Calendar.getInstance();
	Calendar planEndDate = Calendar.getInstance();
	Calendar epfBalDate = Calendar.getInstance();
	String strInterimDate = "";
	SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MMMM-yyyy");

	SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd-MMMM-yy");

	
	public EPF2Calculator getEPF2CaculatedValues(Date clientDOB, double currEPFBal, double currEPSBal,
			double monthlyBasicDA, double expectedIncreaseSal, int contributionUptoAge,int epfWithdrawAlage, String increaseMonth) {
		EPF2Calculator epf2Cal = new EPF2Calculator();
		
		epf2Cal.setEpfWiDAge(contributionUptoAge);
		
		List<EPF2Lookup> epf2LoohkupList = new ArrayList<EPF2Lookup>();
		try {
			/*
			 * planningDateCal.set(Calendar.DAY_OF_MONTH, 1);
			 * planningDateCal.set(Calendar.MONTH, 3); planningDateCal.set(Calendar.YEAR,
			 * planningDateCal.get(Calendar.YEAR));
			 */
			//After Discuss with Mihir Sir and Nissy (Planning Date = current FY Date)
			planningDateCal.set(Calendar.MILLISECOND, 0);
			planningDateCal.set(Calendar.SECOND, 0);
			planningDateCal.set(Calendar.MINUTE, 0);
			planningDateCal.set(Calendar.HOUR, 0);
			
			int planningDateYear = planningDateCal.get(Calendar.YEAR);
			if (planningDateCal.get(Calendar.MONTH) < 3) {
				planningDateYear = planningDateCal.get(Calendar.YEAR) - 1;
				planningDateCal.set(Calendar.YEAR, planningDateYear);
			}
		 	 planningDateCal.set(Calendar.MONTH, 3);
			 planningDateCal.set(Calendar.DAY_OF_MONTH,1);
		
			Date date = new SimpleDateFormat("MMMM").parse(increaseMonth);
			Calendar calRefMonth = Calendar.getInstance();
			calRefMonth.setTime(date);
			planEndDate.setTime(clientDOB);
			epfBalDate.setTime(clientDOB);
			planEndDate.add(Calendar.MONTH, (contributionUptoAge * 12));
			// Requirement Change of EPF on 11/03/2019
			/***We are going to mature the EPF  one month before so that it is available at the time when Client Retires *****************/
			// new change
			planEndDate.add(Calendar.MONTH, -1);
			planEndDate.setTime(planEndDate.getTime());
			epfBalDate.add(Calendar.MONTH, ((epfWithdrawAlage) * 12));
			// new change
			epfBalDate.add(Calendar.MONTH, -1);
			epfBalDate.set(Calendar.DAY_OF_MONTH, epfBalDate.getActualMaximum(Calendar.DAY_OF_MONTH));
			epfBalDate.setTime(epfBalDate.getTime());
			
			int clientAge = 0;
			double monthlyBasicDa = 0;
			double openingBalEPF = 0.0;
			double openingBalEPS = 0.0;
			double employeeContEPF = 0.0;
			double employerContEPF = 0.0;
			double employerContEPS = 0.0;
			double totalEmployeeCont = 0.0;
			double totalEmployerContEPF = 0.0;
			double totalEmployerContEPS = 0.0;
			double interestRate = 0.0;
			double interestEarned = 0.0;
			double totalInterestEarned = 0.0;
			double closingBalEPF = 0.0;
			double closingBalEPS = 0.0;
			String strRefDate = "";
			Date dtLastDayOfMonth = null;
			int serialNo = 1;
			MasterDAO mastersukDao = new MasterDAOImplementation();
			List<MasterEPF2Calculator> masterEPF2CalculatorList = mastersukDao.getMasterEPF2CalculatorInterestRates();
			Calendar currentDate = Calendar.getInstance();
			currentDate.set(Calendar.MILLISECOND, 0);
			currentDate.set(Calendar.SECOND, 0);
			currentDate.set(Calendar.MINUTE, 0);
			currentDate.set(Calendar.HOUR_OF_DAY, 0);
			double epfoInterestRate = mastersukDao.getMasterEPFRates(currentDate.getTime());
			epf2Cal.setCurrentInterestRate(epfoInterestRate);
			boolean checkInterestRate = false;
			while (true) {
				EPF2Lookup epf2Lookup = new EPF2Lookup();
				strInterimDate = sdfOutput.format(planningDateCal.getTime());
				strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);
				String strRefMonth = String.valueOf(planningDateCal.get(Calendar.MONTH) + 1);
				String fiscalYear = FinexaDateUtil.getFiscalYear(planningDateCal.getTime());
				try {
					dtLastDayOfMonth = sdfOutput.parse(strRefDate);
					long diff = dtLastDayOfMonth.getTime() - clientDOB.getTime();
					clientAge = (int) (TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) / 365.25);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
              for (MasterEPF2Calculator masterEPF2Calculator : masterEPF2CalculatorList) {
					
					if ((dtLastDayOfMonth.after(masterEPF2Calculator.getStartDate())
							&& dtLastDayOfMonth.before(masterEPF2Calculator.getEndDate()))
							|| dtLastDayOfMonth.equals(masterEPF2Calculator.getStartDate())
							|| dtLastDayOfMonth.equals(masterEPF2Calculator.getEndDate())) {
						interestRate = masterEPF2Calculator.getInterestRate();
						
						checkInterestRate = true;
					}
					if (checkInterestRate) {
						break;
					}

				}
             
			

				if (serialNo == 1) {
					monthlyBasicDa = monthlyBasicDA;
					openingBalEPF = currEPFBal;
					openingBalEPS = currEPSBal;
					interestEarned = (currEPFBal) * ((interestRate) / (double) 12);
					totalInterestEarned = interestEarned;
				} else {		
					if (planningDateCal.get(Calendar.MONTH) + 1 == calRefMonth.get(Calendar.MONTH) + 1) {
						monthlyBasicDa = (1 + expectedIncreaseSal) * monthlyBasicDa;
					}
					openingBalEPF = closingBalEPF;
					openingBalEPS = closingBalEPS;
					interestEarned = (openingBalEPF) * ((interestRate) / 12);
					totalInterestEarned = totalInterestEarned + interestEarned;
				}

				employeeContEPF = (12 / (double) 100) * monthlyBasicDa;
				totalEmployeeCont = totalEmployeeCont + employeeContEPF;
				if (clientAge <= 57) {
					employerContEPS = Math.min((8.33 / (double) 100) * monthlyBasicDa, 1250);
				} else {
					employerContEPS = 0;
				}
				totalEmployerContEPS = totalEmployerContEPS + employerContEPS;
				employerContEPF = employeeContEPF - employerContEPS;
				totalEmployerContEPF = totalEmployerContEPF + employerContEPF;

				closingBalEPF = interestEarned + employerContEPF + employeeContEPF + openingBalEPF;
				closingBalEPS = employerContEPS + openingBalEPS;
		
				epf2Lookup.setSerialNumber(serialNo);
				epf2Lookup.setRefDate(dtLastDayOfMonth);
				epf2Lookup.setDisplayDate(displayDateFormat.format(dtLastDayOfMonth));
				epf2Lookup.setReferenceMonth(strRefMonth);
				epf2Lookup.setFinancialYear(fiscalYear);
				epf2Lookup.setClientAge(clientAge);
				epf2Lookup.setMonBasicDA(monthlyBasicDa);
				epf2Lookup.setOpeningBalEPF(openingBalEPF);
				epf2Lookup.setOpeningBalEPS(openingBalEPS);
				epf2Lookup.setEmployeeContEPF(employeeContEPF);
				epf2Lookup.setEmployerContEPF(employerContEPF);
				epf2Lookup.setEmployerContEPS(employerContEPS);
				epf2Lookup.setInterestRateEPF(interestRate);
				epf2Lookup.setInterestEarnedEPF(interestEarned);
				epf2Lookup.setTotalInterestEarnedEPF(totalInterestEarned);
				epf2Lookup.setClosingBalEPF(closingBalEPF);
				epf2Lookup.setClosingBalEPS(closingBalEPS);
				
				
				/*System.out.println("SerialNumber "+epf2Lookup.getSerialNumber());
				System.out.println("RefDate "+epf2Lookup.getRefDate());
				System.out.println("dtLastDayOfMonth "+displayDateFormat.format(dtLastDayOfMonth));
				System.out.println("strRefMonth "+strRefMonth);
				System.out.println("fiscalYear "+fiscalYear);
				System.out.println("clientAge "+clientAge);
				System.out.println("monthlyBasicDa "+monthlyBasicDa);
				System.out.println("openingBalEPF "+openingBalEPF);
				System.out.println("openingBalEPS  "+openingBalEPS);
				System.out.println("employeeContEPF "+employeeContEPF);
				System.out.println("employerContEPF "+employerContEPF);
				System.out.println("employerContEPS "+employerContEPS);
				System.out.println("interestRate "+interestRate);
				System.out.println("interestEarned "+interestEarned);
				System.out.println("totalInterestEarned "+totalInterestEarned);;
				System.out.println("closingBalEPF "+closingBalEPF);
				System.out.println("closingBalEPS "+closingBalEPS);
				
				System.out.println("===================================== ");
*/
				planningDateCal.add(Calendar.MONTH, 1);

				if (planningDateCal.getTime().compareTo(epfBalDate.getTime()) == 1) {				
					epf2Cal.setEpsBalClientAge(closingBalEPS);
				}
				if (dtLastDayOfMonth.compareTo(epfBalDate.getTime()) == 1) {
					break;
				}
				
				epf2LoohkupList.add(epf2Lookup);
				serialNo++;
			}
			
			
       
			
		} catch (Exception exp) {
			exp.printStackTrace();
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_EPF,
					FinexaConstant.PRODUCT_CAL_EPF_CODE, FinexaConstant.PRODUCT_CAL_EPF_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		epf2Cal.setLookupList(epf2LoohkupList);
		return epf2Cal;
	}
	
/*	public static void main(String args[]) {
		EPF2Service epf2Service = new EPF2Service();
		Calendar clientCal = Calendar.getInstance();
		clientCal.set(1979, 2, 11);
		Date clientDOB = clientCal.getTime();
		double currEPFBal = 3140690;
		double currEPSBal = 147948;
		double monthlyBasicDA = 125000;
		double expectedIncreaseSal = 0.08; 
	    int contributionUptoAge = 43;
	    int epfWithdrawAlage = 43;
	    String increaseMonth = "April";
		
		
		EPF2Calculator cal = epf2Service.getEPF2CaculatedValues(clientDOB, currEPFBal, 
				currEPSBal, monthlyBasicDA, expectedIncreaseSal, contributionUptoAge, epfWithdrawAlage, increaseMonth);
		System.out.println(cal.getEpfBalWithdraw());
	}*/
	
}

package com.finlabs.finexa.resources.service;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.finlabs.finexa.resources.dao.MasterDAO;
import com.finlabs.finexa.resources.dao.MasterDAOImplementation;
import com.finlabs.finexa.resources.exception.FinexaBussinessException;
import com.finlabs.finexa.resources.model.KisanVikasPatra;
import com.finlabs.finexa.resources.model.KisanVikasPatraLookup;
import com.finlabs.finexa.resources.model.MasterKisanVikasPatra;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class KisanVikasPatraService {
	
	public static void main(String args[]) {
		 Date date = null;
		 Date dt = null;
		   /* try {
		        date = dateFormat.parse("2017-01-17");
		        System.out.println(date.toString()); // Wed Dec 04 00:00:00 CST 2013

		        String output = dateFormat.format(date);
		        System.out.println(output); // 2013-12-04
		         dt = dateFormat.parse(output);
		    } 
		    catch (ParseException e) {
		        e.printStackTrace();
		    }
		
		new MutualFundLumpsumSipService().getMutualFundSIPCalculationPortFolio(10000, "INF179KA1IZ7", dt,12,12);*/
		
		//new MutualFundLumpsumSipService().getMutualFundSIPCalculationForPorfolio(10000, "INF179KA1IZ7", dt,6,12);
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   try {
	       date = dateFormat.parse("2015-03-24");
	      // System.out.println(date.toString()); // Wed Dec 04 00:00:00 CST 2013

	       String output = dateFormat.format(date);
	       //System.out.println(output); // 2013-12-04
	       dt = dateFormat.parse(output);
	      // System.out.println("dt "+dt);
	       new KisanVikasPatraService().getKisanVikasPatraCalc(50000,dt);
	   } 
	   catch (ParseException e) {
	       e.printStackTrace();
	   }

		
	}

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
	double interestReceived = 0.0;
	double interestAccrued = 0.0;
	double totalInterestReceived = 0.0;
	double totalInterestAccrued = 0.0;
	long daysToMaturity = 0;
	Date dtLastDayOfMonth = new Date();
	Date dtMaturityDate = new Date();
	double rateOfInterest = 0.0;

	public KisanVikasPatra getKisanVikasPatraCalc(double deposit, Date depositDate) {
		//System.out.println("deposit "+deposit);
		//System.out.println("depositDate "+depositDate);
		MasterDAO masterkvpDao = new MasterDAOImplementation();
		int varTermMonths = 0;
		KisanVikasPatra kisanVikasPatra = new KisanVikasPatra();
		List<KisanVikasPatraLookup> kisanVikasPatraLookupList = new ArrayList<KisanVikasPatraLookup>();
		try {
			MasterKisanVikasPatra masterKvp = masterkvpDao.getKvpTerm(depositDate);
          //  System.out.println("year "+masterKvp.getYear());
          //  System.out.println("month "+masterKvp.getMonth());
			varTermMonths = (int) masterKvp.getYear() * 12 + masterKvp.getMonth();
		//	System.out.println("varTermMonths "+varTermMonths);
			kisanVikasPatra.setDisplayTermYear(masterKvp.getYear());
			kisanVikasPatra.setDisplayTermMonth(masterKvp.getMonth());

			masterKvp = masterkvpDao.getKvpCompoundFreq(depositDate);
			String frequencyDesc = masterkvpDao.getFrequencyDescById(masterKvp.getCompundFreq());
			//System.out.println("frequencyDesc "+frequencyDesc);
			kisanVikasPatra.setDisplayCompundingFreq(frequencyDesc);
			int varInterestFreq = masterKvp.getCompundFreq();
			//System.out.println("varInterestFreq "+varInterestFreq);
			openingBal = deposit;
			//System.out.println("openingBal "+openingBal);
			double closingBal = deposit;
			//System.out.println("closingBal "+closingBal);
			int interestFreqConstantLookup = 12 / varInterestFreq;
			//System.out.println("interestFreqConstantLookup "+interestFreqConstantLookup);
			int interestFreqInterestPaid = varInterestFreq;
			//System.out.println("interestFreqInterestPaid "+interestFreqInterestPaid);
			Date varDepositDate = depositDate;
			//System.out.println("varDepositDate "+varDepositDate);
			totalInterestAccrued = 0.0;

			cDepositDate.setTime(varDepositDate);
			cInterimDate.setTime(varDepositDate);
			cMaturityDate.setTime(varDepositDate);
			cMaturityDate.add(Calendar.MONTH, varTermMonths);
			//System.out.println("cDepositDate "+cDepositDate.getTime());
			//System.out.println("cInterimDate "+cInterimDate.getTime());
			//System.out.println("cMaturityDate "+cMaturityDate.getTime());

			kisanVikasPatra.setMaturityDate(cMaturityDate.getTime());
			strInterimDate = sdfOutput.format(cDepositDate.getTime());

			int serialNo = 0;

			List<MasterKisanVikasPatra> masterKisanVikasPatraList = masterkvpDao.getKvpInterestRates();
		/*	Collections.sort(masterKisanVikasPatraList, new Comparator<MasterKisanVikasPatra>() {
				@Override
				public int compare(MasterKisanVikasPatra masterkvp1, MasterKisanVikasPatra masterkvp2) {

					return masterkvp1.getInvestmentPeriod().compareTo(masterkvp2.getInvestmentPeriod());
				}
			});*/

			for (MasterKisanVikasPatra masterKisanVikasPatra : masterKisanVikasPatraList) {
				if ((depositDate.after(masterKisanVikasPatra.getDepositFromDate())
						&& depositDate.before(masterKisanVikasPatra.getDepositToDate()))
						|| depositDate.equals(masterKisanVikasPatra.getDepositFromDate())
						|| depositDate.equals(masterKisanVikasPatra.getDepositToDate())) {
					// if (0 >= masterKisanVikasPatra.getInvestmentPeriod()) {
					kisanVikasPatra.setDisplayAnnualInterestRate(masterKisanVikasPatra.getInterestRate());
					//System.out.println("DisplayAnnualInterestRate "+masterKisanVikasPatra.getInterestRate());
					
					
					break;
					/*
					 * } else {
					 * 
					 * }
					 */
				}

			}

			while (true) {
				KisanVikasPatraLookup kisanVikasPatraLookup = new KisanVikasPatraLookup();

				Date dtInterimDate = cInterimDate.getTime();
				strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);
				//System.out.println("strRefDate "+strRefDate);
				try {
					dtLastDayOfMonth = sdfOutput.parse(strRefDate);
					long diff = cMaturityDate.getTime().getTime() - dtLastDayOfMonth.getTime();
					daysToMaturity = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
					//System.out.println("daysToMaturity "+daysToMaturity);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				strRefMonth = strRefDate.substring(3);

				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(0);
				// cal.set(2015, 2, 24, 10, 00, 00);
				cal.setTime(depositDate);
				// System.out.println(cal.getTime());
				Calendar cal2 = Calendar.getInstance();
				cal2.setTimeInMillis(0);
				// cal2.set(2016, 2, 31, 10, 00, 00);
				cal2.setTime(dtLastDayOfMonth);
				// System.out.println(cal2.getTime());
				LocalDate dateTo = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
						cal.get(Calendar.DATE));
				LocalDate dateFrom = LocalDate.of(cal2.get(Calendar.YEAR), cal2.get(Calendar.MONTH) + 1,
						cal2.get(Calendar.DATE));
				Period intervalPeriod = Period.between(dateTo, dateFrom);
				long years = intervalPeriod.getYears();
				long months = intervalPeriod.getMonths();
				long calculateddays = intervalPeriod.getDays();
				//System.out.println("years "+years);
				//System.out.println("months "+months);
				//System.out.println("calculateddays "+calculateddays);
				double investmentPeriod = Double.parseDouble(new DecimalFormat(".##")
						.format(years + (months / (double) 12) + (calculateddays / (double) 365.25)));
               // System.out.println("investmentPeriod "+investmentPeriod);
				double lastInterestRate = 0.0;
				//int investmentSerial = 1;
				for (MasterKisanVikasPatra masterKisanVikasPatra : masterKisanVikasPatraList) {
					if ((depositDate.after(masterKisanVikasPatra.getDepositFromDate())
							&& depositDate.before(masterKisanVikasPatra.getDepositToDate()))
							|| depositDate.equals(masterKisanVikasPatra.getDepositFromDate())
							|| depositDate.equals(masterKisanVikasPatra.getDepositToDate())) {
						//if (investmentPeriod > masterKisanVikasPatra.getInvestmentPeriod()) {
						//	lastInterestRate = masterKisanVikasPatra.getInterestRate();
					//	} else {
							//if (investmentSerial == 1) {
								lastInterestRate = masterKisanVikasPatra.getInterestRate();
							//}
							break;
						//}
						//investmentSerial++;
					}

				}
				fiscalYear = FinexaDateUtil.getFiscalYear(dtInterimDate);
				openingBal = closingBal;
				rateOfInterest = lastInterestRate;
				if (serialNo > 0) {
					interestAccrued = openingBal * (rateOfInterest) / interestFreqConstantLookup
							/ interestFreqInterestPaid;
				}

				totalInterestAccrued = totalInterestAccrued + interestAccrued;
				//System.out.println("totalInterestAccrued "+totalInterestAccrued);
				//System.out.println("interestAccrued "+interestAccrued);
				//System.out.println("totalInterestReceived "+totalInterestReceived);
				//System.out.println("serialNo "+serialNo);
				//System.out.println("interestFreqConstantLookup "+interestFreqConstantLookup);
				//System.out.println("serialNo % interestFreqConstantLookup "+(serialNo % interestFreqConstantLookup));
				if (serialNo % interestFreqConstantLookup == 0) {
					//System.out.println("serialNo "+serialNo);
					if (serialNo > 0) {
						//System.out.println("totalInterestAccrued"+totalInterestAccrued);
						//System.out.println("totalInterestReceived"+totalInterestReceived);
						interestReceived = totalInterestAccrued - totalInterestReceived;
						//System.out.println("interestReceived"+interestReceived);
						
					}
				} else {
					interestReceived = 0;
				}
				//System.out.println("interestReceived "+interestReceived);
				//System.out.println("totalInterestReceived "+totalInterestReceived);
				
				totalInterestReceived = totalInterestReceived + interestReceived;
				//System.out.println("openingBal "+openingBal);
				closingBal = closingBal + interestReceived;
				//System.out.println("interestReceived "+interestReceived);
				//System.out.println("closingBal "+closingBal);
				if (dtLastDayOfMonth.after(cMaturityDate.getTime())) {
                //new code
					//System.out.println("after ");
					//System.out.println("totalInterestAccrued"+totalInterestAccrued);
					//System.out.println("totalInterestReceived"+totalInterestReceived);
					interestReceived = totalInterestAccrued - totalInterestReceived;
					//System.out.println("interestReceived"+interestReceived);
					
					//System.out.println("openingBal "+openingBal);
					closingBal = closingBal + interestReceived;
					//System.out.println("interestReceived "+interestReceived);
					//System.out.println("closingBal "+closingBal);
				}
				kisanVikasPatraLookup.setSerialNo(serialNo);
				kisanVikasPatraLookup.setReferenceDate(strRefDate);
				kisanVikasPatraLookup.setReferenceMonth(strRefMonth);
				kisanVikasPatraLookup.setFinancialYear(fiscalYear);
				kisanVikasPatraLookup.setYearOfInvestment(investmentPeriod);
				kisanVikasPatraLookup.setInterestRate(rateOfInterest);
				kisanVikasPatraLookup.setOpeningBal(Math.round(openingBal));
				kisanVikasPatraLookup.setInterestAccrued(interestAccrued);
				kisanVikasPatraLookup.setInterestReceived(Math.round(interestReceived));
				kisanVikasPatraLookup.setTotalInterestReceived(Math.round(totalInterestAccrued));
				kisanVikasPatraLookup.setClosingBalance(Math.round(closingBal));
				kisanVikasPatraLookup.setDaysToMaturity(daysToMaturity);

				kisanVikasPatraLookupList.add(kisanVikasPatraLookup);
				
				//Result========
				/*System.out.println("serialNo "+kisanVikasPatraLookup.getSerialNo());
				System.out.println("ReferenceDate "+kisanVikasPatraLookup.getReferenceDate());
				System.out.println("ReferenceMonth "+kisanVikasPatraLookup.getReferenceMonth());
				System.out.println("FinancialYear "+kisanVikasPatraLookup.getFinancialYear());
				System.out.println("YearOfInvestment "+kisanVikasPatraLookup.getYearOfInvestment());
				System.out.println("InterestRate "+kisanVikasPatraLookup.getInterestRate());
				System.out.println("OpeningBal "+kisanVikasPatraLookup.getOpeningBal());
				System.out.println("interestAccrued "+kisanVikasPatraLookup.getInterestAccrued());
				System.out.println("InterestReceived "+kisanVikasPatraLookup.getInterestReceived());
				System.out.println("totalInterestAccrued "+kisanVikasPatraLookup.getInterestAccrued());
				System.out.println("TotalInterestReceived "+kisanVikasPatraLookup.getTotalInterestReceived());
				System.out.println("closingBal "+kisanVikasPatraLookup.getClosingBalance());
				System.out.println("matuirity "+kisanVikasPatraLookup.getDaysToMaturity());*/
				//============
				cInterimDate.add(Calendar.MONTH, 1);
				dtInterimDate = cInterimDate.getTime();
				//System.out.println("dtInterimDate "+dtInterimDate);
				serialNo++;
				strInterimDate = sdfOutput.format(dtInterimDate);
				if (dtLastDayOfMonth.after(cMaturityDate.getTime())) {
					
					break;
				}
				//System.out.println("=====================");
			}
			kisanVikasPatra.setMaturityAmount(closingBal);
			//System.out.println("MaturityAmount "+kisanVikasPatra.getMaturityAmount());
			kisanVikasPatra.setTotalInterestReceived(kisanVikasPatra.getMaturityAmount() - deposit);
			//System.out.println("TotalInterestReceived "+kisanVikasPatra.getTotalInterestReceived());
		}catch(

	Exception exp)
	{
			exp.printStackTrace();
		FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_KVP,
				FinexaConstant.PRODUCT_CAL_KVP_CODE, FinexaConstant.PRODUCT_CAL_KVP_DESC, exp);
		FinexaBussinessException.logFinexaBusinessException(finexaBuss);
	}kisanVikasPatra.setKisanVikasPatraLookupList(kisanVikasPatraLookupList);return kisanVikasPatra;
}

}

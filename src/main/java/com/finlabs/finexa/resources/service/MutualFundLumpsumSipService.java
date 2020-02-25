
package com.finlabs.finexa.resources.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.finlabs.finexa.resources.dao.MasterDAO;
import com.finlabs.finexa.resources.dao.MasterDAOImplementation;
import com.finlabs.finexa.resources.exception.FinexaBussinessException;
import com.finlabs.finexa.resources.model.MasterMutualFundLumpsumSip;
import com.finlabs.finexa.resources.model.MutualFundLumpsumSip;
import com.finlabs.finexa.resources.model.MutualFundLumpsumSipLookup;
import com.finlabs.finexa.resources.util.FinanceUtil;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;
import com.finlabs.finexa.resources.util.Transaction;

public class MutualFundLumpsumSipService {

	Calendar calInstance = Calendar.getInstance();
	String strInterimDate = "";
	SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MMMM-yyyy");
	SimpleDateFormat xirrDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	MasterDAO mastermfDao = new MasterDAOImplementation();

	List<Transaction> xirrTransactionList = new ArrayList<>();

	public MutualFundLumpsumSip getMutualFundLumpsumCalculation(double amountInvested, String isin, Date investedDate,
			int unitPurchased) {
		//System.out.println("amountInvested "+amountInvested);
		//System.out.println("isin "+isin);
		//System.out.println("investedDate "+investedDate);
		//System.out.println("unitPurchased "+unitPurchased);
		
		List<MutualFundLumpsumSipLookup> mutualFundLumpsumSipList = new ArrayList<MutualFundLumpsumSipLookup>();

		MutualFundLumpsumSipLookup mutualFundLumpSumLookup = null;
		MutualFundLumpsumSip mutualFundLumpSum = new MutualFundLumpsumSip();
		Calendar generalCal = Calendar.getInstance();
		double ouputcurrentUnitPurchased = 0.0;
		try {

			generalCal.setTime(investedDate);
			System.out.println("generalCal "+generalCal.getTime());
			String strRefDate = "";
			String strRefMonth = "";
			String fiscalYear = "";
			int monthCounter = -1;
			double openingBal = 0.0;
			double currAmountInvested = 0.0;
			double currentUnitPurchased = 0.0;
			double closingBal = 0.0;
			double lastPortfolioValue = 0.0;
			double currNavRate = 0.0;
			double portfolioValue = 0.0;
			double currPtpReturns = 0.0;
			double annualisedCagr = 0.0;
			double investmentTenure = 0.0;
			Calendar portfolioValueDate = Calendar.getInstance();
			portfolioValueDate.set(Calendar.MILLISECOND, 0);
			portfolioValueDate.set(Calendar.SECOND, 0);
			portfolioValueDate.set(Calendar.MINUTE, 0);
			portfolioValueDate.set(Calendar.HOUR, 0);
			
			//System.out.println("portfolioValueDate "+portfolioValueDate.getTime());
			
			List<MasterMutualFundLumpsumSip> mastermfnavList = mastermfDao
					.getMasterMutualFundLumpsumSipNavs(investedDate, portfolioValueDate.getTime(), isin);
			System.out.println("mastermfnavList.size() "+mastermfnavList.size());
			System.out.println("generalCal.getTime().compareTo(portfolioValueDate.getTime() "+generalCal.getTime().compareTo(portfolioValueDate.getTime()));
			while (generalCal.getTime().compareTo(portfolioValueDate.getTime()) == -1) {
				mutualFundLumpSumLookup = new MutualFundLumpsumSipLookup();
				if (monthCounter == -1) {
					strInterimDate = sdfOutput.format(generalCal.getTime());

					mutualFundLumpSumLookup.setRefDate(sdfOutput.parse(sdfOutput.format(investedDate)));
					strRefMonth = strInterimDate.substring(3);
					fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
					currAmountInvested = amountInvested;

					if(mastermfnavList.isEmpty()){
						break;
					}
					currNavRate = returnNAVRate(mutualFundLumpSumLookup.getRefDate(), mastermfnavList, isin);

					currentUnitPurchased = currAmountInvested / currNavRate;
					if (currentUnitPurchased > 0) {
						ouputcurrentUnitPurchased = currentUnitPurchased;
					}
					closingBal = currentUnitPurchased + openingBal;
					portfolioValue = currNavRate * closingBal;
					lastPortfolioValue = portfolioValue;
					mutualFundLumpSumLookup.setScheme("Mutual Fund");
					mutualFundLumpSumLookup.setNav(currNavRate);
					mutualFundLumpSumLookup.setReferenceMonth(strRefMonth);
					mutualFundLumpSumLookup.setFinancialYear(fiscalYear);
					mutualFundLumpSumLookup.setAmountInvested(currAmountInvested);
					mutualFundLumpSumLookup.setUnitsPurchased(currentUnitPurchased);
					mutualFundLumpSumLookup.setClosingBal(closingBal);
					mutualFundLumpSumLookup.setPortfolioValue(portfolioValue);
					mutualFundLumpsumSipList.add(mutualFundLumpSumLookup);
					
					//System.out.println("currAmountInvested 111 "+currAmountInvested);
					//System.out.println("currNavRate 111  "+currNavRate);
					//System.out.println("currentUnitPurchased 111  "+currentUnitPurchased);
					//System.out.println("openingBal 111  "+openingBal);
					//System.out.println("closingBal 111  "+closingBal);
					//System.out.println("portfolioValue 111  "+portfolioValue);
					
					monthCounter++;
					System.out.println("=================================");
				} else {
					if (monthCounter > 0) {
						generalCal.add(Calendar.MONTH, 1);
					}
					//System.out.println("generalCal "+generalCal.getTime());
					
					strInterimDate = sdfOutput.format(generalCal.getTime());
					System.out.println("strInterimDate "+strInterimDate);
					
					strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);
					System.out.println("strRefDate "+strRefDate);
					
					mutualFundLumpSumLookup.setRefDate(sdfOutput.parse(strRefDate));
					
				    //new
					Calendar lastDate = Calendar.getInstance();
					lastDate.setTime(sdfOutput.parse(strRefDate));
					//System.out.println("LastDate "+lastDate.getTime());
					//System.out.println("LastDate.getTime().compareTo(portfolioValueDate.getTime()) "+lastDate.getTime().compareTo(portfolioValueDate.getTime()));
					
					
					if (lastDate.getTime().compareTo(portfolioValueDate.getTime()) >= 0) {
						portfolioValueDate.set(Calendar.MILLISECOND, 0);
						portfolioValueDate.set(Calendar.SECOND, 0);
						portfolioValueDate.set(Calendar.MINUTE, 0);
						portfolioValueDate.set(Calendar.HOUR, 0);
						
						mutualFundLumpSumLookup.setRefDate(portfolioValueDate.getTime());

						currNavRate = returnNAVRate(mutualFundLumpSumLookup.getRefDate(), mastermfnavList, isin);
						mutualFundLumpSumLookup.setScheme("Mutual Fund");
						currentUnitPurchased = currAmountInvested / currNavRate;
						if (currentUnitPurchased > 0) {
							ouputcurrentUnitPurchased = currentUnitPurchased;
						}
						closingBal = currentUnitPurchased + openingBal;
						//System.out.println("closingBal "+closingBal);
						//System.out.println("currNavRate "+currNavRate);
						portfolioValue = currNavRate * closingBal;
						//System.out.println("portfolioValue "+portfolioValue);
						
						Calendar cstartDate = Calendar.getInstance();
						cstartDate.setTime(investedDate);
					    //new
						investmentTenure = FinanceUtil.YEARFRAC(cstartDate.getTime(), portfolioValueDate.getTime(), 1);
						//System.out.println("investmentTenure "+investmentTenure);
						
						annualisedCagr = (Math.pow(portfolioValue / lastPortfolioValue, (1 / investmentTenure)) - 1);
						currPtpReturns = ((portfolioValue - lastPortfolioValue) / (double) lastPortfolioValue) * 100;

						mutualFundLumpSumLookup.setReferenceMonth(strRefMonth);
						mutualFundLumpSumLookup.setFinancialYear(fiscalYear);
						mutualFundLumpSumLookup.setOpeningBal(openingBal);
						mutualFundLumpSumLookup.setClosingBal(closingBal);
						mutualFundLumpSum.setTotalInvestedAmount(amountInvested);
						mutualFundLumpSumLookup.setNav(currNavRate);
						mutualFundLumpSumLookup.setPortfolioValue(portfolioValue);
						mutualFundLumpSum.setPortfolioValue(portfolioValue);
						mutualFundLumpSum.setPtpReturns(currPtpReturns);
						mutualFundLumpSum.setCacgr(annualisedCagr);
						mutualFundLumpSumLookup.setPtpReturns(currPtpReturns);
						mutualFundLumpSumLookup.setAnnualisedCagrReturns(annualisedCagr);
						mutualFundLumpSumLookup.setUnitsPurchased(currentUnitPurchased);
						mutualFundLumpSumLookup.setInvestmentTenure(investmentTenure);
						mutualFundLumpsumSipList.add(mutualFundLumpSumLookup);
						
						/*System.out.println("currAmountInvested 222 "+currAmountInvested);
						System.out.println("currNavRate 222  "+currNavRate);
						System.out.println("currentUnitPurchased 222  "+currentUnitPurchased);
						System.out.println("openingBal 222  "+openingBal);
						System.out.println("closingBal 222  "+closingBal);
						System.out.println("portfolioValue 222  "+portfolioValue);
						System.out.println("annualisedCagr "+annualisedCagr);
						System.out.println("currPtpReturns "+currPtpReturns);
						System.out.println("=================================break");*/
						break;
					}else {
						

						strRefMonth = strRefDate.substring(3);
						
						openingBal = closingBal;
						
						fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
						
						currAmountInvested = 0.0;
						
						currNavRate = returnNAVRate(mutualFundLumpSumLookup.getRefDate(), mastermfnavList, isin);
						
						currentUnitPurchased = currAmountInvested / currNavRate;
						if (currentUnitPurchased > 0) {
							ouputcurrentUnitPurchased = currentUnitPurchased;
						}
						closingBal = currentUnitPurchased + openingBal;
						
						/*System.out.println("currNavRate           "+currNavRate);
						System.out.println("openingBal "+openingBal);
						System.out.println("currentUnitPurchased "+currentUnitPurchased);
						System.out.println("closingBal "+closingBal);*/

						Calendar cstartDate = Calendar.getInstance();
						Calendar portValueDate = Calendar.getInstance();

						cstartDate.setTime(investedDate);
						portValueDate.setTime(sdfOutput.parse(strRefDate));
						
						 //System.out.println("cstartDate.getTime() "+cstartDate.getTime());
		                // System.out.println("portValueDate.getTime() "+portValueDate.getTime());
		                    

						investmentTenure = FinanceUtil.YEARFRAC(cstartDate.getTime(), portValueDate.getTime(), 1);
						//System.out.println("investmentTenure "+investmentTenure);
						portfolioValue = currNavRate * closingBal;
						// System.out.println("portfolioValue / lastPortfolioValue "+(portfolioValue / lastPortfolioValue));
						// System.out.println("(Math.pow(portfolioValue / lastPortfolioValue, (1 / investmentTenure)) "+(Math.pow(portfolioValue / lastPortfolioValue, (1 / investmentTenure))));
						annualisedCagr = (Math.pow(portfolioValue / lastPortfolioValue, (1 / investmentTenure)) - 1);


						 /*System.out.println("annualisedCagr "+annualisedCagr);
						 System.out.println("portfolioValue "+portfolioValue);
						 System.out.println("lastPortfolioValue "+lastPortfolioValue);
						 */
						currPtpReturns = ((portfolioValue - lastPortfolioValue) / (double) lastPortfolioValue) * 100;
						 System.out.println("currPtpReturns "+currPtpReturns);
						
						//System.out.println("generalCal.getTime( "+generalCal.getTime());
		               // System.out.println("portfolioValueDate.getTime() "+portfolioValueDate.getTime());
		               // System.out.println("generalCal.getTime().compareTo(portfolioValueDate.getTime() "+generalCal.getTime().compareTo(portfolioValueDate.getTime()));
		            	//System.out.println("=================================");
					}
					
					
					 
					mutualFundLumpSumLookup.setReferenceMonth(strRefMonth);
					mutualFundLumpSumLookup.setFinancialYear(fiscalYear);
					mutualFundLumpSumLookup.setAmountInvested(currAmountInvested);
					mutualFundLumpSumLookup.setOpeningBal(openingBal);
					mutualFundLumpSumLookup.setUnitsPurchased(currentUnitPurchased);
					mutualFundLumpSumLookup.setClosingBal(closingBal);
					mutualFundLumpSumLookup.setNav(currNavRate);
					mutualFundLumpSumLookup.setPortfolioValue(portfolioValue);
					mutualFundLumpSumLookup.setPtpReturns(currPtpReturns);
					mutualFundLumpSumLookup.setAnnualisedCagrReturns(annualisedCagr);
					mutualFundLumpSumLookup.setInvestmentTenure(investmentTenure);
					mutualFundLumpSumLookup.setMonthCounter(monthCounter);
					mutualFundLumpSumLookup.setScheme("Mutual Fund");
					mutualFundLumpsumSipList.add(mutualFundLumpSumLookup);

					monthCounter++;
				}

			}

		} catch (Exception exp) {
			exp.printStackTrace();
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_MUTUAL_FUND,
					FinexaConstant.PRODUCT_CAL_MUTUAL_FUND_LUMSUM, FinexaConstant.PRODUCT_CAL_MUTUAL_FUND_LUMSUM_DESC,
					exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		mutualFundLumpSum.setMutualFundLumpsumSipList(mutualFundLumpsumSipList);
		mutualFundLumpSum.setUnitPurchased((int) ouputcurrentUnitPurchased);

		return mutualFundLumpSum;
	}
	public MutualFundLumpsumSip getMutualFundSIPCalculation(double sipAmount, String isin, Date sipStartDate,
			int sipInstallment, int sipFrequency) {
		
		//System.out.println("sipAmount "+sipAmount);
		//System.out.println("isin "+isin);
		//System.out.println("sipStartDate "+sipStartDate);
		//System.out.println("sipInstallment "+sipInstallment);
		//System.out.println("sipFrequency "+sipFrequency);
		double currentUnitPurchasedTotal = 0;
		double currentPortfoliovalue = 0;

		
		List<MutualFundLumpsumSipLookup> mutualFundLumpsumSipList = new ArrayList<MutualFundLumpsumSipLookup>();
		MutualFundLumpsumSipLookup mutualFundSipLookup = null;
		MutualFundLumpsumSip mutualFundSip = new MutualFundLumpsumSip();
		try {
			Calendar generalCal = Calendar.getInstance();

			generalCal.setTime(sipStartDate);
			Calendar endDate = Calendar.getInstance();
			endDate.setTime(generalCal.getTime());
			String strRefMonth = "";
			String strRefDate = "";
			String fiscalYear = "";
			int monthCounter = 1;
			int serialCounter = 0;
			double openingBal = 0.0;
			double currAmountInvested = 0.0;
			double currentUnitPurchased = 0.0;
			double closingBal = 0.0;
			double currNavRate = 0.0;
			double portfolioValue = 0.0;
			double totalAmountInvested = 0.0;
			double PTPReturn;
			Calendar portfolioValueDate = Calendar.getInstance();
			endDate.add(Calendar.MONTH, (sipInstallment * (12 / sipFrequency) - 1));

			Date sipEndDate = endDate.getTime();
			mutualFundSip.setSipEndDate(sipEndDate);

			generalCal.set(Calendar.MILLISECOND, 0);
			generalCal.set(Calendar.SECOND, 0);
			generalCal.set(Calendar.MINUTE, 0);
			generalCal.set(Calendar.HOUR, 0);
		
			portfolioValueDate.set(Calendar.MILLISECOND, 0);
			portfolioValueDate.set(Calendar.SECOND, 0);
			portfolioValueDate.set(Calendar.MINUTE, 0);
			portfolioValueDate.set(Calendar.HOUR, 0);
			
			//System.out.println("startdate "+sipStartDate);
			//System.out.println("endate "+endDate);
		   //System.out.println("isin "+isin);
			
			
			List<MasterMutualFundLumpsumSip> mastermfnavList = mastermfDao
					.getMasterMutualFundLumpsumSipNavs(sipStartDate, portfolioValueDate.getTime(), isin);
			
			int installmentCounter=1;
			int flag=0;
			while (generalCal.getTime().compareTo(portfolioValueDate.getTime()) == -1) {
				mutualFundSipLookup = new MutualFundLumpsumSipLookup();
				mutualFundSipLookup.setScheme("Mutual Fund");
				if (serialCounter == 0) {
					strInterimDate = sdfOutput.format(generalCal.getTime());

					mutualFundSipLookup.setRefDate(sdfOutput.parse(sdfOutput.format(sipStartDate)));
					currNavRate =  returnNAVRate(mutualFundSipLookup.getRefDate(), mastermfnavList, isin);

					strRefMonth = strInterimDate.substring(3);
					fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
					
				
					currAmountInvested = 0;
					if(installmentCounter <= sipInstallment) {
					currAmountInvested = sipAmount;
					currentUnitPurchased = currAmountInvested / currNavRate;
					currentUnitPurchasedTotal = currentUnitPurchasedTotal+currentUnitPurchased;
					closingBal = currentUnitPurchased + openingBal;
					
					totalAmountInvested = currAmountInvested;
					mutualFundSipLookup.setAmountInvested(currAmountInvested);
					}else{
					currentPortfoliovalue = currentUnitPurchasedTotal*currNavRate;
				   }
				//	System.out.println("currAmountInvested 1st "+currAmountInvested);
				//	System.out.println("currNavRate 1st "+currNavRate);
				//	System.out.println("currentUnitPurchased(currAmountInvested / currNavRate 1st) "+currentUnitPurchased);
				//	System.out.println("currentUnitPurchasedTotal "+currentUnitPurchasedTotal);
				//	System.out.println("pppp currentPortfoliovalue 1st "+currentPortfoliovalue);
				//	System.out.println("openingBal 1st "+openingBal);
				//	System.out.println("(closingBal = currentUnitPurchased + openingBal) 1st "+closingBal);
					
					portfolioValue = currNavRate * closingBal;
				//	System.out.println("qqqqq portfolioValue= currNavRate * closingBal 1st "+portfolioValue);
			
					
					mutualFundSipLookup.setOpeningBal(openingBal);
					mutualFundSipLookup.setRefDate(sipStartDate);
					mutualFundSipLookup.setReferenceMonth(strRefMonth);
					mutualFundSipLookup.setFinancialYear(fiscalYear);
					
					if (currAmountInvested != 0) {
						mutualFundSipLookup.setXirrCalc(-currAmountInvested);
					} else {
						mutualFundSipLookup.setXirrCalc(portfolioValue);
					}
					Transaction transaction = new Transaction(mutualFundSipLookup.getXirrCalc(),
							xirrDateFormat.format(mutualFundSipLookup.getRefDate()));
					xirrTransactionList.add(transaction);
					
				
					mutualFundSipLookup.setUnitsPurchased(currentUnitPurchased);
					mutualFundSipLookup.setClosingBal(closingBal);
					mutualFundSipLookup.setNav(currNavRate);
					mutualFundSipLookup.setPortfolioValue(portfolioValue);
					mutualFundSipLookup.setMonthCounter(monthCounter);
					mutualFundLumpsumSipList.add(mutualFundSipLookup);
					currAmountInvested = 0;
				
					//System.out.println("================================");
				} else {

					generalCal.add(Calendar.MONTH, 1);
					
				//	System.out.println("generalCal.getTime() "+generalCal.getTime());
				//	System.out.println("portfolioValueDate.getTime()  "+portfolioValueDate.getTime() );
					System.out.println("generalCal.getTime().compareTo(portfolioValueDate.getTime() "+generalCal.getTime().compareTo(portfolioValueDate.getTime()));
					
					if (generalCal.getTime().compareTo(portfolioValueDate.getTime()) >= 0) {

						currNavRate = 0.0;
						
						//currNavRate = returnNAVRate(generalCal.getTime(), mastermfnavList, isin);
						currNavRate = returnNAVRate(portfolioValueDate.getTime(), mastermfnavList, isin);

						openingBal = closingBal;
						currAmountInvested = 0;
						mutualFundSipLookup.setOpeningBal(openingBal);
						strRefMonth = strInterimDate.substring(3);
					
						portfolioValue = currNavRate * closingBal;
						currentUnitPurchased = currAmountInvested / currNavRate;
						currentPortfoliovalue = currentUnitPurchasedTotal*currNavRate;
						
						mutualFundSipLookup.setAmountInvested(currAmountInvested);
						mutualFundSipLookup.setUnitsPurchased(0);
						mutualFundSipLookup.setScheme("");
					//	System.out.println("inside generalCal.getTime().compareTo(portfolioValueDate.getTime() "+generalCal.getTime().compareTo(portfolioValueDate.getTime()));
						
						/*if (generalCal.getTime().compareTo(portfolioValueDate.getTime()) == 0) {
						  if(installmentCounter <= sipInstallment) {
							currAmountInvested=sipAmount;
							totalAmountInvested = totalAmountInvested + sipAmount;
							
							currentUnitPurchased = currAmountInvested / currNavRate;
							currentUnitPurchasedTotal = currentUnitPurchasedTotal+currentUnitPurchased;
							
							closingBal = currentUnitPurchased + openingBal;
							portfolioValue = currNavRate * closingBal;
						//	System.out.println("aaaainstallmentCounter <= sipInstallment");
							}else{
								currentUnitPurchased = currAmountInvested / currNavRate;
						//		System.out.println("bbbbinstallmentCounter > sipInstallment");
								currentPortfoliovalue = currentUnitPurchasedTotal*currNavRate;
								
							}
						}
						if (generalCal.getTime().compareTo(portfolioValueDate.getTime()) > 0) {
						//	System.out.println("cccinstallmentCounter > sipInstallment");
							currentPortfoliovalue = currentUnitPurchasedTotal*currNavRate;
						 }*/
						  //  System.out.println("currentUnitPurchasedTotal "+currentUnitPurchasedTotal);
						
						  //  System.out.println("currAmountInvested 3rd "+currAmountInvested);
						//	System.out.println("currNavRate 3rd "+currNavRate);
						//	System.out.println("currentUnitPurchased(currAmountInvested / currNavRate 3rd) "+currentUnitPurchased);
						//	System.out.println("bbbbinstallmentCounter > sipInstallment");
						
						//	System.out.println("openingBal 3rd "+openingBal);
						//	System.out.println("(closingBal = currentUnitPurchased + openingBal) 3rd "+closingBal);
						//	System.out.println("portfolioValue = currNavRate * closingBal 3rd "+portfolioValue);
						//	System.out.println("pppp currentPortfoliovalue(currentUnitPurchasedTotal*currNavRate) 3rd "+currentPortfoliovalue);
						//	System.out.println("fiscalYear "+fiscalYear);
							
							
						//closingBal = currentUnitPurchased + openingBal;
						mutualFundSipLookup.setClosingBal(closingBal);
						mutualFundSipLookup.setNav(currNavRate);
						mutualFundSipLookup.setPortfolioValue(portfolioValue);
						mutualFundSipLookup.setCurrentportfoliovalue(currentPortfoliovalue);
					//	System.out.println("mutualFundSipLookup.setCurrentportfoliovalue "+mutualFundSipLookup.getCurrentportfoliovalue()); 
					//	System.out.println("portfolioValue = currNavRate * closingBal; "+mutualFundSipLookup.getPortfolioValue()); 
						PTPReturn=(portfolioValue-totalAmountInvested)/totalAmountInvested;
						
						generalCal.set(Calendar.DAY_OF_MONTH, portfolioValueDate.get(Calendar.DAY_OF_MONTH));
						generalCal.set(Calendar.MONTH, portfolioValueDate.get(Calendar.MONTH));
						strInterimDate = sdfOutput.format(generalCal.getTime());
						
						fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
						mutualFundSipLookup.setRefDate(generalCal.getTime());
						mutualFundSipLookup.setReferenceMonth(strRefMonth);
						mutualFundSipLookup.setFinancialYear(fiscalYear);
						
						//xirr start
						//System.out.println("currAmountInvested "+currAmountInvested);
						
						if (currAmountInvested != 0) {
							mutualFundSipLookup.setXirrCalc(-currAmountInvested);
						} else {
							mutualFundSipLookup.setXirrCalc(portfolioValue);
						}
						
					
						Transaction transaction = new Transaction(Math.abs(mutualFundSipLookup.getXirrCalc()),
								xirrDateFormat.format(mutualFundSipLookup.getRefDate()));
					//	System.out.println("transaction "+transaction);
						xirrTransactionList.add(transaction);
					//	System.out.println("xirrTransactionList.size() "+xirrTransactionList.size());
						double Xirrrate = new com.finlabs.finexa.resources.util.Xirr(xirrTransactionList).xirr();
					//	System.out.println("Xirrrate "+Xirrrate);
						mutualFundSipLookup.setXirr(Xirrrate);
						mutualFundSip.setXirr(mutualFundSipLookup.getXirr());
					//	System.out.println("xirr "+mutualFundSip.getXirr());
						//xirrList.add(mutualFundSip.getXirr());
						
						//xirr end
						mutualFundSipLookup.setTotalInvestmentValue(totalAmountInvested);
						mutualFundSipLookup.setGainLoss(portfolioValue-totalAmountInvested);
						mutualFundSipLookup.setPTPReturn(PTPReturn);
						mutualFundSipLookup.setMonthCounter(0);
	
					//	System.out.println("in pod mutualFundSipLookup.getXirrCalc() "+mutualFundSipLookup.getXirrCalc());
					//	System.out.println("in pod mutualFundSipLookup.getRefDate() "+mutualFundSipLookup.getRefDate());
					//	System.out.println("in pod mutualFundSipLookup.getRefDate() "+xirrDateFormat.format(mutualFundSipLookup.getRefDate()));
						
						mutualFundLumpsumSipList.add(mutualFundSipLookup);
						//System.out.println("===============break");
						break;
					} else {
                     //  System.out.println("flag "+flag);
                        if(flag == 0) {
						strInterimDate = sdfOutput.format(generalCal.getTime());
						SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(formatter.parse(strInterimDate));
						strRefDate = formatter.format(calendar.getTime());
						mutualFundSipLookup.setRefDate(sdfOutput.parse(strRefDate));
						currNavRate = returnNAVRate(mutualFundSipLookup.getRefDate(), mastermfnavList, isin);
						strRefMonth = strRefDate.substring(3);
						openingBal = closingBal;
						fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
						
					//	System.out.println("monthCounter "+monthCounter );
					//	System.out.println("monthCounter % (12 / sipFrequency) "+monthCounter % (12 / sipFrequency));

						/*if (monthCounter > 0 && monthCounter % (12 / sipFrequency) == 0) {
							currAmountInvested = sipAmount;
							totalAmountInvested = totalAmountInvested + sipAmount;
							System.out.println("totalAmountInvested "+totalAmountInvested);
						}*/

						if(installmentCounter <= sipInstallment) {
							currAmountInvested = sipAmount;
							totalAmountInvested = totalAmountInvested + sipAmount;
							
							currentUnitPurchased = currAmountInvested / currNavRate;
							currentUnitPurchasedTotal = currentUnitPurchasedTotal+currentUnitPurchased;
							closingBal = currentUnitPurchased + openingBal;
						//	System.out.println("installmentCounter <= sipInstallment");
							}/*else{
							currentPortfoliovalue = currentUnitPurchasedTotal*currNavRate;
							System.out.println("installmentCounter > sipInstallment");
						   }*/
						if(installmentCounter == sipInstallment) {
							flag=1;
						}
					//	System.out.println(" currentUnitPurchased(currAmountInvested / currNavRate 2nd) "+currentUnitPurchased);
					//	System.out.println("currentUnitPurchasedTotal 2nd"+currentUnitPurchasedTotal);
						
					//	System.out.println("openingBal 2nd "+openingBal);
						//System.out.println("closingBal = currentUnitPurchased + openingBal 2nd "+closingBal);
						//closingBal = currentUnitPurchased + openingBal;
						//System.out.println("currNavRate 2nd "+currNavRate);
						portfolioValue = currNavRate * closingBal;
					//	System.out.println("portfolioValue = currNavRate * closingBal 2nd "+portfolioValue);
					//	System.out.println("pppp currentPortfoliovalue(currentUnitPurchasedTotal*currNavRate) 2nd "+currentPortfoliovalue);
						mutualFundSipLookup.setReferenceMonth(strRefMonth);
						mutualFundSipLookup.setFinancialYear(fiscalYear);
						mutualFundSipLookup.setAmountInvested(currAmountInvested);
						mutualFundSipLookup.setOpeningBal(openingBal);
						//System.out.println("currAmountInvested "+currAmountInvested);
						if (currAmountInvested != 0.0) {
							mutualFundSipLookup.setXirrCalc(-currAmountInvested);
						} else {
							mutualFundSipLookup.setXirrCalc(0);
						}
						
						Transaction transaction = new Transaction(mutualFundSipLookup.getXirrCalc(),
								xirrDateFormat.format(mutualFundSipLookup.getRefDate()));
						//System.out.println("transaction "+transaction);
						xirrTransactionList.add(transaction);
						//System.out.println("xirrTransactionList "+xirrTransactionList.size());
						
						//new ===================
					/*	double Xirrrate = new com.finlabs.finexa.resources.util.Xirr(xirrTransactionList).xirr();
						System.out.println("Xirrrate "+Xirrrate);
						mutualFundSipLookup.setXirr(Xirrrate);
						mutualFundSip.setXirr(mutualFundSipLookup.getXirr());
						
						System.out.println("xirr "+mutualFundSip.getXirr());
						xirrList.add(mutualFundSip.getXirr());*/
						//================
						mutualFundSipLookup.setUnitsPurchased(currentUnitPurchased);
						mutualFundSipLookup.setClosingBal(closingBal);
						mutualFundSipLookup.setNav(currNavRate);
						mutualFundSipLookup.setPortfolioValue(portfolioValue);
						mutualFundSipLookup.setMonthCounter(monthCounter);
						mutualFundLumpsumSipList.add(mutualFundSipLookup);
						currAmountInvested = 0;
						//System.out.println("===============");
                        }
					}
					
				}

				monthCounter++;
				serialCounter++;
				installmentCounter++;

			}
		
			
			//================Result==========================
			/*System.out.println("after discussion "+mutualFundSipLookup.getCurrentportfoliovalue());
			System.out.println("current value "+mutualFundSipLookup.getPortfolioValue());
			System.out.println("investmentValue value "+mutualFundSipLookup.getTotalInvestmentValue());
			System.out.println("gainLoss "+mutualFundSipLookup.getGainLoss());
			System.out.println("PTP "+mutualFundSipLookup.getPTPReturn());
			System.out.println("XIRR "+mutualFundSipLookup.getXirr());
			*/
			//================END POD==========================
		} catch (Exception exp) {
			exp.printStackTrace();
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_MUTUAL_FUND,
					FinexaConstant.PRODUCT_CAL_MUTUAL_FUND_SIP, FinexaConstant.PRODUCT_CAL_MUTUAL_FUND_SIP_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}

		mutualFundSip.setMutualFundLumpsumSipList(mutualFundLumpsumSipList);
		return mutualFundSip;
	}


	//By ViswajeetDa
	/*public static double returnNAVRate(Date givenDate, List<MasterMutualFundLumpsumSip> mastermfnavList, String isin) {
		double currNavRate = 0d;
		for (MasterMutualFundLumpsumSip masterMutualFundLumpsumSip : mastermfnavList) {
			if ((givenDate.equals(masterMutualFundLumpsumSip.getDate())
					&& masterMutualFundLumpsumSip.getIsin().equals(isin))) {
				currNavRate = masterMutualFundLumpsumSip.getNav();
				break;
			}

		}
		// if current date nav not present than take last available date nav
		if (currNavRate == 0d) {
			currNavRate = mastermfnavList.get(mastermfnavList.size() - 1).getNav();
		}

		return currNavRate;

	}*/


	//By Anindya
	public static double returnNAVRate(Date givenDate, List<MasterMutualFundLumpsumSip> mastermfnavList, String isin) {
		double currNavRate = 0d;
		int breakFlag = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(givenDate);
		for (int maxTry = 0; maxTry<= 7; maxTry ++) {
			for (MasterMutualFundLumpsumSip masterMutualFundLumpsumSip : mastermfnavList) {
				if ((c.getTime().equals(masterMutualFundLumpsumSip.getDate())
						&& masterMutualFundLumpsumSip.getIsin().equals(isin))) {
					
					currNavRate = masterMutualFundLumpsumSip.getNav();
					breakFlag = 1;
					
					//System.out.println("currNavRateflag breakFlag(1)    "+currNavRate);
					break;
				}
			}
			if (breakFlag == 0) {
				c.add(Calendar.DATE, 1);	
				//System.out.println("c.getTime() breakflag(0)    "+c.getTime());
			} else {
				break;
			}
		}
		if (currNavRate == 0d) {
			currNavRate = mastermfnavList.get(mastermfnavList.size() - 1).getNav();
			//System.out.println("currNavRateflag od   "+currNavRate);
		}
		return currNavRate;

	}

//For Portfolio model
public MutualFundLumpsumSipLookup getMutualFundSIPCalculationPortFolio(double sipAmount, String isin, Date sipStartDate,
		int sipInstallment, int sipFrequency) {
	
	
	double currentUnitPurchasedTotal = 0;
	double currentPortfoliovalue = 0;
	
	//List<MutualFundLumpsumSipLookup> mutualFundLumpsumSipList = new ArrayList<MutualFundLumpsumSipLookup>();
	MutualFundLumpsumSipLookup mutualFundSipLookup = new MutualFundLumpsumSipLookup();
	MutualFundLumpsumSip mutualFundSip = new MutualFundLumpsumSip();
	try {
		Calendar generalCal = Calendar.getInstance();

		generalCal.setTime(sipStartDate);
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(generalCal.getTime());
		String strRefMonth = "";
		String strRefDate = "";
		String fiscalYear = "";
		int monthCounter = 1;
		int serialCounter = 0;
		double openingBal = 0.0;
		double currAmountInvested = 0.0;
		double currentUnitPurchased = 0.0;
		double closingBal = 0.0;
		double currNavRate = 0.0;
		double portfolioValue = 0.0;
		double totalAmountInvested = 0.0;
		double PTPReturn;
		Calendar portfolioValueDate = Calendar.getInstance();
		endDate.add(Calendar.MONTH, (sipInstallment * (12 / sipFrequency) - 1));

		Date sipEndDate = endDate.getTime();
		mutualFundSip.setSipEndDate(sipEndDate);

		generalCal.set(Calendar.MILLISECOND, 0);
		generalCal.set(Calendar.SECOND, 0);
		generalCal.set(Calendar.MINUTE, 0);
		generalCal.set(Calendar.HOUR_OF_DAY, 0);
	
		portfolioValueDate.set(Calendar.MILLISECOND, 0);
		portfolioValueDate.set(Calendar.SECOND, 0);
		portfolioValueDate.set(Calendar.MINUTE, 0);
		portfolioValueDate.set(Calendar.HOUR_OF_DAY, 0);
		
		List<MasterMutualFundLumpsumSip> mastermfnavList = mastermfDao
				.getMasterMutualFundLumpsumSipNavs(sipStartDate, portfolioValueDate.getTime(), isin);
		//System.out.println("mastermfnavList.size"+mastermfnavList);
		//System.out.println("generalCal.getTime() "+generalCal.getTime());
	
		int installmentCounter=1;
		int flag=0;
	
		
		
		while (generalCal.getTime().compareTo(portfolioValueDate.getTime()) == -1) {
			//mutualFundSipLookup = new MutualFundLumpsumSipLookup();
			mutualFundSipLookup.setScheme("Mutual Fund");
			if (serialCounter == 0) {
				strInterimDate = sdfOutput.format(generalCal.getTime());

				mutualFundSipLookup.setRefDate(sdfOutput.parse(sdfOutput.format(sipStartDate)));
				
				if(!mastermfnavList.isEmpty()) {
				currNavRate =  returnNAVRate(mutualFundSipLookup.getRefDate(), mastermfnavList, isin);
				}
				strRefMonth = strInterimDate.substring(3);
				fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
				
			
				currAmountInvested = 0;
			
				
				if(installmentCounter <= sipInstallment) {
				currAmountInvested = sipAmount;
				if(currNavRate != 0){
				currentUnitPurchased = currAmountInvested / currNavRate;
				}
				currentUnitPurchasedTotal = currentUnitPurchasedTotal+currentUnitPurchased;
				closingBal = currentUnitPurchased + openingBal;
				
				totalAmountInvested = currAmountInvested;//no according to excel
				//System.out.println("totalAmountInvested "+totalAmountInvested);
				mutualFundSipLookup.setAmountInvested(currAmountInvested);
				}else{
				currentPortfoliovalue = currentUnitPurchasedTotal*currNavRate;
			   }
		
				//System.out.println("currNavRate 1st "+currNavRate);
			
				portfolioValue = currNavRate * closingBal;
		
				
				mutualFundSipLookup.setOpeningBal(openingBal);
				mutualFundSipLookup.setRefDate(sipStartDate);
				mutualFundSipLookup.setReferenceMonth(strRefMonth);
				mutualFundSipLookup.setFinancialYear(fiscalYear);
				
				if (currAmountInvested != 0) {
					mutualFundSipLookup.setXirrCalc(-currAmountInvested);
				} else {
					mutualFundSipLookup.setXirrCalc(portfolioValue);
				}
				Transaction transaction = new Transaction(mutualFundSipLookup.getXirrCalc(),
						xirrDateFormat.format(mutualFundSipLookup.getRefDate()));
				xirrTransactionList.add(transaction);
				
				
				mutualFundSipLookup.setUnitsPurchased(currentUnitPurchased);
				mutualFundSipLookup.setClosingBal(closingBal);
				mutualFundSipLookup.setNav(currNavRate);
				mutualFundSipLookup.setPortfolioValue(portfolioValue);
				mutualFundSipLookup.setMonthCounter(monthCounter);
				//mutualFundLumpsumSipList.add(mutualFundSipLookup);
				currAmountInvested = 0;
			
				//System.out.println("================================");
			} else {
				
				//System.out.println("inside monthCounter "+monthCounter);
				generalCal.add(Calendar.MONTH, 1);
				generalCal.set(Calendar.MILLISECOND, 0);
				generalCal.set(Calendar.SECOND, 0);
				generalCal.set(Calendar.MINUTE, 0);
				generalCal.set(Calendar.HOUR_OF_DAY, 0);
		
				
				//System.out.println("generalCal.getTime() "+generalCal.getTime());
				//System.out.println("generalCal.getTime().compareTo(portfolioValueDate.getTime() "+generalCal.getTime().compareTo(portfolioValueDate.getTime()));
				//System.out.println("portfolioValueDate.getTime() "+portfolioValueDate.getTime());
				if (generalCal.getTime().compareTo(portfolioValueDate.getTime()) >= 0) {
                    //System.out.println("totalAmountInvested "+totalAmountInvested);
					mutualFundSipLookup.setTotalInvestmentValue(totalAmountInvested);
					currNavRate = 0.0;
					
					//currNavRate = returnNAVRate(generalCal.getTime(), mastermfnavList, isin);
					if(!mastermfnavList.isEmpty()) {
					currNavRate = returnNAVRate(portfolioValueDate.getTime(), mastermfnavList, isin);
					}
					//System.out.println("currNavRate      "+currNavRate);
					openingBal = closingBal;
					
					currAmountInvested = 0;
					mutualFundSipLookup.setOpeningBal(openingBal);
					strRefMonth = strInterimDate.substring(3);
				
					//System.out.println("closingBal "+closingBal);					
					portfolioValue = currNavRate * closingBal;

					
					mutualFundSipLookup.setAmountInvested(currAmountInvested);
					mutualFundSipLookup.setUnitsPurchased(0);
					mutualFundSipLookup.setScheme("");
				
					/*if (generalCal.getTime().compareTo(portfolioValueDate.getTime()) == 0) {
					  if(installmentCounter <= sipInstallment) {
						currAmountInvested=sipAmount;
						totalAmountInvested = totalAmountInvested + sipAmount;
						
						currentUnitPurchased = currAmountInvested / currNavRate;
						currentUnitPurchasedTotal = currentUnitPurchasedTotal+currentUnitPurchased;
						
						closingBal = currentUnitPurchased + openingBal;
						portfolioValue = currNavRate * closingBal;
					//	System.out.println("aaaainstallmentCounter <= sipInstallment");
						}else{
							currentUnitPurchased = currAmountInvested / currNavRate;
					//		System.out.println("bbbbinstallmentCounter > sipInstallment");
							currentPortfoliovalue = currentUnitPurchasedTotal*currNavRate;
							
						}
					}*/
					
					    //System.out.println("currentUnitPurchasedTotal "+currentUnitPurchasedTotal);
						//System.out.println("currNavRate 3rd "+currNavRate);
						/*if(currNavRate != 0){
					    currentUnitPurchased = currAmountInvested / currNavRate;
						}*/
						currentPortfoliovalue = currentUnitPurchasedTotal*currNavRate;
				
					//closingBal = currentUnitPurchased + openingBal;
					mutualFundSipLookup.setClosingBal(closingBal);
					mutualFundSipLookup.setNav(currNavRate);
					mutualFundSipLookup.setPortfolioValue(portfolioValue);
					mutualFundSipLookup.setCurrentportfoliovalue(currentPortfoliovalue);
					PTPReturn=(portfolioValue-totalAmountInvested)/totalAmountInvested;
					generalCal.set(Calendar.DAY_OF_MONTH, portfolioValueDate.get(Calendar.DAY_OF_MONTH));
					generalCal.set(Calendar.MONTH, portfolioValueDate.get(Calendar.MONTH));
					strInterimDate = sdfOutput.format(generalCal.getTime());
					
					fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
					mutualFundSipLookup.setRefDate(generalCal.getTime());
					mutualFundSipLookup.setReferenceMonth(strRefMonth);
					mutualFundSipLookup.setFinancialYear(fiscalYear);
					
					//xirr start
					//System.out.println("currAmountInvested "+currAmountInvested);
					
					if (currAmountInvested != 0) {
						mutualFundSipLookup.setXirrCalc(-currAmountInvested);
					} else {
						mutualFundSipLookup.setXirrCalc(portfolioValue);
					}
					
				
					Transaction transaction = new Transaction(Math.abs(mutualFundSipLookup.getXirrCalc()),
							xirrDateFormat.format(mutualFundSipLookup.getRefDate()));
			
					xirrTransactionList.add(transaction);
					
				
					double Xirrrate = new com.finlabs.finexa.resources.util.Xirr(xirrTransactionList).xirr();
				
					mutualFundSipLookup.setXirr(Xirrrate);
					mutualFundSip.setXirr(mutualFundSipLookup.getXirr());
				
					//xirr end
				
					mutualFundSipLookup.setGainLoss(portfolioValue-totalAmountInvested);
					mutualFundSipLookup.setPTPReturn(PTPReturn);
					mutualFundSipLookup.setMonthCounter(0);
					//System.out.println("===============break");
					break;
				} else {
                   
                    if(flag == 0) {
					strInterimDate = sdfOutput.format(generalCal.getTime());
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(formatter.parse(strInterimDate));
					strRefDate = formatter.format(calendar.getTime());
					mutualFundSipLookup.setRefDate(sdfOutput.parse(strRefDate));
					if(!mastermfnavList.isEmpty()) {
					currNavRate = returnNAVRate(mutualFundSipLookup.getRefDate(), mastermfnavList, isin);
					}
					strRefMonth = strRefDate.substring(3);
					openingBal = closingBal;
					fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
					
				
					/*if (monthCounter > 0 && monthCounter % (12 / sipFrequency) == 0) {
						currAmountInvested = sipAmount;
						totalAmountInvested = totalAmountInvested + sipAmount;
						System.out.println("totalAmountInvested "+totalAmountInvested);
					}*/
				
			
					if(installmentCounter <= sipInstallment) {
						currAmountInvested = sipAmount;	
						totalAmountInvested = totalAmountInvested + sipAmount;
						if(currNavRate != 0){
						currentUnitPurchased = currAmountInvested / currNavRate;
						}
						currentUnitPurchasedTotal = currentUnitPurchasedTotal+currentUnitPurchased;
						closingBal = currentUnitPurchased + openingBal;
					
						
						}
				
					if(installmentCounter == sipInstallment) {
						flag=1;
					}
		
					portfolioValue = currNavRate * closingBal;
				
					mutualFundSipLookup.setReferenceMonth(strRefMonth);
					mutualFundSipLookup.setFinancialYear(fiscalYear);
					mutualFundSipLookup.setAmountInvested(currAmountInvested);
					mutualFundSipLookup.setOpeningBal(openingBal);
				
					if (currAmountInvested != 0.0) {
						mutualFundSipLookup.setXirrCalc(-currAmountInvested);
					} else {
						mutualFundSipLookup.setXirrCalc(0);
					}
					
					Transaction transaction = new Transaction(mutualFundSipLookup.getXirrCalc(),
							xirrDateFormat.format(mutualFundSipLookup.getRefDate()));
					
					xirrTransactionList.add(transaction);
				
					//new ===================
				/*	double Xirrrate = new com.finlabs.finexa.resources.util.Xirr(xirrTransactionList).xirr();
					System.out.println("Xirrrate "+Xirrrate);
					mutualFundSipLookup.setXirr(Xirrrate);
					mutualFundSip.setXirr(mutualFundSipLookup.getXirr());
					
					System.out.println("xirr "+mutualFundSip.getXirr());
					xirrList.add(mutualFundSip.getXirr());*/
					//================
					mutualFundSipLookup.setUnitsPurchased(currentUnitPurchased);
					mutualFundSipLookup.setClosingBal(closingBal);
					mutualFundSipLookup.setNav(currNavRate);
					mutualFundSipLookup.setPortfolioValue(portfolioValue);
					mutualFundSipLookup.setMonthCounter(monthCounter);
					//mutualFundLumpsumSipList.add(mutualFundSipLookup);
					currAmountInvested = 0;
					//System.out.println("===============");
                    }
				}
				
			}

			monthCounter++;
			serialCounter++;
			installmentCounter++;
		}
		//================Result==========================
		if(mutualFundSipLookup!=null) {
				/*System.out.println("after discussion "+mutualFundSipLookup.getCurrentportfoliovalue());
				System.out.println("current value "+mutualFundSipLookup.getPortfolioValue());
				System.out.println("investmentValue value "+mutualFundSipLookup.getTotalInvestmentValue());
				System.out.println("gainLoss "+mutualFundSipLookup.getGainLoss());
				System.out.println("PTP "+mutualFundSipLookup.getPTPReturn());
				System.out.println("XIRR "+mutualFundSipLookup.getXirr());*/
		}
		
		mutualFundSip.setTotalInvestedAmount(totalAmountInvested);
		mutualFundSip.setPortfolioValue(portfolioValue);
		
		
		
		
		//================END POD==========================
	} catch (Exception exp) {
		exp.printStackTrace();
		FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_MUTUAL_FUND,
				FinexaConstant.PRODUCT_CAL_MUTUAL_FUND_SIP, FinexaConstant.PRODUCT_CAL_MUTUAL_FUND_SIP_DESC, exp);
		FinexaBussinessException.logFinexaBusinessException(finexaBuss);
	}

	//mutualFundSip.setMutualFundLumpsumSipList(mutualFundLumpsumSipList);
	return mutualFundSipLookup;
}

public MutualFundLumpsumSipLookup getMutualFundLumpsumCalculationPortfolio(double amountInvested, String isin, Date investedDate,
		int unitPurchased) {
	
	List<MutualFundLumpsumSipLookup> mutualFundLumpsumSipList = new ArrayList<MutualFundLumpsumSipLookup>();

	MutualFundLumpsumSipLookup mutualFundLumpSumLookup = null;
	MutualFundLumpsumSip mutualFundLumpSum = new MutualFundLumpsumSip();
	Calendar generalCal = Calendar.getInstance();
	double ouputcurrentUnitPurchased = 0.0;
	try {

		generalCal.setTime(investedDate);
		//System.out.println("generalCal "+generalCal.getTime());
		String strRefDate = "";
		String strRefMonth = "";
		String fiscalYear = "";
		int monthCounter = -1;
		double openingBal = 0.0;
		double currAmountInvested = 0.0;
		double currentUnitPurchased = 0.0;
		double closingBal = 0.0;
		double lastPortfolioValue = 0.0;
		double currNavRate = 0.0;
		double portfolioValue = 0.0;
		double currPtpReturns = 0.0;
		double annualisedCagr = 0.0;
		double investmentTenure = 0.0;
		double currentUnitPurchasedTotal = 0.0;
		double currentValue = 0.0;
		Calendar portfolioValueDate = Calendar.getInstance();
		
		portfolioValueDate.set(Calendar.MILLISECOND, 0);
		portfolioValueDate.set(Calendar.SECOND, 0);
		portfolioValueDate.set(Calendar.MINUTE, 0);
		portfolioValueDate.set(Calendar.HOUR_OF_DAY, 0);
		
		generalCal.set(Calendar.MILLISECOND, 0);
		generalCal.set(Calendar.SECOND, 0);
		generalCal.set(Calendar.MINUTE, 0);
		generalCal.set(Calendar.HOUR_OF_DAY, 0);
		
		//System.out.println("portfolioValueDate "+portfolioValueDate.getTime());
		
		List<MasterMutualFundLumpsumSip> mastermfnavList = mastermfDao
				.getMasterMutualFundLumpsumSipNavs(investedDate, portfolioValueDate.getTime(), isin);
		//System.out.println("mastermfnavList.size() "+mastermfnavList.size());
		//System.out.println("generalCal.getTime().compareTo(portfolioValueDate.getTime() "+generalCal.getTime().compareTo(portfolioValueDate.getTime()));
	
		while (generalCal.getTime().compareTo(portfolioValueDate.getTime()) == -1) {
			mutualFundLumpSumLookup = new MutualFundLumpsumSipLookup();
			if (monthCounter == -1) {
				strInterimDate = sdfOutput.format(generalCal.getTime());
				mutualFundLumpSumLookup.setRefDate(sdfOutput.parse(sdfOutput.format(investedDate)));
				strRefMonth = strInterimDate.substring(3);
				fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
				currAmountInvested = amountInvested;

				if(!mastermfnavList.isEmpty()){
				currNavRate = returnNAVRate(mutualFundLumpSumLookup.getRefDate(), mastermfnavList, isin);
				}
				if(currNavRate != 0){
				currentUnitPurchased = currAmountInvested / currNavRate;
				}
				if (currentUnitPurchased > 0) {
					ouputcurrentUnitPurchased = currentUnitPurchased;
					currentUnitPurchasedTotal=currentUnitPurchased;
				}
				closingBal = currentUnitPurchased + openingBal;
				portfolioValue = currNavRate * closingBal;
				lastPortfolioValue = portfolioValue;
				
				if (currAmountInvested != 0) {
					mutualFundLumpSumLookup.setXirrCalc(-currAmountInvested);
				} else {
					mutualFundLumpSumLookup.setXirrCalc(0);
				}
				Transaction transaction = new Transaction(mutualFundLumpSumLookup.getXirrCalc(),
						xirrDateFormat.format(mutualFundLumpSumLookup.getRefDate()));
				xirrTransactionList.add(transaction);
				
				mutualFundLumpSumLookup.setScheme("Mutual Fund");
				mutualFundLumpSumLookup.setNav(currNavRate);
				mutualFundLumpSumLookup.setReferenceMonth(strRefMonth);
				mutualFundLumpSumLookup.setFinancialYear(fiscalYear);
				mutualFundLumpSumLookup.setAmountInvested(currAmountInvested);
				mutualFundLumpSumLookup.setUnitsPurchased(currentUnitPurchased);
				mutualFundLumpSumLookup.setClosingBal(closingBal);
				mutualFundLumpSumLookup.setPortfolioValue(portfolioValue);
				//mutualFundLumpsumSipList.add(mutualFundLumpSumLookup);
				
				
				monthCounter++;
				//System.out.println("=================================");
			} else {
				if (monthCounter > 0) {
					generalCal.add(Calendar.MONTH, 1);
					generalCal.set(Calendar.MILLISECOND, 0);
					generalCal.set(Calendar.SECOND, 0);
					generalCal.set(Calendar.MINUTE, 0);
					generalCal.set(Calendar.HOUR_OF_DAY, 0);
				}
				//System.out.println("generalCal "+generalCal.getTime());
				
				strInterimDate = sdfOutput.format(generalCal.getTime());
			
				
				strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);
				//System.out.println("strRefDate "+strRefDate);
				
				mutualFundLumpSumLookup.setRefDate(sdfOutput.parse(strRefDate));
				
			    //new
				Calendar lastDate = Calendar.getInstance();
				lastDate.setTime(sdfOutput.parse(strRefDate));
				lastDate.set(Calendar.MILLISECOND, 0);
				lastDate.set(Calendar.SECOND, 0);
				lastDate.set(Calendar.MINUTE, 0);
				lastDate.set(Calendar.HOUR_OF_DAY, 0);
				
				//System.out.println("LastDate "+lastDate.getTime());
				
				
			
				
				if (lastDate.getTime().compareTo(portfolioValueDate.getTime()) >= 0) {
					
					mutualFundLumpSumLookup.setRefDate(portfolioValueDate.getTime());
					if(!mastermfnavList.isEmpty()){
					currNavRate = returnNAVRate(mutualFundLumpSumLookup.getRefDate(), mastermfnavList, isin);
					}
					mutualFundLumpSumLookup.setScheme("Mutual Fund");
					currAmountInvested = 0.0;
					if(currNavRate != 0){
					currentUnitPurchased = currAmountInvested / currNavRate;
					}
					if (currentUnitPurchased > 0) {
						ouputcurrentUnitPurchased = currentUnitPurchased;
					}
					
					openingBal = closingBal;
				
					closingBal = currentUnitPurchased + openingBal;
					
					portfolioValue = currNavRate * closingBal;
					
					
					//testing
					currentValue = currNavRate*currentUnitPurchasedTotal;
					
					//======
					
					Calendar cstartDate = Calendar.getInstance();
					cstartDate.setTime(investedDate);
				    //new
					investmentTenure = FinanceUtil.YEARFRAC(cstartDate.getTime(), portfolioValueDate.getTime(), 1);
					//System.out.println("investmentTenure "+investmentTenure);
					
					annualisedCagr = (Math.pow(portfolioValue / lastPortfolioValue, (1 / investmentTenure)) - 1);
					currPtpReturns = ((portfolioValue - lastPortfolioValue) / (double) lastPortfolioValue);
					
				
					mutualFundLumpSumLookup.setXirrCalc(currentValue);
					
					
				
					Transaction transaction = new Transaction(Math.abs(mutualFundLumpSumLookup.getXirrCalc()),
							xirrDateFormat.format(mutualFundLumpSumLookup.getRefDate()));
			
					xirrTransactionList.add(transaction);
					
				
					double Xirrrate = new com.finlabs.finexa.resources.util.Xirr(xirrTransactionList).xirr();
				
					mutualFundLumpSumLookup.setXirr(Xirrrate);

					mutualFundLumpSumLookup.setReferenceMonth(strRefMonth);
					mutualFundLumpSumLookup.setFinancialYear(fiscalYear);
					mutualFundLumpSumLookup.setOpeningBal(openingBal);
					mutualFundLumpSumLookup.setClosingBal(closingBal);
					
					mutualFundLumpSumLookup.setAmountInvested(amountInvested);
					mutualFundLumpSumLookup.setNav(currNavRate);
					mutualFundLumpSumLookup.setPortfolioValue(portfolioValue);
					mutualFundLumpSum.setPortfolioValue(portfolioValue);
					
					mutualFundLumpSum.setCacgr(annualisedCagr);
					mutualFundLumpSumLookup.setPTPReturn(currPtpReturns);
					mutualFundLumpSumLookup.setAnnualisedCagrReturns(annualisedCagr);
					mutualFundLumpSumLookup.setUnitsPurchased(currentUnitPurchased);
					mutualFundLumpSumLookup.setInvestmentTenure(investmentTenure);
					mutualFundLumpSumLookup.setGainLoss(portfolioValue-amountInvested);
					mutualFundLumpSumLookup.setCurrentportfoliovalue(currentValue);
					//mutualFundLumpsumSipList.add(mutualFundLumpSumLookup);
					
					/*System.out.println("currAmountInvested 222 "+currAmountInvested);
					System.out.println("currNavRate 222  "+currNavRate);
					System.out.println("currentUnitPurchased 222  "+currentUnitPurchased);
					System.out.println("openingBal 222  "+openingBal);
					System.out.println("closingBal 222  "+closingBal);
					System.out.println("portfolioValue 222  "+mutualFundLumpSumLookup.getPortfolioValue());
					System.out.println("annualisedCagr "+mutualFundLumpSumLookup.getAnnualisedCagrReturns());
					System.out.println("currPtpReturns "+mutualFundLumpSumLookup.getPTPReturn());
					System.out.println("investamount "+mutualFundLumpSumLookup.getAmountInvested());
					System.out.println("gainLoss "+mutualFundLumpSumLookup.getGainLoss());
					System.out.println("after discussion  "+mutualFundLumpSumLookup.getCurrentportfoliovalue());
					System.out.println("xirr  "+mutualFundLumpSumLookup.getXirr));
					*/
					//System.out.println("=================================break");
					break;
				}else {
					

					strRefMonth = strRefDate.substring(3);
					
					openingBal = closingBal;
					
					fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
					
					currAmountInvested = 0.0;
					if(!mastermfnavList.isEmpty()){
					currNavRate = returnNAVRate(mutualFundLumpSumLookup.getRefDate(), mastermfnavList, isin);
					}
					if(currNavRate != 0){
					currentUnitPurchased = currAmountInvested / currNavRate;
					}
					if (currentUnitPurchased > 0) {
						ouputcurrentUnitPurchased = currentUnitPurchased;
					}
					closingBal = currentUnitPurchased + openingBal;
					
					//System.out.println("currNavRate           "+currNavRate);
					

					Calendar cstartDate = Calendar.getInstance();
					Calendar portValueDate = Calendar.getInstance();

					cstartDate.setTime(investedDate);
					portValueDate.setTime(sdfOutput.parse(strRefDate));
					
					

					investmentTenure = FinanceUtil.YEARFRAC(cstartDate.getTime(), portValueDate.getTime(), 1);
					
					portfolioValue = currNavRate * closingBal;
					
					annualisedCagr = (Math.pow(portfolioValue / lastPortfolioValue, (1 / investmentTenure)) - 1);


					
					 
					currPtpReturns = ((portfolioValue - lastPortfolioValue) / (double) lastPortfolioValue) * 100;
					
					
					
	            	//System.out.println("=================================");
				}
				
				
				 
				mutualFundLumpSumLookup.setReferenceMonth(strRefMonth);
				mutualFundLumpSumLookup.setFinancialYear(fiscalYear);
				mutualFundLumpSumLookup.setAmountInvested(currAmountInvested);
				mutualFundLumpSumLookup.setOpeningBal(openingBal);
				mutualFundLumpSumLookup.setUnitsPurchased(currentUnitPurchased);
				mutualFundLumpSumLookup.setClosingBal(closingBal);
				mutualFundLumpSumLookup.setNav(currNavRate);
				mutualFundLumpSumLookup.setPortfolioValue(portfolioValue);
				mutualFundLumpSumLookup.setPTPReturn(currPtpReturns);
				mutualFundLumpSumLookup.setAnnualisedCagrReturns(annualisedCagr);
				mutualFundLumpSumLookup.setInvestmentTenure(investmentTenure);
				mutualFundLumpSumLookup.setMonthCounter(monthCounter);
				mutualFundLumpSumLookup.setScheme("Mutual Fund");
				//mutualFundLumpsumSipList.add(mutualFundLumpSumLookup);

				monthCounter++;
			}

		}
		

	} catch (Exception exp) {
		exp.printStackTrace();
		FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_MUTUAL_FUND,
				FinexaConstant.PRODUCT_CAL_MUTUAL_FUND_LUMSUM, FinexaConstant.PRODUCT_CAL_MUTUAL_FUND_LUMSUM_DESC,
				exp);
		FinexaBussinessException.logFinexaBusinessException(finexaBuss);
	}
	mutualFundLumpSum.setMutualFundLumpsumSipList(mutualFundLumpsumSipList);
	mutualFundLumpSum.setUnitPurchased((int) ouputcurrentUnitPurchased);

	return mutualFundLumpSumLookup;
}
//For Client Info Model


public MutualFundLumpsumSipLookup getMutualFundSIPCalculationClient(double sipAmount, String isin, Date sipStartDate,
		int sipInstallment, int sipFrequency) {
	
	
	double currentUnitPurchasedTotal = 0;
	double currentPortfoliovalue = 0;
	
	//List<MutualFundLumpsumSipLookup> mutualFundLumpsumSipList = new ArrayList<MutualFundLumpsumSipLookup>();
	MutualFundLumpsumSipLookup mutualFundSipLookup = null;
	MutualFundLumpsumSip mutualFundSip = new MutualFundLumpsumSip();
	try {
		Calendar generalCal = Calendar.getInstance();

		generalCal.setTime(sipStartDate);
		Calendar endDate = Calendar.getInstance();
		endDate.setTime(generalCal.getTime());
		String strRefMonth = "";
		String strRefDate = "";
		String fiscalYear = "";
		int monthCounter = 1;
		int serialCounter = 0;
		double openingBal = 0.0;
		double currAmountInvested = 0.0;
		double currentUnitPurchased = 0.0;
		double closingBal = 0.0;
		double currNavRate = 0.0;
		double portfolioValue = 0.0;
		double totalAmountInvested = 0.0;
		double PTPReturn;
		Calendar portfolioValueDate = Calendar.getInstance();
		endDate.add(Calendar.MONTH, (sipInstallment * (12 / sipFrequency) - 1));

		Date sipEndDate = endDate.getTime();
		mutualFundSip.setSipEndDate(sipEndDate);

		generalCal.set(Calendar.MILLISECOND, 0);
		generalCal.set(Calendar.SECOND, 0);
		generalCal.set(Calendar.MINUTE, 0);
		generalCal.set(Calendar.HOUR_OF_DAY, 0);
	
		portfolioValueDate.set(Calendar.MILLISECOND, 0);
		portfolioValueDate.set(Calendar.SECOND, 0);
		portfolioValueDate.set(Calendar.MINUTE, 0);
		portfolioValueDate.set(Calendar.HOUR_OF_DAY, 0);
		
		List<MasterMutualFundLumpsumSip> mastermfnavList = mastermfDao
				.getMasterMutualFundLumpsumSipNavs(sipStartDate, portfolioValueDate.getTime(), isin);
		//System.out.println("mastermfnavList.size"+mastermfnavList);
		//System.out.println("generalCal.getTime() "+generalCal.getTime());
	
		int installmentCounter=1;
		int flag=0;
	
		
		while (generalCal.getTime().compareTo(portfolioValueDate.getTime()) == -1) {
			mutualFundSipLookup = new MutualFundLumpsumSipLookup();
			mutualFundSipLookup.setScheme("Mutual Fund");
			if (serialCounter == 0) {
				strInterimDate = sdfOutput.format(generalCal.getTime());

				mutualFundSipLookup.setRefDate(sdfOutput.parse(sdfOutput.format(sipStartDate)));
				
				if(!mastermfnavList.isEmpty()) {
				currNavRate =  returnNAVRate(mutualFundSipLookup.getRefDate(), mastermfnavList, isin);
				}
				strRefMonth = strInterimDate.substring(3);
				fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
				
			
				currAmountInvested = 0;
			
				
				if(installmentCounter <= sipInstallment) {
				currAmountInvested = sipAmount;
				if(currNavRate != 0){
				currentUnitPurchased = currAmountInvested / currNavRate;
				}
				currentUnitPurchasedTotal = currentUnitPurchasedTotal+currentUnitPurchased;
				closingBal = currentUnitPurchased + openingBal;
				
				totalAmountInvested = currAmountInvested;//no according to excel
				//System.out.println("totalAmountInvested "+totalAmountInvested);
				mutualFundSipLookup.setAmountInvested(currAmountInvested);
				}else{
				currentPortfoliovalue = currentUnitPurchasedTotal*currNavRate;
			   }
		
				//System.out.println("currNavRate 1st "+currNavRate);
			
				portfolioValue = currNavRate * closingBal;
		
				
				mutualFundSipLookup.setOpeningBal(openingBal);
				mutualFundSipLookup.setRefDate(sipStartDate);
				mutualFundSipLookup.setReferenceMonth(strRefMonth);
				mutualFundSipLookup.setFinancialYear(fiscalYear);
				
				if (currAmountInvested != 0) {
					mutualFundSipLookup.setXirrCalc(-currAmountInvested);
				} else {
					mutualFundSipLookup.setXirrCalc(portfolioValue);
				}
				Transaction transaction = new Transaction(mutualFundSipLookup.getXirrCalc(),
						xirrDateFormat.format(mutualFundSipLookup.getRefDate()));
				xirrTransactionList.add(transaction);
				
				
				mutualFundSipLookup.setUnitsPurchased(currentUnitPurchased);
				mutualFundSipLookup.setClosingBal(closingBal);
				mutualFundSipLookup.setNav(currNavRate);
				mutualFundSipLookup.setPortfolioValue(portfolioValue);
				mutualFundSipLookup.setMonthCounter(monthCounter);
				//mutualFundLumpsumSipList.add(mutualFundSipLookup);
				currAmountInvested = 0;
			
				System.out.println("================================");
			} else {
				
				//System.out.println("inside monthCounter "+monthCounter);
				generalCal.add(Calendar.MONTH, 1);
				generalCal.set(Calendar.MILLISECOND, 0);
				generalCal.set(Calendar.SECOND, 0);
				generalCal.set(Calendar.MINUTE, 0);
				generalCal.set(Calendar.HOUR_OF_DAY, 0);
				
				//System.out.println("generalCal.getTime() "+generalCal.getTime());
				//System.out.println("generalCal.getTime().compareTo(portfolioValueDate.getTime() "+generalCal.getTime().compareTo(portfolioValueDate.getTime()));
				
				if (generalCal.getTime().compareTo(portfolioValueDate.getTime()) >= 0) {
                    //System.out.println("totalAmountInvested "+totalAmountInvested);
					mutualFundSipLookup.setTotalInvestmentValue(totalAmountInvested);
					currNavRate = 0.0;
					
					//currNavRate = returnNAVRate(generalCal.getTime(), mastermfnavList, isin);
					if(!mastermfnavList.isEmpty()) {
					currNavRate = returnNAVRate(portfolioValueDate.getTime(), mastermfnavList, isin);
					}
					//System.out.println("currNavRate      "+currNavRate);
					openingBal = closingBal;
					
					currAmountInvested = 0;
					mutualFundSipLookup.setOpeningBal(openingBal);
					strRefMonth = strInterimDate.substring(3);
				
										
					portfolioValue = currNavRate * closingBal;

					
					mutualFundSipLookup.setAmountInvested(currAmountInvested);
					mutualFundSipLookup.setUnitsPurchased(0);
					mutualFundSipLookup.setScheme("");
				
					/*if (generalCal.getTime().compareTo(portfolioValueDate.getTime()) == 0) {
					  if(installmentCounter <= sipInstallment) {
						currAmountInvested=sipAmount;
						totalAmountInvested = totalAmountInvested + sipAmount;
						
						currentUnitPurchased = currAmountInvested / currNavRate;
						currentUnitPurchasedTotal = currentUnitPurchasedTotal+currentUnitPurchased;
						
						closingBal = currentUnitPurchased + openingBal;
						portfolioValue = currNavRate * closingBal;
					//	System.out.println("aaaainstallmentCounter <= sipInstallment");
						}else{
							currentUnitPurchased = currAmountInvested / currNavRate;
					//		System.out.println("bbbbinstallmentCounter > sipInstallment");
							currentPortfoliovalue = currentUnitPurchasedTotal*currNavRate;
							
						}
					}*/
					
					
						//System.out.println("currNavRate 3rd "+currNavRate);
						if(currNavRate != 0){
					    currentUnitPurchased = currAmountInvested / currNavRate;
						}
						currentPortfoliovalue = currentUnitPurchasedTotal*currNavRate;
				
					//closingBal = currentUnitPurchased + openingBal;
					mutualFundSipLookup.setClosingBal(closingBal);
					mutualFundSipLookup.setNav(currNavRate);
					mutualFundSipLookup.setPortfolioValue(portfolioValue);
					mutualFundSipLookup.setCurrentportfoliovalue(currentPortfoliovalue);
					PTPReturn=(portfolioValue-totalAmountInvested)/totalAmountInvested;
					generalCal.set(Calendar.DAY_OF_MONTH, portfolioValueDate.get(Calendar.DAY_OF_MONTH));
					generalCal.set(Calendar.MONTH, portfolioValueDate.get(Calendar.MONTH));
					strInterimDate = sdfOutput.format(generalCal.getTime());
					
					fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
					mutualFundSipLookup.setRefDate(generalCal.getTime());
					mutualFundSipLookup.setReferenceMonth(strRefMonth);
					mutualFundSipLookup.setFinancialYear(fiscalYear);
					
					//xirr start
					//System.out.println("currAmountInvested "+currAmountInvested);
					
					if (currAmountInvested != 0) {
						mutualFundSipLookup.setXirrCalc(-currAmountInvested);
					} else {
						mutualFundSipLookup.setXirrCalc(portfolioValue);
					}
					
				
					Transaction transaction = new Transaction(Math.abs(mutualFundSipLookup.getXirrCalc()),
							xirrDateFormat.format(mutualFundSipLookup.getRefDate()));
			
					xirrTransactionList.add(transaction);
					
				
					double Xirrrate = new com.finlabs.finexa.resources.util.Xirr(xirrTransactionList).xirr();
				
					mutualFundSipLookup.setXirr(Xirrrate);
					mutualFundSip.setXirr(mutualFundSipLookup.getXirr());
				
					//xirr end
				
					mutualFundSipLookup.setGainLoss(portfolioValue-totalAmountInvested);
					mutualFundSipLookup.setPTPReturn(PTPReturn);
					mutualFundSipLookup.setMonthCounter(0);
					//System.out.println("===============break");
					break;
				} else {
                   
                    if(flag == 0) {
					strInterimDate = sdfOutput.format(generalCal.getTime());
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(formatter.parse(strInterimDate));
					strRefDate = formatter.format(calendar.getTime());
					mutualFundSipLookup.setRefDate(sdfOutput.parse(strRefDate));
					if(!mastermfnavList.isEmpty()) {
					currNavRate = returnNAVRate(mutualFundSipLookup.getRefDate(), mastermfnavList, isin);
					}
					strRefMonth = strRefDate.substring(3);
					openingBal = closingBal;
					fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
					
				
					/*if (monthCounter > 0 && monthCounter % (12 / sipFrequency) == 0) {
						currAmountInvested = sipAmount;
						totalAmountInvested = totalAmountInvested + sipAmount;
						System.out.println("totalAmountInvested "+totalAmountInvested);
					}*/
				
			
					if(installmentCounter <= sipInstallment) {
						currAmountInvested = sipAmount;	
						totalAmountInvested = totalAmountInvested + sipAmount;
						if(currNavRate != 0){
						currentUnitPurchased = currAmountInvested / currNavRate;
						}
						currentUnitPurchasedTotal = currentUnitPurchasedTotal+currentUnitPurchased;
						closingBal = currentUnitPurchased + openingBal;
					
						
						}
				
					if(installmentCounter == sipInstallment) {
						flag=1;
					}
		
					portfolioValue = currNavRate * closingBal;
				
					mutualFundSipLookup.setReferenceMonth(strRefMonth);
					mutualFundSipLookup.setFinancialYear(fiscalYear);
					mutualFundSipLookup.setAmountInvested(currAmountInvested);
					mutualFundSipLookup.setOpeningBal(openingBal);
				
					if (currAmountInvested != 0.0) {
						mutualFundSipLookup.setXirrCalc(-currAmountInvested);
					} else {
						mutualFundSipLookup.setXirrCalc(0);
					}
					
					Transaction transaction = new Transaction(mutualFundSipLookup.getXirrCalc(),
							xirrDateFormat.format(mutualFundSipLookup.getRefDate()));
					
					xirrTransactionList.add(transaction);
				
					//new ===================
				/*	double Xirrrate = new com.finlabs.finexa.resources.util.Xirr(xirrTransactionList).xirr();
					System.out.println("Xirrrate "+Xirrrate);
					mutualFundSipLookup.setXirr(Xirrrate);
					mutualFundSip.setXirr(mutualFundSipLookup.getXirr());
					
					System.out.println("xirr "+mutualFundSip.getXirr());
					xirrList.add(mutualFundSip.getXirr());*/
					//================
					mutualFundSipLookup.setUnitsPurchased(currentUnitPurchased);
					mutualFundSipLookup.setClosingBal(closingBal);
					mutualFundSipLookup.setNav(currNavRate);
					mutualFundSipLookup.setPortfolioValue(portfolioValue);
					mutualFundSipLookup.setMonthCounter(monthCounter);
					//mutualFundLumpsumSipList.add(mutualFundSipLookup);
					currAmountInvested = 0;
					//System.out.println("===============");
                    }
				}
				
			}

			monthCounter++;
			serialCounter++;
			installmentCounter++;
		}
		//================Result==========================
		if(mutualFundSipLookup!=null) {
				/*System.out.println("after discussion "+mutualFundSipLookup.getCurrentportfoliovalue());
				System.out.println("current value "+mutualFundSipLookup.getPortfolioValue());
				System.out.println("investmentValue value "+mutualFundSipLookup.getTotalInvestmentValue());
				System.out.println("gainLoss "+mutualFundSipLookup.getGainLoss());
				System.out.println("PTP "+mutualFundSipLookup.getPTPReturn());
				System.out.println("XIRR "+mutualFundSipLookup.getXirr());*/
		}
		
		mutualFundSip.setTotalInvestedAmount(totalAmountInvested);
		mutualFundSip.setPortfolioValue(portfolioValue);
		
		
		
		
		//================END POD==========================
	} catch (Exception exp) {
		exp.printStackTrace();
		FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_MUTUAL_FUND,
				FinexaConstant.PRODUCT_CAL_MUTUAL_FUND_SIP, FinexaConstant.PRODUCT_CAL_MUTUAL_FUND_SIP_DESC, exp);
		FinexaBussinessException.logFinexaBusinessException(finexaBuss);
	}

	//mutualFundSip.setMutualFundLumpsumSipList(mutualFundLumpsumSipList);
	return mutualFundSipLookup;
}

public MutualFundLumpsumSipLookup getMutualFundLumpsumCalculationClient(double amountInvested, String isin, Date investedDate,
		int unitPurchased) {
	
	List<MutualFundLumpsumSipLookup> mutualFundLumpsumSipList = new ArrayList<MutualFundLumpsumSipLookup>();

	MutualFundLumpsumSipLookup mutualFundLumpSumLookup = null;
	MutualFundLumpsumSip mutualFundLumpSum = new MutualFundLumpsumSip();
	Calendar generalCal = Calendar.getInstance();
	double ouputcurrentUnitPurchased = 0.0;
	try {

		generalCal.setTime(investedDate);
		//System.out.println("generalCal "+generalCal.getTime());
		String strRefDate = "";
		String strRefMonth = "";
		String fiscalYear = "";
		int monthCounter = -1;
		double openingBal = 0.0;
		double currAmountInvested = 0.0;
		double currentUnitPurchased = 0.0;
		double closingBal = 0.0;
		double lastPortfolioValue = 0.0;
		double currNavRate = 0.0;
		double portfolioValue = 0.0;
		double currPtpReturns = 0.0;
		double annualisedCagr = 0.0;
		double investmentTenure = 0.0;
		double currentUnitPurchasedTotal = 0.0;
		double currentValue = 0.0;
		Calendar portfolioValueDate = Calendar.getInstance();
		portfolioValueDate.set(Calendar.MILLISECOND, 0);
		portfolioValueDate.set(Calendar.SECOND, 0);
		portfolioValueDate.set(Calendar.MINUTE, 0);
		portfolioValueDate.set(Calendar.HOUR_OF_DAY, 0);
		
		generalCal.set(Calendar.MILLISECOND, 0);
		generalCal.set(Calendar.SECOND, 0);
		generalCal.set(Calendar.MINUTE, 0);
		generalCal.set(Calendar.HOUR_OF_DAY, 0);
		
		//System.out.println("portfolioValueDate "+portfolioValueDate.getTime());
		
		List<MasterMutualFundLumpsumSip> mastermfnavList = mastermfDao
				.getMasterMutualFundLumpsumSipNavs(investedDate, portfolioValueDate.getTime(), isin);
		//System.out.println("mastermfnavList.size() "+mastermfnavList.size());
		//System.out.println("generalCal.getTime().compareTo(portfolioValueDate.getTime() "+generalCal.getTime().compareTo(portfolioValueDate.getTime()));
	
		while (generalCal.getTime().compareTo(portfolioValueDate.getTime()) == -1) {
			mutualFundLumpSumLookup = new MutualFundLumpsumSipLookup();
			if (monthCounter == -1) {
				strInterimDate = sdfOutput.format(generalCal.getTime());

				mutualFundLumpSumLookup.setRefDate(sdfOutput.parse(sdfOutput.format(investedDate)));
				strRefMonth = strInterimDate.substring(3);
				fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
				currAmountInvested = amountInvested;

				if(!mastermfnavList.isEmpty()){
				currNavRate = returnNAVRate(mutualFundLumpSumLookup.getRefDate(), mastermfnavList, isin);
				}
				if(currNavRate != 0){
				currentUnitPurchased = currAmountInvested / currNavRate;
				}
				if (currentUnitPurchased > 0) {
					ouputcurrentUnitPurchased = currentUnitPurchased;
					currentUnitPurchasedTotal=currentUnitPurchased;
				}
				closingBal = currentUnitPurchased + openingBal;
				portfolioValue = currNavRate * closingBal;
				lastPortfolioValue = portfolioValue;
				mutualFundLumpSumLookup.setScheme("Mutual Fund");
				mutualFundLumpSumLookup.setNav(currNavRate);
				mutualFundLumpSumLookup.setReferenceMonth(strRefMonth);
				mutualFundLumpSumLookup.setFinancialYear(fiscalYear);
				mutualFundLumpSumLookup.setAmountInvested(currAmountInvested);
				mutualFundLumpSumLookup.setUnitsPurchased(currentUnitPurchased);
				mutualFundLumpSumLookup.setClosingBal(closingBal);
				mutualFundLumpSumLookup.setPortfolioValue(portfolioValue);
				//mutualFundLumpsumSipList.add(mutualFundLumpSumLookup);
				
				
				monthCounter++;
				//System.out.println("=================================");
			} else {
				if (monthCounter > 0) {
					generalCal.add(Calendar.MONTH, 1);
					generalCal.set(Calendar.MILLISECOND, 0);
					generalCal.set(Calendar.SECOND, 0);
					generalCal.set(Calendar.MINUTE, 0);
					generalCal.set(Calendar.HOUR_OF_DAY, 0);
				}
				//System.out.println("generalCal "+generalCal.getTime());
				
				strInterimDate = sdfOutput.format(generalCal.getTime());
			
				
				strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);
				//System.out.println("strRefDate "+strRefDate);
				
				mutualFundLumpSumLookup.setRefDate(sdfOutput.parse(strRefDate));
				
			    //new
				Calendar lastDate = Calendar.getInstance();
				lastDate.setTime(sdfOutput.parse(strRefDate));
				//System.out.println("LastDate "+lastDate.getTime());
				
				lastDate.set(Calendar.MILLISECOND, 0);
				lastDate.set(Calendar.SECOND, 0);
				lastDate.set(Calendar.MINUTE, 0);
				lastDate.set(Calendar.HOUR_OF_DAY, 0);
				
				if (lastDate.getTime().compareTo(portfolioValueDate.getTime()) >= 0) {
					
					
					mutualFundLumpSumLookup.setRefDate(portfolioValueDate.getTime());
					if(!mastermfnavList.isEmpty()){
					currNavRate = returnNAVRate(mutualFundLumpSumLookup.getRefDate(), mastermfnavList, isin);
					}
					mutualFundLumpSumLookup.setScheme("Mutual Fund");
					currAmountInvested = 0.0;
					if(currNavRate != 0){
					currentUnitPurchased = currAmountInvested / currNavRate;
					}
					if (currentUnitPurchased > 0) {
						ouputcurrentUnitPurchased = currentUnitPurchased;
					}
					
					openingBal = closingBal;
				
					closingBal = currentUnitPurchased + openingBal;
					
					portfolioValue = currNavRate * closingBal;
					
					
					//testing
					currentValue = currNavRate*currentUnitPurchasedTotal;
					
					//======
					
					Calendar cstartDate = Calendar.getInstance();
					cstartDate.setTime(investedDate);
				    //new
					investmentTenure = FinanceUtil.YEARFRAC(cstartDate.getTime(), portfolioValueDate.getTime(), 1);
					System.out.println("investmentTenure "+investmentTenure);
					
					annualisedCagr = (Math.pow(portfolioValue / lastPortfolioValue, (1 / investmentTenure)) - 1);
					currPtpReturns = ((portfolioValue - lastPortfolioValue) / (double) lastPortfolioValue);

					mutualFundLumpSumLookup.setReferenceMonth(strRefMonth);
					mutualFundLumpSumLookup.setFinancialYear(fiscalYear);
					mutualFundLumpSumLookup.setOpeningBal(openingBal);
					mutualFundLumpSumLookup.setClosingBal(closingBal);
					
					mutualFundLumpSumLookup.setAmountInvested(amountInvested);
					mutualFundLumpSumLookup.setNav(currNavRate);
					mutualFundLumpSumLookup.setPortfolioValue(portfolioValue);
					mutualFundLumpSum.setPortfolioValue(portfolioValue);
					
					mutualFundLumpSum.setCacgr(annualisedCagr);
					mutualFundLumpSumLookup.setPTPReturn(currPtpReturns);
					mutualFundLumpSumLookup.setAnnualisedCagrReturns(annualisedCagr);
					mutualFundLumpSumLookup.setUnitsPurchased(currentUnitPurchased);
					mutualFundLumpSumLookup.setInvestmentTenure(investmentTenure);
					mutualFundLumpSumLookup.setGainLoss(portfolioValue-amountInvested);
					mutualFundLumpSumLookup.setCurrentportfoliovalue(currentValue);
					//mutualFundLumpsumSipList.add(mutualFundLumpSumLookup);
					
					/*System.out.println("currAmountInvested 222 "+currAmountInvested);
					System.out.println("currNavRate 222  "+currNavRate);
					System.out.println("currentUnitPurchased 222  "+currentUnitPurchased);
					System.out.println("openingBal 222  "+openingBal);
					System.out.println("closingBal 222  "+closingBal);
					System.out.println("portfolioValue 222  "+mutualFundLumpSumLookup.getPortfolioValue());
					System.out.println("annualisedCagr "+mutualFundLumpSumLookup.getAnnualisedCagrReturns());
					System.out.println("currPtpReturns "+mutualFundLumpSumLookup.getPTPReturn());
					System.out.println("investamount "+mutualFundLumpSumLookup.getAmountInvested());
					System.out.println("gainLoss "+mutualFundLumpSumLookup.getGainLoss());
					System.out.println("after discussion  "+mutualFundLumpSumLookup.getCurrentportfoliovalue());
					*/
					//System.out.println("=================================break");
					break;
				}else {
					

					strRefMonth = strRefDate.substring(3);
					
					openingBal = closingBal;
					
					fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
					
					currAmountInvested = 0.0;
					if(!mastermfnavList.isEmpty()){
					currNavRate = returnNAVRate(mutualFundLumpSumLookup.getRefDate(), mastermfnavList, isin);
					}
					if(currNavRate != 0){
					currentUnitPurchased = currAmountInvested / currNavRate;
					}
					if (currentUnitPurchased > 0) {
						ouputcurrentUnitPurchased = currentUnitPurchased;
					}
					closingBal = currentUnitPurchased + openingBal;
					
					//System.out.println("currNavRate           "+currNavRate);
					

					Calendar cstartDate = Calendar.getInstance();
					Calendar portValueDate = Calendar.getInstance();

					cstartDate.setTime(investedDate);
					portValueDate.setTime(sdfOutput.parse(strRefDate));
					
					

					investmentTenure = FinanceUtil.YEARFRAC(cstartDate.getTime(), portValueDate.getTime(), 1);
					
					portfolioValue = currNavRate * closingBal;
					
					annualisedCagr = (Math.pow(portfolioValue / lastPortfolioValue, (1 / investmentTenure)) - 1);


					
					 
					currPtpReturns = ((portfolioValue - lastPortfolioValue) / (double) lastPortfolioValue) * 100;
					
					
					
	            //System.out.println("=================================");
				}
				
				
				 
				mutualFundLumpSumLookup.setReferenceMonth(strRefMonth);
				mutualFundLumpSumLookup.setFinancialYear(fiscalYear);
				mutualFundLumpSumLookup.setAmountInvested(currAmountInvested);
				mutualFundLumpSumLookup.setOpeningBal(openingBal);
				mutualFundLumpSumLookup.setUnitsPurchased(currentUnitPurchased);
				mutualFundLumpSumLookup.setClosingBal(closingBal);
				mutualFundLumpSumLookup.setNav(currNavRate);
				mutualFundLumpSumLookup.setPortfolioValue(portfolioValue);
				mutualFundLumpSumLookup.setPTPReturn(currPtpReturns);
				mutualFundLumpSumLookup.setAnnualisedCagrReturns(annualisedCagr);
				mutualFundLumpSumLookup.setInvestmentTenure(investmentTenure);
				mutualFundLumpSumLookup.setMonthCounter(monthCounter);
				mutualFundLumpSumLookup.setScheme("Mutual Fund");
				//mutualFundLumpsumSipList.add(mutualFundLumpSumLookup);

				monthCounter++;
			}

		}
		

	} catch (Exception exp) {
		exp.printStackTrace();
		FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_MUTUAL_FUND,
				FinexaConstant.PRODUCT_CAL_MUTUAL_FUND_LUMSUM, FinexaConstant.PRODUCT_CAL_MUTUAL_FUND_LUMSUM_DESC,
				exp);
		FinexaBussinessException.logFinexaBusinessException(finexaBuss);
	}
	mutualFundLumpSum.setMutualFundLumpsumSipList(mutualFundLumpsumSipList);
	mutualFundLumpSum.setUnitPurchased((int) ouputcurrentUnitPurchased);

	return mutualFundLumpSumLookup;
}
}
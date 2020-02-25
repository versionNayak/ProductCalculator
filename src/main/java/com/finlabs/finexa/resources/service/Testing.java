package com.finlabs.finexa.resources.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.finlabs.finexa.resources.exception.FinexaBussinessException;
import com.finlabs.finexa.resources.model.MasterMutualFundLumpsumSip;
import com.finlabs.finexa.resources.model.MutualFundLumpsumSip;
import com.finlabs.finexa.resources.model.MutualFundLumpsumSipLookup;
import com.finlabs.finexa.resources.model.PPFFixedAmountDeposit;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;
import com.finlabs.finexa.resources.util.Transaction;

public class Testing {

	public static void main(String args[]) throws ParseException {
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    Date date = null;
		    Date dt = null;
		    Date dt1 = null;
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
		    
		  
		   try {
	       date = dateFormat.parse("2010-10-20");
	       System.out.println(date.toString()); // Wed Dec 04 00:00:00 CST 2013

	       String output = dateFormat.format(date);
	       System.out.println(output); // 2013-12-04
	        dt = dateFormat.parse(output);
	        System.out.println("dt "+dt);
	        
	        date = dateFormat.parse("2026-04-01");
		    System.out.println(date.toString()); // Wed Dec 04 00:00:00 CST 2013

		    output = dateFormat.format(date);
		    System.out.println(output); // 2013-12-04
		    dt1 = dateFormat.parse(output);
		    System.out.println("dt "+dt1);
	   } 
	   catch (ParseException e) {
	       e.printStackTrace();
	   }

	 // new EquityCalculatorService().getPortfolioValue(100,"INE534E01012",dt,30000);
	  new PPFFixedAmountService().getPPFFixedAmountCalculationDetails(200000,100000,"",15,4, 1, dt);
	
	  new PPFFixedAmountService().getPPFExtensionCalcuation(10, "Y", 5,4,1, dt1, 4247070.945533843, 2900000);

     // new EPF2Service().getEPF2CaculatedValues(dt, 65000, 9750, 30000, .08, 60, 60, "April");
	//new MutualFundLumpsumSipService().getMutualFundLumpsumCalculation(15000,"INF200K01QX4", dt,201);
	//new MutualFundLumpsumSipService().getMutualFundLumpsumCalculationClient(10000,"INF843K01AW7", dt,201);
    //new MutualFundLumpsumSipService().getMutualFundLumpsumCalculationPortfolio(1000,"INF090I01AE7", dt,201);
	
	//new MutualFundLumpsumSipService().getMutualFundLumpsumCalculationPortfolio(10000,"INF209K01WQ7", dt,201);
	// new MutualFundLumpsumSipService().getMutualFundLumpsumCalculationPortfolio(15000,"INF200K01QX4", dt,388);
	//new MutualFundLumpsumSipService().getMutualFundLumpsumCalculationPortfolio(10000,"INF200K01TP4", dt,120);
		   
	//new MutualFundLumpsumSipService().getMutualFundSIPCalculationForPorfolio(10000, "INF179KA1IZ7", dt,6,12);
    //new MutualFundLumpsumSipService().getMutualFundSIPCalculationPortFolio(1000, "INF090I01AE7", dt,240,12);
	 // new MutualFundLumpsumSipService().getMutualFundSIPCalculationPortFolio(1000, "INF082J01036", dt,36,12);
		  // new MutualFundLumpsumSipService().getMutualFundSIPCalculationPortFolio(1000, "INF174K01LS2", dt,36,12);
   //new MutualFundLumpsumSipService().getMutualFundSIPCalculationPortFolio(1000, "INF760K01EI4", dt,36,12);//canara
		   

	//Transaction transaction1 = new Transaction(-100000,"2014-01-01");

	//Transaction transaction13 = new Transaction(190585,"2018-11-05");


	//List<Transaction> xirrTransactionList = new ArrayList<Transaction>();
	//xirrTransactionList.add(transaction1);
	//xirrTransactionList.add(transaction13);
	
	//double Xirrrate = new com.finlabs.finexa.resources.util.Xirr(xirrTransactionList).xirr();
	//System.out.println("Xirrrate "+Xirrrate);
	//EquityCalculatorService ecs = new EquityCalculatorService();
	//double currentValue = ecs.getPortfolioValue(23, "INE413X01019");
		   
		// PPFFixedAmountService ppffd = new PPFFixedAmountService();
		 //PPFFixedAmountDeposit pffixedAmount = ppffd.getPPFFixedAmountCalculationDetails(500000,5000, "", 15,1,1,dt);	   
		// double deposit = ppffd.getPPFFixedAmountDepositDetails(5000,15,dt,1);
		 //System.out.println("deposit "+deposit);
	}
	/*public MutualFundLumpsumSip getMutualFundSIPCalculationPorfolioFor(double sipAmount, String isin, Date sipStartDate,
			int sipInstallment, int sipFrequency)*/ {/*
		
		System.out.println("sipAmount "+sipAmount);
		System.out.println("isin "+isin);
		System.out.println("sipStartDate "+sipStartDate);
		System.out.println("sipInstallment "+sipInstallment);
		System.out.println("sipFrequency "+sipFrequency);
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
			int monthCounter = 0;
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

			
			List<MasterMutualFundLumpsumSip> mastermfnavList = mastermfDao
					.getMasterMutualFundLumpsumSipNavs(sipStartDate, portfolioValueDate.getTime(), isin);
			
			//System.out.println("generalCal.getTime() "+generalCal.getTime());
		///	System.out.println("portfolioValueDate.getTime() "+portfolioValueDate.getTime());
			//System.out.println("generalCal.getTime().compareTo(portfolioValueDate.getTime() "+generalCal.getTime().compareTo(portfolioValueDate.getTime()));

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
					System.out.println("mutualFundSipLookup.getXirrCalc() "+mutualFundSipLookup.getXirrCalc());
					System.out.println("xirrDateFormat.format(mutualFundSipLookup.getRefDate()) "+xirrDateFormat.format(mutualFundSipLookup.getRefDate()));
					xirrTransactionList.add(transaction);
					
					mutualFundSipLookup.setUnitsPurchased(currentUnitPurchased);
					mutualFundSipLookup.setClosingBal(closingBal);
					mutualFundSipLookup.setNav(currNavRate);
					mutualFundSipLookup.setPortfolioValue(portfolioValue);
					mutualFundSipLookup.setMonthCounter(monthCounter);
					
					currAmountInvested = 0;
				
					System.out.println("================================");
				} else {
	                if(monthCounter>0) {
					generalCal.add(Calendar.MONTH, 1);
	                }
					
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

						
						mutualFundSipLookup.setAmountInvested(currAmountInvested);
						mutualFundSipLookup.setUnitsPurchased(0);
						mutualFundSipLookup.setScheme("");
					
						    currentUnitPurchased = currAmountInvested / currNavRate;
							currentPortfoliovalue = currentUnitPurchasedTotal*currNavRate;
						
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
						mutualFundSipLookup.setTotalInvestmentValue(totalAmountInvested);
						mutualFundSipLookup.setGainLoss(portfolioValue-totalAmountInvested);
						mutualFundSipLookup.setPTPReturn(PTPReturn);
						mutualFundSipLookup.setMonthCounter(0);
						mutualFundLumpsumSipList.add(mutualFundSipLookup);
						System.out.println("===============break");
						break;
					} else {
	                  // System.out.println("flag "+flag);
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
						
					
						if(installmentCounter <= sipInstallment) {
							currAmountInvested = sipAmount;
							if(monthCounter>0) {
							totalAmountInvested = totalAmountInvested + sipAmount;
							}
							
							currentUnitPurchased = currAmountInvested / currNavRate;
							if(monthCounter>0) {
							currentUnitPurchasedTotal = currentUnitPurchasedTotal+currentUnitPurchased;
							}
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

						
		
						mutualFundSipLookup.setUnitsPurchased(currentUnitPurchased);
						mutualFundSipLookup.setClosingBal(closingBal);
						mutualFundSipLookup.setNav(currNavRate);
						mutualFundSipLookup.setPortfolioValue(portfolioValue);
						mutualFundSipLookup.setMonthCounter(monthCounter);
						mutualFundLumpsumSipList.add(mutualFundSipLookup);
						currAmountInvested = 0;
						System.out.println("===============");
	                    }
					}
					
				}

				monthCounter++;
				serialCounter++;
				installmentCounter++;

			}
			mutualFundSip.setTotalInvestedAmount(totalAmountInvested);
			mutualFundSip.setPortfolioValue(portfolioValue);
			
			
			//================Result==========================
			System.out.println("after discussion "+mutualFundSipLookup.getCurrentportfoliovalue());
			System.out.println("current value "+mutualFundSipLookup.getPortfolioValue());
			System.out.println("investmentValue value "+mutualFundSipLookup.getTotalInvestmentValue());
			System.out.println("gainLoss "+mutualFundSipLookup.getGainLoss());
			System.out.println("PTP "+mutualFundSipLookup.getPTPReturn());
			System.out.println("XIRR "+mutualFundSipLookup.getXirr());
			
			//================END POD==========================
		} catch (Exception exp) {
			exp.printStackTrace();
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_MUTUAL_FUND,
					FinexaConstant.PRODUCT_CAL_MUTUAL_FUND_SIP, FinexaConstant.PRODUCT_CAL_MUTUAL_FUND_SIP_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}

		mutualFundSip.setMutualFundLumpsumSipList(mutualFundLumpsumSipList);
		return mutualFundSip;
	*/}


}

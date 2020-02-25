package com.finlabs.finexa.resources.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


import com.finlabs.finexa.resources.util.FinanceUtil;
import com.finlabs.finexa.resources.dao.MasterDAO;
import com.finlabs.finexa.resources.dao.MasterDAOImplementation;
import com.finlabs.finexa.resources.exception.FinexaBussinessException;
import com.finlabs.finexa.resources.model.EquityCalculator;
import com.finlabs.finexa.resources.model.EquityCalculatorLookup;
import com.finlabs.finexa.resources.model.MasterEquityCalculator;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class EquityCalculatorService {

	Calendar calInstance = Calendar.getInstance();
	String strInterimDate = "";
	SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MMMM-yyyy");

	public EquityCalculator getEquityCalculation(Date dateOfPurchased, double amountInvested, int noOfSharesPurchased,
			String isin) {
		List<EquityCalculatorLookup> equityCalcList = new ArrayList<EquityCalculatorLookup>();
		EquityCalculatorLookup equityCalculatorLookup = null;
		EquityCalculator equityCalc = new EquityCalculator();
		Calendar generalCal = Calendar.getInstance();
		generalCal.set(Calendar.MILLISECOND, 0);
		generalCal.set(Calendar.SECOND, 0);
		generalCal.set(Calendar.MINUTE, 0);
		generalCal.set(Calendar.HOUR_OF_DAY, 0);
		try {
			MasterDAO masterEquityDao = new MasterDAOImplementation();
			List<MasterEquityCalculator> MasterEquityClosingList = masterEquityDao.getMasterEquityCalculatorClosingPrice(isin);

			generalCal.setTime(dateOfPurchased);
			String strRefDate = "";
			String strRefMonth = "";
			String fiscalYear = "";
			int counter = 1;
			double openingBal = 0.0;
			double currAmountInvested = 0.0;
			double currentsharePurchased = 0.0;
			double portfolioValue = 0.0;
			double currPtpReturns = 0.0;
			double annualisedCagr = 0.0;
			double investmentTenure = 0.0;
			double lastPortfolioValue = 0.0;
			int currNoOfSharesPurchased = 0;
			// temp portfolio date
			Calendar calendar = Calendar.getInstance();
			Date portfolioDate = new Date();
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			portfolioDate = (calendar.getTime());
			while (generalCal.getTime().compareTo(portfolioDate) == -1) {
				equityCalculatorLookup = new EquityCalculatorLookup();
				if (counter == 1) {
					strInterimDate = sdfOutput.format(generalCal.getTime());

					equityCalculatorLookup.setRefDate(sdfOutput.parse(sdfOutput.format(dateOfPurchased)));

					strRefMonth = strInterimDate.substring(3);
					fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
					currAmountInvested = amountInvested;
					currNoOfSharesPurchased = noOfSharesPurchased;
					currentsharePurchased = amountInvested / noOfSharesPurchased;
					equityCalc.setPurchaseValue(currentsharePurchased);
					portfolioValue = currNoOfSharesPurchased * currentsharePurchased;
					lastPortfolioValue = portfolioValue;
					currPtpReturns = portfolioValue / portfolioValue - 1;
					annualisedCagr = currPtpReturns;
					equityCalculatorLookup.setRefDate(dateOfPurchased);
					equityCalculatorLookup.setReferenceMonth(strRefMonth);
					equityCalculatorLookup.setFinancialYear(fiscalYear);
					equityCalculatorLookup.setOpeningBal(openingBal);
					equityCalculatorLookup.setAmountInvested(currAmountInvested);
					equityCalculatorLookup.setNoOfSharesPurchased(currNoOfSharesPurchased);
					equityCalculatorLookup.setSharePrice(currentsharePurchased);
					equityCalculatorLookup.setPortfolioValue(portfolioValue);
					equityCalculatorLookup.setPtpReturns(currPtpReturns);
					equityCalculatorLookup.setAnnualisedCagrReturns(annualisedCagr);
					equityCalculatorLookup.setInvestmentTenure(investmentTenure);
					equityCalcList.add(equityCalculatorLookup);
					counter++;
					System.out.println("dateOfPurchased "+dateOfPurchased);
					System.out.println("strRefMonth "+strRefMonth);
					System.out.println("fiscalYear "+fiscalYear);
					System.out.println("openingBal "+openingBal);
					System.out.println("currAmountInvested "+currAmountInvested);
					System.out.println("currNoOfSharesPurchased "+currNoOfSharesPurchased);
					System.out.println("currentsharePurchased "+currentsharePurchased);
					System.out.println("portfolioValue "+portfolioValue);
					System.out.println("currPtpReturns "+currPtpReturns);
					System.out.println("annualisedCagr "+annualisedCagr);
					System.out.println("investmentTenure "+investmentTenure);

				} else {
					if (counter > 2) {
						generalCal.add(Calendar.MONTH, 1);
					}

					openingBal = portfolioValue;
					fiscalYear = FinexaDateUtil.getFiscalYear(generalCal.getTime());
					currAmountInvested = 0.0;

					if (generalCal.getTime().compareTo(portfolioDate) >= 0) {
						strInterimDate = sdfOutput.format(portfolioDate);
						SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
						java.util.Date dt = formatter.parse(strInterimDate);
						strRefDate = formatter.format(dt);

					} else {
						strInterimDate = sdfOutput.format(generalCal.getTime());
						strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);
					}
					strRefMonth = strRefDate.substring(3);
					equityCalculatorLookup.setRefDate(sdfOutput.parse(strRefDate));

					for (MasterEquityCalculator masterEquityCalculator : MasterEquityClosingList) {
						if ((equityCalculatorLookup.getRefDate().equals(masterEquityCalculator.getDate())
								&& masterEquityCalculator.getIsin().equals(isin))) {
							currentsharePurchased = masterEquityCalculator.getClosingBal();
							break;
						}

					}
					// currentsharePurchased = 202;
					portfolioValue = noOfSharesPurchased * currentsharePurchased;
					currPtpReturns = portfolioValue / lastPortfolioValue - 1;
					equityCalc.setPortfolioValue(portfolioValue);

					Calendar cstartDate = Calendar.getInstance();
					Calendar portValueDate = Calendar.getInstance();
					cstartDate.setTime(dateOfPurchased);
					portValueDate.setTime(sdfOutput.parse(strRefDate));

					LocalDate dateFrom = LocalDate.of(cstartDate.get(Calendar.YEAR), cstartDate.get(Calendar.MONTH) + 1,
							cstartDate.get(Calendar.DATE));
					LocalDate dateTo = LocalDate.of(portValueDate.get(Calendar.YEAR),
							portValueDate.get(Calendar.MONTH) + 1, portValueDate.get(Calendar.DATE));
					Period intervalPeriod = Period.between(dateFrom, dateTo);
					int days = intervalPeriod.getDays();
					investmentTenure = intervalPeriod.getYears() + intervalPeriod.getMonths() / (double) 12
							+ days / (double) 365;
					if (investmentTenure < 1) {
						annualisedCagr = currPtpReturns;
					} else {
						annualisedCagr = Math.pow((1 + currPtpReturns), (1 / investmentTenure)) - 1;
					}
					if (generalCal.getTime().compareTo(portfolioDate) >= 0) {

						equityCalc.setReturnCagr(annualisedCagr);
					}
					currNoOfSharesPurchased = 0;
					equityCalculatorLookup.setReferenceMonth(strRefMonth);
					equityCalculatorLookup.setFinancialYear(fiscalYear);
					equityCalculatorLookup.setOpeningBal(openingBal);
					equityCalculatorLookup.setAmountInvested(currAmountInvested);
					equityCalculatorLookup.setNoOfSharesPurchased(currNoOfSharesPurchased);
					equityCalculatorLookup.setSharePrice(currentsharePurchased);
					equityCalculatorLookup.setPortfolioValue(portfolioValue);
					equityCalculatorLookup.setPtpReturns(currPtpReturns);
					equityCalculatorLookup.setAnnualisedCagrReturns(annualisedCagr);
					equityCalculatorLookup.setInvestmentTenure(investmentTenure);
					System.out.println("strRefMonth "+strRefMonth);
					System.out.println("fiscalYear "+fiscalYear);
					System.out.println("openingBal "+openingBal);
					System.out.println("currAmountInvested "+currAmountInvested);
					System.out.println("currNoOfSharesPurchased "+currNoOfSharesPurchased);
					System.out.println("currentsharePurchased "+currentsharePurchased);
					System.out.println("portfolioValue "+portfolioValue);
					System.out.println("currPtpReturns "+currPtpReturns);
					System.out.println("annualisedCagr "+annualisedCagr);
					System.out.println("investmentTenure "+investmentTenure);
					equityCalcList.add(equityCalculatorLookup);
					counter++;

				}
			}
		} catch (Exception exp) {
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_EQUITY,
					FinexaConstant.PRODUCT_CAL_EQUITY_CODE, FinexaConstant.PRODUCT_CAL_EQUITY_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		equityCalc.setReturnAbsolute(equityCalc.getPortfolioValue() / amountInvested - 1);
		equityCalc.setEquityCalLookupList(equityCalcList);
		return equityCalc;
	}
	

	public EquityCalculatorLookup getPortfolioValue(int noOfSharesPurchased,String isin,Date dateOfPurchased,double amountInvested) throws RuntimeException {
		// TODO Auto-generated method stub
		double closingPrice = 0;
		double currentValue = 0;
		EquityCalculatorLookup ec = new EquityCalculatorLookup();
		try {
			System.out.println("isin: " + isin);
			
			   MasterDAO masterEquityDao = new MasterDAOImplementation();
			    int i = 1;
			    Calendar calToday = Calendar.getInstance();
				calToday.set(Calendar.MILLISECOND, 0);
				calToday.set(Calendar.SECOND, 0);
				calToday.set(Calendar.MINUTE, 0);
				calToday.set(Calendar.HOUR_OF_DAY, 0);
					
				while (true) {
					
					calToday.add(Calendar.DAY_OF_MONTH,-1);

					Date oneDayBefore= calToday.getTime();
					closingPrice = masterEquityDao.getMasterEquityCalculatorClosingPrice(isin, oneDayBefore);
					if (closingPrice != 0) { 	
						currentValue = closingPrice * noOfSharesPurchased;	
						break;
					} 
					if(i==7) {
						break;
					}
					i++;					
				}
				
				  ec = new EquityCalculatorService().getEquityCAGR(dateOfPurchased,amountInvested,currentValue);
		          ec.setCurrentvalue(currentValue);
		} catch (RuntimeException e) {
			e.printStackTrace();
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		}
		return ec;
	}
	public EquityCalculatorLookup getEquityCAGR(Date dateOfPurchased, double amountInvested,double portfolioValue) {
		double currPtpReturns = 0.0;
		double annualisedCagr = 0.0;
		double investmentTenure = 0.0;
		EquityCalculatorLookup equityCalculatorLookup = new EquityCalculatorLookup();
		
		Calendar generalCal = Calendar.getInstance();
		generalCal.set(Calendar.MILLISECOND, 0);
		generalCal.set(Calendar.SECOND, 0);
		generalCal.set(Calendar.MINUTE, 0);
		generalCal.set(Calendar.HOUR_OF_DAY, 0);
		
		investmentTenure = FinanceUtil.YEARFRAC(dateOfPurchased, generalCal.getTime(), 1);
		
		/*System.out.println("portfolioValue "+portfolioValue);
		System.out.println("amountInvested "+amountInvested);
		System.out.println("(portfolioValue / amountInvested) "+(portfolioValue / amountInvested));
		System.out.println("(portfolioValue / amountInvested) -1 "+((portfolioValue / amountInvested) - 1));*/
		
		currPtpReturns = (portfolioValue / amountInvested) - 1;
		
		
		//System.out.println("investmentTenure "+investmentTenure);
		if (investmentTenure <= 1) {
			annualisedCagr = currPtpReturns;
		} else {
			annualisedCagr = Math.pow((1 + currPtpReturns), (1 / investmentTenure)) - 1;
		}


		equityCalculatorLookup.setReturnCagr(annualisedCagr);
		equityCalculatorLookup.setPtpReturns(currPtpReturns);
		equityCalculatorLookup.setReturnAbsolute(currPtpReturns);
		
		return equityCalculatorLookup;
	}
	/*public EquityCalculatorLookup getEquityCal(Date dateOfPurchased, double amountInvested,double portfolioValue, double lastPortfolioValue) {
		
		EquityCalculatorLookup equityCalculatorLookup = null;
	
		try {
			Calendar generalCal = Calendar.getInstance();
			generalCal.set(Calendar.MILLISECOND, 0);
			generalCal.set(Calendar.SECOND, 0);
			generalCal.set(Calendar.MINUTE, 0);
			generalCal.set(Calendar.HOUR_OF_DAY, 0);
			generalCal.setTime(dateOfPurchased);
			String strRefDate = "";
			String strRefMonth = "";
			int counter = 1;
			double currPtpReturns = 0.0;
			double annualisedCagr = 0.0;
			double investmentTenure = 0.0;
			// temp portfolio date
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MILLISECOND, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			
			Date portfolioDate = (calendar.getTime());
		
			while (generalCal.getTime().compareTo(portfolioDate) == -1) {
				System.out.println("generalCal.getTime() "+generalCal.getTime());
				System.out.println("portfolioDate "+portfolioDate);
				//System.out.println("------start-----------");
				equityCalculatorLookup = new EquityCalculatorLookup();
				if (counter == 1) {
					strInterimDate = sdfOutput.format(generalCal.getTime());
					strRefMonth = strInterimDate.substring(3);
			

				} else {
					if (counter > 2) {
						generalCal.add(Calendar.MONTH, 1);
					}

					if (generalCal.getTime().compareTo(portfolioDate) >= 0) {
						strInterimDate = sdfOutput.format(portfolioDate);
						SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
						java.util.Date dt = formatter.parse(strInterimDate);
						strRefDate = formatter.format(dt);
						//System.out.println("111 strRefDate "+strRefDate);

					} else {
						//System.out.println("generalCal.getTime() "+generalCal.getTime());
						strInterimDate = sdfOutput.format(generalCal.getTime());
						//System.out.println("strInterimDate "+strInterimDate);
						strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);
						//System.out.println("222 strRefDate "+strRefDate);

					}
					strRefMonth = strRefDate.substring(3);
					
					//System.out.println("portfolioValue "+portfolioValue);
					//System.out.println("lastPortfolioValue "+lastPortfolioValue);
					
					//As per discussion with Nissy correction Done (wrong in excel)
					currPtpReturns = portfolioValue / lastPortfolioValue - 1;
					
					
					//System.out.println("currPtpReturns "+currPtpReturns);
					
					Calendar cstartDate = Calendar.getInstance();
					cstartDate.set(Calendar.MILLISECOND, 0);
					cstartDate.set(Calendar.SECOND, 0);
					cstartDate.set(Calendar.MINUTE, 0);
					cstartDate.set(Calendar.HOUR_OF_DAY, 0);
					cstartDate.setTime(dateOfPurchased);
					
					Calendar portValueDate = Calendar.getInstance();
					portValueDate.set(Calendar.MILLISECOND, 0);
					portValueDate.set(Calendar.SECOND, 0);
					portValueDate.set(Calendar.MINUTE, 0);
					portValueDate.set(Calendar.HOUR_OF_DAY, 0);
					portValueDate.setTime(sdfOutput.parse(strRefDate));

					LocalDate dateFrom = LocalDate.of(cstartDate.get(Calendar.YEAR), cstartDate.get(Calendar.MONTH) + 1,
							cstartDate.get(Calendar.DATE));
					LocalDate dateTo = LocalDate.of(portValueDate.get(Calendar.YEAR),
							portValueDate.get(Calendar.MONTH) + 1, portValueDate.get(Calendar.DATE));
					
					//System.out.println("dateFrom "+dateFrom);
					//System.out.println("dateTo "+dateTo);
					Period intervalPeriod = Period.between(dateFrom, dateTo);
					//System.out.println("intervalPeriod "+intervalPeriod);
					int days = intervalPeriod.getDays();
					//System.out.println("days "+days);
					investmentTenure = intervalPeriod.getYears() + (intervalPeriod.getMonths() / (double) 12)
							+ (days / (double) 365);
					System.out.println("investmentTenure "+investmentTenure);
					if (investmentTenure < 1) {
						annualisedCagr = currPtpReturns;
					} else {
						annualisedCagr = Math.pow((1 + currPtpReturns), (1 / investmentTenure)) - 1;
					}
					if (generalCal.getTime().compareTo(portfolioDate) >= 0) {

						equityCalculatorLookup.setReturnCagr(annualisedCagr);
					}
					

					equityCalculatorLookup.setPtpReturns(currPtpReturns);
					//equityCalculatorLookup.setAnnualisedCagrReturns(annualisedCagr);
					
					
					//System.out.println("annualisedCagr "+equityCalculatorLookup.getAnnualisedCagrReturns());
					
					

				}
				counter++;
				
				equityCalculatorLookup.setReturnAbsolute(portfolioValue / lastPortfolioValue - 1);
				//System.out.println("getReturnAbsolute "+equityCalculatorLookup.getReturnAbsolute());
				//System.out.println("-----end------------");
			}
		} catch (Exception exp) {
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_EQUITY,
					FinexaConstant.PRODUCT_CAL_EQUITY_CODE, FinexaConstant.PRODUCT_CAL_EQUITY_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		
	
		return equityCalculatorLookup;
	}*/
	

		
	
}

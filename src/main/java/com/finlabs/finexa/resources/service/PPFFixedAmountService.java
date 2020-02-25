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
import com.finlabs.finexa.resources.model.MasterPPFFixedAmountDeposit;
import com.finlabs.finexa.resources.model.PPFFixedAmountDeposit;
import com.finlabs.finexa.resources.model.PPFFixedAmountLookup;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class PPFFixedAmountService {
	Calendar cDepositDate = Calendar.getInstance();
	Calendar cMaturityDate = Calendar.getInstance();
	Calendar cInterimDate = Calendar.getInstance();
	String strInterimDate = "";
	SimpleDateFormat sdfOutput = new SimpleDateFormat("dd-MMM-yyyy");

	String strRefDate = "";
	String strRefMonth = "";
	String fiscalYear = "";
	double openingBal = 0.0;
	double interestEarned = 0.0;
	double totalInterestEarned = 0.0;
	double interestCredit = 0.0;
	double totalInterestAccrued = 0.0;
	double totalInterestCredit = 0.0;
	double totalAmountDeposited = 0.0;
	boolean lastDataRowFlag = true;
	double totalAmo = 0.0;
	long daysToMaturity = 0;
	Date dtLastDayOfMonth = new Date();
	Date lastMonRef = new Date();
	Date dtMaturityDate = new Date();
	int varTermMonths = 0;
	int lookupSerialNumber = 1;
	boolean checkInterestRate = false;
	//double currentBalance = 0;

	public PPFFixedAmountDeposit getPPFFixedAmountCalculationDetails(double currentBalance,double deposit, String tenureType, int tenure,
			int rdDepositFreq, int compundingFreq, Date depositDate) {
		
		
		/*System.out.println("currentBalance "+currentBalance);
		System.out.println("deposit "+deposit);
		System.out.println("tenureType "+tenureType);
		System.out.println("tenure "+tenure);
		System.out.println("rdDepositFreq "+rdDepositFreq);
		System.out.println("compundingFreq "+compundingFreq);
		System.out.println("depositDate "+depositDate);*/
		PPFFixedAmountDeposit ppfAmountDepositOutput = new PPFFixedAmountDeposit();
		try {
			openingBal  = 0;
			//System.out.println("currentBalance "+currentBalance);
			double amountDeposited = deposit;
			double rateOfInterest = 0.0;
			varTermMonths = (int) tenure * 12;
			//System.out.println("varTermMonths "+varTermMonths);
			double closingBal = 0;
			int rdDepositFreqConstantLookup = 12 / rdDepositFreq;
			//System.out.println("rdDepositFreqConstantLookup "+rdDepositFreqConstantLookup);
			int rdDepositFreqInterestPaid = rdDepositFreq;
			//System.out.println("rdDepositFreqInterestPaid "+rdDepositFreqInterestPaid);
			//System.out.println("compundingFreq "+compundingFreq);
			int compundingFreqInterestPaid = 12 / compundingFreq;
			//System.out.println("compundingFreqInterestPaid "+compundingFreqInterestPaid);
			MasterDAO mastersukDao = new MasterDAOImplementation();
			List<MasterPPFFixedAmountDeposit> masterPPFFDList = mastersukDao.getMasterPPFFixedAmountDepositInterestRates();
			Date varDepositDate = depositDate;
			//System.out.println("varDepositDate "+varDepositDate);
			
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			
			//System.out.println("cal "+cal);
			
			Date currentDate = cal.getTime();
			
			//new Code as per req
			cDepositDate.setTime(varDepositDate);
			cInterimDate.setTime(currentDate);
		
			//cDepositDate.setTime(varDepositDate);
			//cInterimDate.setTime(varDepositDate);
			cMaturityDate.setTime(varDepositDate);
			
			
			
			
			int month = cMaturityDate.get(Calendar.MONTH) + 1;
			//System.out.println("month "+month);
					
					
			int day = cMaturityDate.get(Calendar.DAY_OF_MONTH);
			//System.out.println("day "+day);
			if (month > 3) {
				cMaturityDate.set(Calendar.YEAR, cMaturityDate.get(Calendar.YEAR) + 1);
				cMaturityDate.set(cMaturityDate.get(Calendar.YEAR), 02, 31);
				//System.out.println("cMaturityDate "+cMaturityDate.getTime());
			} else {
				cMaturityDate.set(cMaturityDate.get(Calendar.YEAR), 02, 31);
				//System.out.println("else cMaturityDate "+cMaturityDate.getTime());
			}
			//System.out.println("varTermMonths "+varTermMonths);
			cMaturityDate.add(Calendar.MONTH, varTermMonths);
			//System.out.println("cMaturityDate "+cMaturityDate.getTime());
			ppfAmountDepositOutput.setMaturityDate(cMaturityDate.getTime());
	
			
			List<PPFFixedAmountLookup> ppfAmountLookupList = new ArrayList<PPFFixedAmountLookup>();
			int serialNo = 0;
			//System.out.println("masterPPFFDList "+masterPPFFDList.size());
			for (MasterPPFFixedAmountDeposit masterPPFFD : masterPPFFDList) {
				
				/*
				 * if (masterPPFFD.getEndDate() == null) { Calendar cal =
				 * Calendar.getInstance(); cal.setTime(masterPPFFD.getStartDate());
				 * cal.add(Calendar.DATE, 1); masterPPFFD.setEndDate(cal.getTime());
				 * }
				 */
				
				if ((dtLastDayOfMonth.after(masterPPFFD.getStartDate())
						&& (masterPPFFD.getEndDate() == null || dtLastDayOfMonth.before(masterPPFFD.getEndDate())))
						|| dtLastDayOfMonth.equals(masterPPFFD.getStartDate())
						|| (masterPPFFD.getEndDate() != null ? dtLastDayOfMonth.equals(masterPPFFD.getEndDate()) : false)) {
				
				
			/*	if ((depositDate.after(masterPPFFD.getStartDate())
						&& (masterPPFFD.getEndDate() == null || depositDate.before(masterPPFFD.getEndDate())))
						|| depositDate.equals(masterPPFFD.getStartDate())
						|| (masterPPFFD.getEndDate() != null ? depositDate.equals(masterPPFFD.getEndDate()) : false)) {
				*/
					ppfAmountDepositOutput.setAnnualInterest(masterPPFFD.getInterestRate());
					//System.out.println("masterPPFFD.getInterestRate() "+masterPPFFD.getInterestRate());
					checkInterestRate = true;
				}			
				if (checkInterestRate) {
					break;
				}

			}
			if(ppfAmountDepositOutput.getAnnualInterest() == 0 ) {
				//System.out.println("masterPPFFDList.get((masterPPFFDList.size() - 1)).getInterestRate() "+masterPPFFDList.get((masterPPFFDList.size() - 1)).getInterestRate());
				double interestRate = masterPPFFDList.get((masterPPFFDList.size() - 1)).getInterestRate();
				ppfAmountDepositOutput.setAnnualInterest(interestRate);
				//System.out.println("ppfAmountDepositOutput.setAnnualInterest "+ppfAmountDepositOutput.getAnnualInterest());
			}
		//	System.out.println("ppfAmountDepositOutput.setAnnualInterest() "+ppfAmountDepositOutput.getAnnualInterest());
			Calendar extensionStartCal = null;
			
			int i =1;
			while (true) {
				//System.out.println("while start=====================================");
				checkInterestRate = false;
				PPFFixedAmountLookup ppfAmountLookup = new PPFFixedAmountLookup();

			//	new code for new req
				
				if(i == 1) {
				openingBal = currentBalance;
				}else {
				 openingBal = closingBal;
				}
				
				//System.out.println("openingBal "+openingBal);
				//System.out.println("closingBal "+closingBal);
				
				java.util.Date dtInterimDate = cInterimDate.getTime();
				//System.out.println("dtInterimDate "+dtInterimDate);
				
				//Ref date
				
				strInterimDate = sdfOutput.format(cInterimDate.getTime());
				//System.out.println("strInterimDate "+strInterimDate);
				strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);	
	
				//System.out.println("strRefDate "+strRefDate);
				try {
					dtLastDayOfMonth = sdfOutput.parse(strRefDate);
					//System.out.println("dtLastDayOfMonth "+dtLastDayOfMonth);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				strRefMonth = strRefDate.substring(3);
				//System.out.println("strRefMonth "+strRefMonth);
				fiscalYear = FinexaDateUtil.getFiscalYear(dtInterimDate);

				//System.out.println("fiscalYear "+fiscalYear);
				long currentDaysToMaturity = 0;

				for (MasterPPFFixedAmountDeposit masterPPFFD : masterPPFFDList) {
					/*
					 * if (masterPPFFD.getEndDate() == null) { Calendar cal =
					 * Calendar.getInstance();
					 * cal.setTime(masterPPFFD.getStartDate());
					 * cal.add(Calendar.DATE, 1);
					 * masterPPFFD.setEndDate(cal.getTime()); }
					 */
					if ((dtLastDayOfMonth.after(masterPPFFD.getStartDate())
							&& (masterPPFFD.getEndDate() == null || dtLastDayOfMonth.before(masterPPFFD.getEndDate())))
							|| dtLastDayOfMonth.equals(masterPPFFD.getStartDate()) || (masterPPFFD.getEndDate() != null
									? dtLastDayOfMonth.equals(masterPPFFD.getEndDate()) : false)) {
						
						
						rateOfInterest = masterPPFFD.getInterestRate();
						checkInterestRate = true;
						
					}
					if (checkInterestRate) {
						break;
					}

				}
				
				if (lookupSerialNumber == 1) {
					
					daysToMaturity = FinexaDateUtil.getDays(cMaturityDate.getTime(), cInterimDate.getTime());
					
					currentDaysToMaturity = FinexaDateUtil.getDays(dtLastDayOfMonth, varDepositDate);
					
				} else {
					
					currentDaysToMaturity = FinexaDateUtil.getDays(dtLastDayOfMonth, lastMonRef);
					

				}
				
				daysToMaturity = daysToMaturity - currentDaysToMaturity;
				
				if (serialNo % rdDepositFreqConstantLookup == 0) {
					amountDeposited = deposit;
					//System.out.println("amountDeposited "+amountDeposited);
				}
				
				if (day < 6) {
					
					interestEarned = (deposit) * (rateOfInterest) / compundingFreqInterestPaid
							+ (openingBal * (rateOfInterest) / compundingFreqInterestPaid);
					
				} else {
					interestEarned = 0 + (openingBal * (rateOfInterest) / compundingFreqInterestPaid);
					
				}
				totalInterestAccrued = totalInterestEarned + interestEarned;
			
				totalInterestEarned = totalInterestEarned + interestEarned;
				
				if (cInterimDate.get(Calendar.MONTH) + 1 == 3) {
					if (serialNo > 0) {
						interestCredit = totalInterestAccrued - totalInterestCredit;

						
					}
				} else {
					interestCredit = 0;
					//System.out.println("else interestCredit "+interestCredit);
				}
				
				if (dtLastDayOfMonth.after(ppfAmountDepositOutput.getMaturityDate()) && lastDataRowFlag) {
					ppfAmountDepositOutput.setMaturityAmount(closingBal);
					lastDataRowFlag = false;

				}
				
				totalInterestCredit = totalInterestCredit + interestCredit;
				                                             
				closingBal = openingBal + interestCredit + amountDeposited;
				
				if(i == 1) {
					//System.out.println("closingBal "+closingBal);
					currentBalance = closingBal;
					//System.out.println("currentBalance "+currentBalance);
				}
				
				if (dtInterimDate.after(ppfAmountDepositOutput.getMaturityDate())) {
					interestCredit = 0;
					interestEarned = 0;
					totalInterestEarned = 0;
					serialNo = 0;
					amountDeposited = 0;
					closingBal = 0;
					strRefDate = "";
					strRefMonth = "";
					fiscalYear = "";
					openingBal = 0;
					rateOfInterest = 0;
					totalInterestAccrued = 0;
				}
				totalAmountDeposited = totalAmountDeposited + amountDeposited;
				ppfAmountLookup.setSerialNo(lookupSerialNumber);
				ppfAmountLookup.setReferenceDate(strRefDate);
				ppfAmountLookup.setReferenceMonth(strRefMonth);
				ppfAmountLookup.setFinancialYear(fiscalYear);
				ppfAmountLookup.setOpeningBal(openingBal);
				ppfAmountLookup.setAmountDeposited(amountDeposited);
				ppfAmountLookup.setInterestRate(rateOfInterest);
				ppfAmountLookup.setInterestEarned(interestEarned);
				ppfAmountLookup.setTotalInterestAccrued(totalInterestAccrued);
				ppfAmountLookup.setInterestCredited(interestCredit);
				ppfAmountLookup.setClosingBalance(closingBal);
				ppfAmountLookup.setDaysToMaturity(daysToMaturity);
			 
				if (dtLastDayOfMonth.after(cMaturityDate.getTime())) {
					break;
				}
                
				
				ppfAmountLookupList.add(ppfAmountLookup);
				//System.out.println("before cInterimDate increase "+cInterimDate.getTime());
				cInterimDate.add(Calendar.MONTH, 1);
				//System.out.println("after cInterimDate increase "+cInterimDate.getTime());
				//strInterimDate = sdfOutput.format(dtInterimDate);
				lastMonRef = dtLastDayOfMonth;
				serialNo++;
				lookupSerialNumber++;
				amountDeposited = 0;
				i++;

			}
			ppfAmountDepositOutput.setAnnualAmountDeposited(deposit * rdDepositFreqInterestPaid);
			ppfAmountDepositOutput.setTotalAmountDeposited(totalAmountDeposited);
			ppfAmountDepositOutput.setTotalInterestReceived(
					ppfAmountDepositOutput.getMaturityAmount() - ppfAmountDepositOutput.getTotalAmountDeposited());
			ppfAmountDepositOutput.setPpfFixedAmountLookupList(ppfAmountLookupList);
			ppfAmountDepositOutput.setCurrentBalance(currentBalance);
			//System.out.println("ppfAmountDepositOutput.setCurrentBalance "+ppfAmountDepositOutput.getCurrentBalance());
			ppfAmountDepositOutput.setDepositDate(varDepositDate);
			ppfAmountDepositOutput.setPpfFixedAmountLookupList(ppfAmountLookupList);
			
			cInterimDate.setTime(ppfAmountDepositOutput.getMaturityDate());
			cInterimDate.add(Calendar.DAY_OF_MONTH, 1);
			ppfAmountDepositOutput.setDepositDate(cInterimDate.getTime());
			//System.out.println("cagr "+ppfAmountDepositOutput.getAnnualInterest());
			//System.out.println("matuirity Date "+ppfAmountDepositOutput.getMaturityDate());
			//System.out.println("matuirity amount "+ppfAmountDepositOutput.getMaturityAmount());
			return ppfAmountDepositOutput;
		} catch (Exception exp) {
			exp.printStackTrace();
			// TODO Auto-generated catch block
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_PPF_FIXED_AMOUNT,
					FinexaConstant.PRODUCT_CAL_PPF_FIXED_AMOUNT_CODE, FinexaConstant.PRODUCT_CAL_PPF_FIXED_AMOUNT_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return new PPFFixedAmountDeposit();
	}

	public PPFFixedAmountDeposit getPPFExtensionCalcuation(double deposit, String tenureType, int tenure,
			int rdDepositFreq, int compundingFreq, Date depositDate, double maturityAmount,
			double totalAmountDiposited) {
		/*System.out.println("===============start ");
		System.out.println("deposit "+deposit);
		System.out.println("tenureType "+tenureType);
		System.out.println("rdDepositFreq "+rdDepositFreq);
		System.out.println("compundingFreq "+compundingFreq);
		System.out.println("depositDate "+depositDate);
		System.out.println("maturityAmount "+maturityAmount);
		System.out.println("totalAmountDiposited "+totalAmountDiposited);
*/
		PPFFixedAmountDeposit ppfAmountDepositOutput = new PPFFixedAmountDeposit();
		try {
			double amountDeposited = deposit;

			varTermMonths = (int) tenure * 12;
			double rateOfInterest = 0.0;
			openingBal = 0;
			double closingBal = 0;
			int rdDepositFreqConstantLookup = 12 / rdDepositFreq;
			int rdDepositFreqInterestPaid = rdDepositFreq;
			int compundingFreqInterestPaid = 12 / compundingFreq;

			Date varDepositDate = depositDate;
			cDepositDate.setTime(varDepositDate);
			cInterimDate.setTime(varDepositDate);
			cMaturityDate.setTime(depositDate);
			cMaturityDate.add(Calendar.MONTH, varTermMonths);
			cMaturityDate.add(Calendar.DAY_OF_MONTH, -1);
			ppfAmountDepositOutput.setMaturityDate(cMaturityDate.getTime());
			strInterimDate = sdfOutput.format(cDepositDate.getTime());
			int day = cDepositDate.get(Calendar.DAY_OF_MONTH);
			List<PPFFixedAmountLookup> ppfAmountLookupList = new ArrayList<PPFFixedAmountLookup>();

			int amountDepositSerial = 0;
			MasterDAO mastersukDao = new MasterDAOImplementation();
			List<MasterPPFFixedAmountDeposit> masterPPFFDList = mastersukDao.getMasterPPFFixedAmountDepositInterestRates();

			while (true) {
				//System.out.println("===============start loop");
				boolean checkInterestRate = false;
				PPFFixedAmountLookup ppfAmountLookup = new PPFFixedAmountLookup();
				//System.out.println("lookupSerialNumber "+lookupSerialNumber);
				if (lookupSerialNumber == 1) {
					openingBal = maturityAmount;
				} else {
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

				if (amountDepositSerial % rdDepositFreqConstantLookup == 0) {
					amountDeposited = deposit;

				}

				for (MasterPPFFixedAmountDeposit masterPPFFD : masterPPFFDList) {
					/*
					 * if (masterPPFFD.getEndDate() == null) { Calendar cal =
					 * Calendar.getInstance();
					 * cal.setTime(masterPPFFD.getStartDate());
					 * cal.add(Calendar.DATE, 1);
					 * masterPPFFD.setEndDate(cal.getTime()); }
					 */
					
					if ((dtLastDayOfMonth.after(masterPPFFD.getStartDate())
							&& (masterPPFFD.getEndDate() == null || dtLastDayOfMonth.before(masterPPFFD.getEndDate())))
							|| dtLastDayOfMonth.equals(masterPPFFD.getStartDate()) || (masterPPFFD.getEndDate() != null
									? dtLastDayOfMonth.equals(masterPPFFD.getEndDate()) : false)) {
						 //System.out.println("dtLastDayOfMonth "+dtLastDayOfMonth);
						// System.out.println("masterPPFFD.getEndDate() "+masterPPFFD.getEndDate());
						// System.out.println("masterPPFFD.getStartDate() "+masterPPFFD.getStartDate());
						rateOfInterest = masterPPFFD.getInterestRate();
						checkInterestRate = true;
					}
					if (checkInterestRate) {
						break;
					}

				}

				if (day < 6) {
					interestEarned = (amountDeposited) * (rateOfInterest) / compundingFreqInterestPaid
							+ (openingBal * (rateOfInterest) / compundingFreqInterestPaid);
				} else {
					interestEarned = 0 + (openingBal * (rateOfInterest) / compundingFreqInterestPaid);
				}
				totalInterestAccrued = totalInterestEarned + interestEarned;
				totalInterestEarned = totalInterestEarned + interestEarned;

				if (cInterimDate.get(Calendar.MONTH) + 1 == 3) {
					interestCredit = totalInterestAccrued - totalInterestCredit;
				} else {
					interestCredit = 0;

				}
                //System.out.println("ppfAmountDepositOutput.getMaturityDate() "+ppfAmountDepositOutput.getMaturityDate());
                //System.out.println("dtLastDayOfMonth "+dtLastDayOfMonth);
                //System.out.println("lastDataRowFlag "+lastDataRowFlag);
                if (dtLastDayOfMonth.after(ppfAmountDepositOutput.getMaturityDate()) && lastDataRowFlag) {
					ppfAmountDepositOutput.setMaturityAmount(closingBal);
					lastDataRowFlag = false;

				}
               // System.out.println("=============IMPStart+++++++++");
				totalInterestCredit = totalInterestCredit + interestCredit;
				//System.out.println("openingBal "+openingBal);
				//System.out.println("interestCredit "+interestCredit);
				//System.out.println("amountDeposited "+amountDeposited);
				closingBal = openingBal + interestCredit + amountDeposited;
				//System.out.println("closingBal "+closingBal);
				// System.out.println("=============IMPEnd+++++++++");

				if (dtInterimDate.after(ppfAmountDepositOutput.getMaturityDate())) {
					interestCredit = 0;
					interestEarned = 0;
					totalInterestEarned = 0;
					closingBal = 0;
					strRefDate = "";
					strRefMonth = "";
					fiscalYear = "";
					openingBal = 0;
					rateOfInterest = 0;
					totalInterestAccrued = 0;
				}

				ppfAmountLookup.setSerialNo(lookupSerialNumber);
				ppfAmountLookup.setReferenceDate(strRefDate);
				ppfAmountLookup.setReferenceMonth(strRefMonth);
				ppfAmountLookup.setFinancialYear(fiscalYear);
				ppfAmountLookup.setOpeningBal(Math.round(openingBal));
				ppfAmountLookup.setAmountDeposited(Math.round(amountDeposited));
				ppfAmountLookup.setInterestRate(rateOfInterest);
				ppfAmountLookup.setInterestEarned(Math.round(interestEarned));
				ppfAmountLookup.setTotalInterestAccrued(totalInterestAccrued);
				ppfAmountLookup.setInterestCredited(interestCredit);
				ppfAmountLookup.setClosingBalance(Math.round(closingBal));
			   // System.out.println("ppfAmountLookup.setClosingBalance "+ppfAmountLookup.getClosingBalance());
				ppfAmountLookup.setDaysToMaturity(daysToMaturity);
				if (dtLastDayOfMonth.after(cMaturityDate.getTime())) {
					break;

				}
				totalAmountDeposited = totalAmountDeposited + amountDeposited;
				ppfAmountLookupList.add(ppfAmountLookup);
				cInterimDate.add(Calendar.MONTH, 1);
				dtInterimDate = cInterimDate.getTime();
				strInterimDate = sdfOutput.format(dtInterimDate);
				amountDeposited = 0;
				amountDepositSerial = amountDepositSerial + 1;
				lookupSerialNumber++;
				//System.out.println("===============end loop");
			}
			ppfAmountDepositOutput.setAnnualAmountDeposited(deposit * rdDepositFreqInterestPaid);
			ppfAmountDepositOutput.setTotalAmountDeposited(
					totalAmountDiposited + ppfAmountDepositOutput.getAnnualAmountDeposited() * tenure);
			ppfAmountDepositOutput.setTotalInterestReceived(
					ppfAmountDepositOutput.getMaturityAmount() - ppfAmountDepositOutput.getTotalAmountDeposited());
			ppfAmountDepositOutput.setPpfFixedAmountLookupExtension(ppfAmountLookupList);
		    //System.out.println("ppfAmountDepositOutput.getMaturityAmount() "+ppfAmountDepositOutput.getMaturityAmount());
		    //System.out.println("ppfAmountDepositOutput.getMaturityDate() "+ppfAmountDepositOutput.getMaturityDate());
		} catch (Exception exp) {
			// TODO Auto-generated catch block
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_PPF_FIXED_AMOUNT,
					FinexaConstant.PRODUCT_CAL_PPF_FIXED_AMOUNT_CODE, "Failed to Calculate PPF Extension. Please see log for details.", exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return ppfAmountDepositOutput;
	}
	
	public double getPPFFixedAmountDepositDetails(double deposit,int tenure,Date depositDate,int depositfreq) {
		double totalAmountDeposited1 = 0;
		try {
		
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.HOUR, 0);
			Date currentDate = cal.getTime();
			Date varDepositDate = depositDate;
			cDepositDate.setTime(varDepositDate);
			cInterimDate.setTime(currentDate);
			String strInterimDate = sdfOutput.format(cInterimDate.getTime());
			strInterimDate = FinexaDateUtil.getLastDayOfTheMonth(strInterimDate);
			Date interimLastDayOfMonth = sdfOutput.parse(strInterimDate);
			
			int i= 0;
			while (true) {
				//System.out.println("====================start");
				//System.out.println("cDepositDate.getTime() "+cDepositDate.getTime());
				String strDepositDate = sdfOutput.format(cDepositDate.getTime());
				strRefDate = FinexaDateUtil.getLastDayOfTheMonth(strDepositDate);
				dtLastDayOfMonth = sdfOutput.parse(strRefDate);
				//System.out.println("dtLastDayOfMonth "+dtLastDayOfMonth);
				//System.out.println("interimLastDayOfMonth "+interimLastDayOfMonth);
				if (dtLastDayOfMonth.equals(interimLastDayOfMonth)) {
					totalAmountDeposited1 = totalAmountDeposited1 + deposit;
					break;
				}
				
				totalAmountDeposited1 = totalAmountDeposited1 + deposit;
				//System.out.println("totalAmountDeposited1 "+totalAmountDeposited1);
				
				//if(depositfreq == 12) //Monthly
				//{
				cDepositDate.add(Calendar.MONTH, 1);
				//}
				/*else if(depositfreq == 6) //Bi-Monthly
				{
				cDepositDate.add(Calendar.MONTH, 2);	
				}
				else if(depositfreq == 4) //Quarterly
				{
				cDepositDate.add(Calendar.MONTH, 3);	
				}
				else if(depositfreq == 3) //Triannualy
				{
				cDepositDate.add(Calendar.MONTH, 4);	
				}
				else if(depositfreq == 2) //half-yearly
				{
				cDepositDate.add(Calendar.MONTH, 6);	
				}
				else if(depositfreq == 1) //Annually
				{
				cDepositDate.add(Calendar.YEAR, 1);	
				}else {
					//---------//
				}*/
				if(i>(tenure*12)) {
					break;
				}
				
				i++;
			
				//System.out.println("====================end");
			}
			System.out.println("totalAmountDeposited1 "+totalAmountDeposited1);
		} catch (Exception exp) {
			exp.printStackTrace();
			// TODO Auto-generated catch block
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_PPF_FIXED_AMOUNT,
					FinexaConstant.PRODUCT_CAL_PPF_FIXED_AMOUNT_CODE, FinexaConstant.PRODUCT_CAL_PPF_FIXED_AMOUNT_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return totalAmountDeposited1;
	}
    
}
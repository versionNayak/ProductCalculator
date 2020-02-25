package com.finlabs.finexa.resources.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.formula.functions.FinanceLib;
import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.finlabs.finexa.resources.exception.FinexaBussinessException;
import com.finlabs.finexa.resources.model.AdvanceLoanCalLookup;
import com.finlabs.finexa.resources.model.AdvanceLoanCalculator;
import com.finlabs.finexa.resources.util.FinanceUtil;
import com.finlabs.finexa.resources.util.FinexaConstant;
import com.finlabs.finexa.resources.util.FinexaDateUtil;

public class AdvanceLoanCalculatorService {

	Calendar cDepositDate = Calendar.getInstance();
	Calendar cMaturityDate = Calendar.getInstance();
	Calendar cInterimDate = Calendar.getInstance();
	String strInterimDate = "";
	SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
	Calendar effectiveCAl = Calendar.getInstance();
	Calendar effectiveAppliedDateCAl = Calendar.getInstance();
	Calendar effectiveEndDateCAl = Calendar.getInstance();
	Calendar loanStartCal = Calendar.getInstance();
	Calendar lastEffectiveDate = Calendar.getInstance();
	SimpleDateFormat Daysdf = new SimpleDateFormat("dd-MMM-yyyy");
	AdvanceLoanCalLookup lastLoanCalResultRow = new AdvanceLoanCalLookup();
	double lastEndingBal = 0.0;

	public List<AdvanceLoanCalLookup> getAdvanceLoanCalculatorTable(JSONArray jsonArr, Date loanStartDate,
			double interestRate, int loanTenure, Date loanEndStartDate, double loanAmount, int origEmiCount,
			double origEmi) {

		String actionDate = "";
		String action = "";
		double begningBal = 0.0;
		double interestPay = 0.0;
		double principalPay = 0.0;
		double endingBal = 0.0;
		double totalPrincipalPay = 0.0;
		double totalInterestPay = 0.0;
		double prePaymentAmount = 0.0;
		double newEMi = 0;
		int emiChange = 0;
		int tenureChange = 0;
		int outstandTenure = 0;
		int originalEmiCount = 0;
		String newInterestRate = "";
		double originalEMI = 0.0;
		long newTenure = 0;
		String Startoutput = "";
		String fiscalYear = "";
		Date refDate = null;

		// effective date calculation
		SimpleDateFormat effectivesdf = new SimpleDateFormat("dd-MMM-yyyy");
		List<AdvanceLoanCalLookup> calulatorLookupDetails = new ArrayList<AdvanceLoanCalLookup>();
		try {

			// System.out.println(jsonArr.get(0));

			JSONParser parser = new JSONParser();

			int count = 1;
			for (int i = 0; i <= jsonArr.length() - 1; i++) {

				// input json values
				JSONObject jSonObj = (JSONObject) parser.parse((jsonArr.get(i).toString()));
				actionDate = (String) jSonObj.get("actionDate");
				action = (String) jSonObj.get("action");
				prePaymentAmount = Double.parseDouble((String) jSonObj.get("prepaymentAmount"));
				newInterestRate = (String) jSonObj.get("newInterestRate");
				// newEMi = Double.parseDouble((String) jSonObj.get("NewEmi"));
				emiChange = Integer.parseInt((String) jSonObj.get("changeInEmi"));
				tenureChange = Integer.parseInt((String) jSonObj.get("changeInTenure"));
				// outstandTenure = Integer.parseInt((String)
				// jSonObj.get("OutstandinTenures"));
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
				actionDate = formatter.format(sdfInput.parse(actionDate));
				Date dt = formatter.parse(actionDate);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(dt);
				loanStartCal.setTime(loanStartDate);
				// last day calculation for action date
				if (calendar.get(Calendar.DAY_OF_MONTH) < loanStartCal.get(Calendar.DAY_OF_MONTH)) {
					calendar.set(Calendar.DAY_OF_MONTH, 1);
					calendar.add(Calendar.DATE, -1);
				} else {
					calendar.add(Calendar.MONTH, 1);
					calendar.set(Calendar.DAY_OF_MONTH, 1);
					calendar.add(Calendar.DATE, -1);
				}

				Date lastDay = calendar.getTime();
				Calendar lastDayCal = Calendar.getInstance();
				lastDayCal.setTime(lastDay);
				Calendar effectiveDateCalendar = Calendar.getInstance();
				effectiveDateCalendar.setTime(lastDayCal.getTime());
				// ****Start Date for EMI payment
				// First action
				if (i == 0) {

					// last day calculation for action date
					effectiveDateCalendar.add(Calendar.DATE, loanStartCal.get(Calendar.DAY_OF_MONTH));
					Startoutput = Daysdf.format(loanStartCal.getTime());
					// ****No. of EMI*
					originalEmiCount = origEmiCount;
					originalEMI = origEmi;
					effectiveEndDateCAl.setTime(loanEndStartDate);

					effectiveCAl.setTime(effectiveDateCalendar.getTime());

					if (refDate == null) {
						refDate = loanStartCal.getTime();
					}

					while (refDate.compareTo(effectiveCAl.getTime()) != 0) {

						// First action checking
						if (count == 1) {
							// first row
							AdvanceLoanCalLookup calLookUp = new AdvanceLoanCalLookup();
							try {
								calLookUp.setRefDate(Daysdf.parse(Startoutput));
								calLookUp.setDisplayRefDate(Daysdf.format(calLookUp.getRefDate()));
							} catch (ParseException e) {
								e.printStackTrace();
							}

							fiscalYear = FinexaDateUtil.getFiscalYear(loanStartCal.getTime());
							calLookUp.setFinancialYear(fiscalYear);

							String strRefDate = effectivesdf.format(refDate);

							String strRefMonth = strRefDate;
							calLookUp.setReferenceMonth(strRefMonth.substring(3));

							calLookUp.setInstallmentNumber(count);

							begningBal = loanAmount;
							calLookUp.setBegningBal(begningBal);

							interestPay = begningBal * ((interestRate / 100) / 12);
							calLookUp.setInterestPayment((int) Math.round((interestPay)));

							if (begningBal == 0) {
								principalPay = 0;
							} else {
								principalPay = originalEMI - interestPay;
							}
							calLookUp.setPrincipalPayment((int) Math.round(principalPay));

							endingBal = (begningBal - principalPay - 0);
							calLookUp.setEndingBalance(endingBal);

							calLookUp.setPrePaymentAmount(0);

							calLookUp.setEmiAmount(originalEMI);

							calLookUp.setInterestRate(interestRate + "%");

							calLookUp.setNewInterestRate(0.0 + "%");

							calLookUp.setChangeInEMI(0);

							calLookUp.setNewEMI(0.0);

							calLookUp.setChangeInTenure(0);

							calLookUp.setNewTenure(originalEmiCount);

							calLookUp.setOutstandingTenure(originalEmiCount);

							totalPrincipalPay = principalPay;
							totalInterestPay = interestPay;
							calLookUp.setTotalPrincipalPaid(totalPrincipalPay);
							calLookUp.setTotalInterestPaid(totalInterestPay);
							calulatorLookupDetails.add(calLookUp);
							originalEmiCount = originalEmiCount - 1;
							count++;
							lastLoanCalResultRow = (AdvanceLoanCalLookup) calLookUp.clone();
						} else {
							loanStartCal.setTime(refDate);
							loanStartCal.add(Calendar.MONTH, 1);
							Startoutput = Daysdf.format(loanStartCal.getTime());
							refDate = loanStartCal.getTime();

							// for the first row of action effective value
							if (refDate.compareTo(effectiveCAl.getTime()) == 0) {
								AdvanceLoanCalLookup calLookUp = new AdvanceLoanCalLookup();
								effectiveAppliedDateCAl.setTime(refDate);
								effectiveAppliedDateCAl.add(Calendar.MONTH, 1);
								try {
									calLookUp.setRefDate(Daysdf.parse(Startoutput));
									refDate = calLookUp.getRefDate();
									calLookUp.setDisplayRefDate(Daysdf.format(calLookUp.getRefDate()));
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								String strRefDate = effectivesdf.format(refDate);
								String strRefMonth = strRefDate;
								calLookUp.setReferenceMonth(strRefMonth.substring(3));
								fiscalYear = FinexaDateUtil.getFiscalYear(refDate);
								calLookUp.setFinancialYear(fiscalYear);
								calLookUp.setInstallmentNumber(count);
								begningBal = endingBal;
								calLookUp.setBegningBal((int) Math.round(begningBal));

								// if change in interest rate
								if (action.equalsIgnoreCase("2")) {
									calLookUp.setPrePaymentAmount(0);
									calLookUp.setNewInterestRate(newInterestRate);
								} else {
									// add new prepayment value
									calLookUp.setNewInterestRate("0.0");
									calLookUp.setPrePaymentAmount(prePaymentAmount);
								}

								// Interest Rate
								if (calLookUp.getBegningBal() == 0) {
									calLookUp.setInterestRate(0.0 + "%");
								} else {
									if (Double.parseDouble(
											lastLoanCalResultRow.getNewInterestRate().replaceAll("%", "").trim()) > 0) {
										calLookUp.setInterestRate(lastLoanCalResultRow.getNewInterestRate() + "%");
									} else {
										calLookUp.setInterestRate(lastLoanCalResultRow.getInterestRate());
									}

								}

								// calLookUp.setNewInterestRate(0.0 + "%");

								interestPay = calLookUp.getBegningBal()
										* ((Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim())
												/ 100) / 12);
								calLookUp.setInterestPayment(interestPay);

								if (calLookUp.getBegningBal() == 0) {
									principalPay = 0;
								} else {
									principalPay = originalEMI - interestPay;
								}

								principalPay = (originalEMI - interestPay);
								calLookUp.setPrincipalPayment(principalPay);

								endingBal = (endingBal - principalPay - calLookUp.getPrePaymentAmount());
								calLookUp.setEndingBalance(endingBal);

								// new EMI amount
								if (lastLoanCalResultRow.getChangeInEMI() == 1
										&& (lastLoanCalResultRow.getPrePaymentAmount() > 0 || Double.parseDouble(
												calLookUp.getInterestRate().replaceAll("%", "").trim()) > 0)) {
									newEMi = FinanceLib.pmt(
											Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim())
													/ 100,
											lastLoanCalResultRow.getNewTenure()
													- (lastLoanCalResultRow.getInstallmentNumber()),
											-calLookUp.getBegningBal(), 0.0, false);

								} else {
									newEMi = 0;
								}
								calLookUp.setNewEMI(newEMi);

								// EMI AMount
								if (calLookUp.getBegningBal() == 0) {
									calLookUp.setEmiAmount(0);
								} else {
									if (calLookUp.getNewEMI() == 0) {
										calLookUp.setEmiAmount(lastLoanCalResultRow.getEmiAmount());
									} else {
										calLookUp.setEmiAmount(calLookUp.getNewEMI());
									}

								}

								calLookUp.setChangeInEMI(emiChange);
								calLookUp.setChangeInTenure(tenureChange);

								// new Tenure and outstanding tenure
								if (lastLoanCalResultRow.getChangeInTenure() > 0
										&& (lastLoanCalResultRow.getPrePaymentAmount() > 0 || !calLookUp
												.getInterestRate().equals(lastLoanCalResultRow.getInterestRate()))) {
									newTenure = (int) Math.round(FinanceLib.nper(
											((Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim())
													/ 100) / 12),
											-calLookUp.getEmiAmount(), calLookUp.getBegningBal(), 0.0, false)
											+ calLookUp.getInstallmentNumber());

									outstandTenure = (int) Math.round(FinanceLib.nper(
											((Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim())
													/ 100) / 12),
											-calLookUp.getEmiAmount(), calLookUp.getBegningBal(), 0.0, false));
								} else {
									newTenure = lastLoanCalResultRow.getNewTenure();
									outstandTenure = lastLoanCalResultRow.getOutstandingTenure() - 1;
								}
								calLookUp.setNewTenure(newTenure);
								calLookUp.setOutstandingTenure(outstandTenure);

								totalPrincipalPay = totalPrincipalPay + principalPay + calLookUp.getPrePaymentAmount();

								totalInterestPay = totalInterestPay + interestPay;

								calLookUp.setTotalPrincipalPaid((int) Math.round(totalPrincipalPay));
								calLookUp.setTotalInterestPaid((int) Math.round(totalInterestPay));
								calulatorLookupDetails.add(calLookUp);
								count++;
								lastEffectiveDate = effectiveCAl;
								lastLoanCalResultRow = (AdvanceLoanCalLookup) calLookUp.clone();
								lastLoanCalResultRow.setNewEMI(newEMi);
								lastLoanCalResultRow.setTotalPrincipalPaid(totalPrincipalPay);
								lastLoanCalResultRow.setTotalInterestPaid(totalInterestPay);

							} else {

								// if effective date doesn't match from 2nd row
								refDate = loanStartCal.getTime();
								AdvanceLoanCalLookup calLookUp = new AdvanceLoanCalLookup();
								try {
									calLookUp.setRefDate(Daysdf.parse(Startoutput));
									refDate = calLookUp.getRefDate();
									calLookUp.setDisplayRefDate(Daysdf.format(calLookUp.getRefDate()));
								} catch (Exception e) {
									e.printStackTrace();
								}

								if (!fiscalYear.isEmpty()) {
									calLookUp.setFinancialYear(fiscalYear);

								} else {
									fiscalYear = FinexaDateUtil.getFiscalYear(refDate);
									calLookUp.setFinancialYear(fiscalYear);

								}
								String strRefDateMonth = effectivesdf.format(refDate);
								calLookUp.setReferenceMonth(strRefDateMonth.substring(3));

								calLookUp.setInstallmentNumber(count);

								calLookUp.setBegningBal(lastLoanCalResultRow.getEndingBalance());

								// Interest Rate
								if (calLookUp.getBegningBal() == 0) {
									calLookUp.setInterestRate(0.0 + "%");
								} else {
									if (Double.parseDouble(
											lastLoanCalResultRow.getNewInterestRate().replaceAll("%", "").trim()) > 0) {
										calLookUp.setInterestRate(lastLoanCalResultRow.getNewInterestRate() + "%");
									} else {
										calLookUp.setInterestRate(lastLoanCalResultRow.getInterestRate());
									}

								}

								// calLookUp.setNewInterestRate(0.0 + "%");

								interestPay = calLookUp.getBegningBal()
										* ((Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim())
												/ 100) / 12);
								calLookUp.setInterestPayment(interestPay);

								if (calLookUp.getBegningBal() == 0) {
									principalPay = 0;
								} else {
									principalPay = originalEMI - interestPay;
								}

								principalPay = (originalEMI - interestPay);
								calLookUp.setPrincipalPayment(principalPay);

								endingBal = (endingBal - principalPay - 0);
								calLookUp.setEndingBalance(endingBal);

								// new EMI amount
								if (lastLoanCalResultRow.getChangeInEMI() == 1
										&& (lastLoanCalResultRow.getPrePaymentAmount() > 0 || Double.parseDouble(
												calLookUp.getInterestRate().replaceAll("%", "").trim()) > 0)) {
									newEMi = FinanceLib.pmt(
											Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim())
													/ 100,
											lastLoanCalResultRow.getNewTenure()
													- (lastLoanCalResultRow.getInstallmentNumber()),
											-calLookUp.getBegningBal(), 0.0, false);

								} else {
									newEMi = 0;
								}
								calLookUp.setNewEMI(newEMi);

								// EMI AMount
								if (calLookUp.getBegningBal() == 0) {
									calLookUp.setEmiAmount(0);
								} else {
									if (calLookUp.getNewEMI() == 0) {
										calLookUp.setEmiAmount(lastLoanCalResultRow.getEmiAmount());
									} else {
										calLookUp.setEmiAmount(calLookUp.getNewEMI());
									}

								}

								// Will always applied on effective date
								calLookUp.setPrePaymentAmount(0);
								calLookUp.setNewInterestRate("0.0");
								calLookUp.setNewEMI(0.0);
								calLookUp.setChangeInEMI(0);
								calLookUp.setChangeInTenure(0);

								// new Tenure and outstanding tenure
								if (lastLoanCalResultRow.getChangeInTenure() > 0
										&& (lastLoanCalResultRow.getPrePaymentAmount() > 0 || !calLookUp
												.getInterestRate().equals(lastLoanCalResultRow.getInterestRate()))) {
									newTenure = (int) Math.round(FinanceLib.nper(
											((Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim())
													/ 100) / 12),
											-calLookUp.getEmiAmount(), calLookUp.getBegningBal(), 0.0, false)
											+ calLookUp.getInstallmentNumber());

									outstandTenure = (int) Math.round(FinanceLib.nper(
											((Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim())
													/ 100) / 12),
											-calLookUp.getEmiAmount(), calLookUp.getBegningBal(), 0.0, false));
								} else {
									newTenure = lastLoanCalResultRow.getNewTenure();
									outstandTenure = lastLoanCalResultRow.getOutstandingTenure() - 1;
								}
								calLookUp.setNewTenure(newTenure);
								calLookUp.setOutstandingTenure(outstandTenure);

								totalPrincipalPay = totalPrincipalPay + principalPay + 0;

								totalInterestPay = totalInterestPay + interestPay;

								calLookUp.setTotalPrincipalPaid((int) Math.round(totalPrincipalPay));
								calLookUp.setTotalInterestPaid((int) Math.round(totalInterestPay));
								calulatorLookupDetails.add(calLookUp);
								count++;
								lastEffectiveDate = effectiveCAl;
								lastLoanCalResultRow = (AdvanceLoanCalLookup) calLookUp.clone();
								lastLoanCalResultRow.setNewEMI(newEMi);
								lastLoanCalResultRow.setTotalPrincipalPaid(totalPrincipalPay);
								lastLoanCalResultRow.setTotalInterestPaid(totalInterestPay);
							}

						}
					}
				} else {

					// new row begins for multiple inputs

					effectiveDateCalendar.add(Calendar.DATE, loanStartCal.get(Calendar.DAY_OF_MONTH));

					effectiveCAl.setTime(effectiveDateCalendar.getTime());

					// count = 1;
					while (refDate.compareTo(effectiveCAl.getTime()) != 0) {
						//System.out.println(effectiveCAl.getTime());
						//System.out.println(refDate);
						loanStartCal.setTime(refDate);
						loanStartCal.add(Calendar.MONTH, 1);
						Startoutput = Daysdf.format(loanStartCal.getTime());
						refDate = loanStartCal.getTime();
						if (refDate.compareTo(effectiveCAl.getTime()) == 0) {
							AdvanceLoanCalLookup calLookUp = new AdvanceLoanCalLookup();
							effectiveAppliedDateCAl.setTime(refDate);
							effectiveAppliedDateCAl.add(Calendar.MONTH, 1);
							try {
								calLookUp.setRefDate(Daysdf.parse(Startoutput));
								calLookUp.setDisplayRefDate(Daysdf.format(calLookUp.getRefDate()));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							String strRefDate = effectivesdf.format(refDate);
							String strRefMonth = strRefDate;
							calLookUp.setReferenceMonth(strRefMonth.substring(3));
							fiscalYear = FinexaDateUtil.getFiscalYear(refDate);
							calLookUp.setFinancialYear(fiscalYear);
							calLookUp.setInstallmentNumber(count);
							begningBal = endingBal;
							calLookUp.setBegningBal((int) Math.round(begningBal));

							// if change in interest rate
							if (action.equalsIgnoreCase("2")) {
								calLookUp.setPrePaymentAmount(0);
								calLookUp.setNewInterestRate(newInterestRate);
							} else {
								// add new prepayment value
								calLookUp.setNewInterestRate("0.0");
								calLookUp.setPrePaymentAmount(prePaymentAmount);
							}

							// Interest Rate
							if (calLookUp.getBegningBal() == 0) {
								calLookUp.setInterestRate(0.0 + "%");
							} else {
								if (Double.parseDouble(
										lastLoanCalResultRow.getNewInterestRate().replaceAll("%", "").trim()) > 0) {
									calLookUp.setInterestRate(lastLoanCalResultRow.getNewInterestRate() + "%");
								} else {
									calLookUp.setInterestRate(lastLoanCalResultRow.getInterestRate());
								}

							}

							// calLookUp.setNewInterestRate(0.0 + "%");

							interestPay = calLookUp.getBegningBal()
									* ((Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim())
											/ 100) / 12);
							calLookUp.setInterestPayment(interestPay);

							// new EMI amount
							if (lastLoanCalResultRow.getChangeInEMI() == 1
									&& (lastLoanCalResultRow.getPrePaymentAmount() > 0 || Double
											.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim()) > 0)) {
								newEMi = FinanceLib.pmt(
										Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim())
												/ 100,
										lastLoanCalResultRow.getNewTenure()
												- (lastLoanCalResultRow.getInstallmentNumber()),
										-calLookUp.getBegningBal(), 0.0, false);

							} else {
								newEMi = 0;
							}
							calLookUp.setNewEMI(newEMi);

							// EMI AMount
							if (calLookUp.getBegningBal() == 0) {
								calLookUp.setEmiAmount(0);
							} else {
								if (calLookUp.getNewEMI() == 0) {
									calLookUp.setEmiAmount(lastLoanCalResultRow.getEmiAmount());
								} else {
									calLookUp.setEmiAmount(calLookUp.getNewEMI());
								}

							}

							if (calLookUp.getBegningBal() == 0) {
								principalPay = 0;
							} else {
								principalPay = calLookUp.getEmiAmount() - calLookUp.getInterestPayment();
							}

							calLookUp.setPrincipalPayment(principalPay);

							endingBal = (calLookUp.getBegningBal() - calLookUp.getPrincipalPayment()
									- calLookUp.getPrePaymentAmount());
							calLookUp.setEndingBalance(endingBal);

							calLookUp.setChangeInEMI(emiChange);
							calLookUp.setChangeInTenure(tenureChange);

							// new Tenure and outstanding tenure
							if (lastLoanCalResultRow.getChangeInTenure() > 0
									&& (lastLoanCalResultRow.getPrePaymentAmount() > 0 || !calLookUp.getInterestRate()
											.equals(lastLoanCalResultRow.getInterestRate()))) {
								newTenure = (int) Math.round(FinanceLib.nper(
										((Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim())
												/ 100) / 12),
										-calLookUp.getEmiAmount(), calLookUp.getBegningBal(), 0.0, false)
										+ calLookUp.getInstallmentNumber());

								outstandTenure = (int) Math.round(FinanceLib.nper(
										((Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim())
												/ 100) / 12),
										-calLookUp.getEmiAmount(), calLookUp.getBegningBal(), 0.0, false));
							} else {
								newTenure = lastLoanCalResultRow.getNewTenure();
								outstandTenure = lastLoanCalResultRow.getOutstandingTenure() - 1;
							}
							calLookUp.setNewTenure(newTenure);
							calLookUp.setOutstandingTenure(outstandTenure);

							totalPrincipalPay = totalPrincipalPay + principalPay + calLookUp.getPrePaymentAmount();

							totalInterestPay = totalInterestPay + interestPay;

							calLookUp.setTotalPrincipalPaid((int) Math.round(totalPrincipalPay));
							calLookUp.setTotalInterestPaid((int) Math.round(totalInterestPay));
							calulatorLookupDetails.add(calLookUp);
							count++;
							lastEffectiveDate = effectiveCAl;
							lastLoanCalResultRow = (AdvanceLoanCalLookup) calLookUp.clone();
							lastLoanCalResultRow.setNewEMI(newEMi);
							lastLoanCalResultRow.setTotalPrincipalPaid(totalPrincipalPay);
							lastLoanCalResultRow.setTotalInterestPaid(totalInterestPay);
						} else {

							// if effective date doesn't match from 2nd row
							refDate = loanStartCal.getTime();
							AdvanceLoanCalLookup calLookUp = new AdvanceLoanCalLookup();
							try {
								calLookUp.setRefDate(Daysdf.parse(Startoutput));
								refDate = calLookUp.getRefDate();
								calLookUp.setDisplayRefDate(Daysdf.format(calLookUp.getRefDate()));
							} catch (Exception e) {
								e.printStackTrace();
							}

							if (!fiscalYear.isEmpty()) {
								calLookUp.setFinancialYear(fiscalYear);

							} else {
								fiscalYear = FinexaDateUtil.getFiscalYear(refDate);
								calLookUp.setFinancialYear(fiscalYear);

							}
							String strRefDateMonth = effectivesdf.format(refDate);
							calLookUp.setReferenceMonth(strRefDateMonth.substring(3));

							calLookUp.setInstallmentNumber(count);

							calLookUp.setBegningBal(lastLoanCalResultRow.getEndingBalance());

							// Interest Rate
							if (calLookUp.getBegningBal() == 0) {
								calLookUp.setInterestRate(0.0 + "%");
							} else {
								if (Double.parseDouble(
										lastLoanCalResultRow.getNewInterestRate().replaceAll("%", "").trim()) > 0) {
									calLookUp.setInterestRate(lastLoanCalResultRow.getNewInterestRate() + "%");
								} else {
									calLookUp.setInterestRate(lastLoanCalResultRow.getInterestRate());
								}

							}

							// calLookUp.setNewInterestRate(0.0 + "%");

							interestPay = calLookUp.getBegningBal()
									* ((Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim())
											/ 100) / 12);
							calLookUp.setInterestPayment(interestPay);

							// new EMI amount
							if (lastLoanCalResultRow.getChangeInEMI() == 1
									&& (lastLoanCalResultRow.getPrePaymentAmount() > 0 || Double
											.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim()) > 0)) {
								newEMi = FinanceLib.pmt(
										(Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim())
												/ 100) / 12,
										lastLoanCalResultRow.getNewTenure()
												- (lastLoanCalResultRow.getInstallmentNumber()),
										-calLookUp.getBegningBal(), 0.0, false);

							} else {
								newEMi = 0;
							}
							calLookUp.setNewEMI(newEMi);

							// EMI AMount
							if (calLookUp.getBegningBal() == 0) {
								calLookUp.setEmiAmount(0);
							} else {
								if (calLookUp.getNewEMI() == 0) {
									calLookUp.setEmiAmount(lastLoanCalResultRow.getEmiAmount());
								} else {
									calLookUp.setEmiAmount(calLookUp.getNewEMI());
								}

							}

							if (calLookUp.getBegningBal() == 0) {
								principalPay = 0;
							} else {
								principalPay = calLookUp.getEmiAmount() - calLookUp.getInterestPayment();
							}

							calLookUp.setPrincipalPayment(principalPay);

							endingBal = (calLookUp.getBegningBal() - calLookUp.getPrincipalPayment()
									- calLookUp.getPrePaymentAmount());
							calLookUp.setEndingBalance(endingBal);

							// Will always applied on effective date
							calLookUp.setPrePaymentAmount(0);
							calLookUp.setNewInterestRate("0.0");

							calLookUp.setChangeInEMI(0);
							calLookUp.setChangeInTenure(0);

							// new Tenure and outstanding tenure
							if (lastLoanCalResultRow.getChangeInTenure() > 0
									&& (lastLoanCalResultRow.getPrePaymentAmount() > 0 || !calLookUp.getInterestRate()
											.equals(lastLoanCalResultRow.getInterestRate()))) {
								newTenure = (int) Math.round(FinanceLib.nper(
										((Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim())
												/ 100) / 12),
										-calLookUp.getEmiAmount(), calLookUp.getBegningBal(), 0.0, false)
										+ lastLoanCalResultRow.getInstallmentNumber());

								outstandTenure = (int) Math.round(FinanceLib.nper(
										((Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim())
												/ 100) / 12),
										-calLookUp.getEmiAmount(), calLookUp.getBegningBal(), 0.0, false));
							} else {
								newTenure = lastLoanCalResultRow.getNewTenure();
								outstandTenure = lastLoanCalResultRow.getOutstandingTenure() - 1;
							}
							calLookUp.setNewTenure(newTenure);
							calLookUp.setOutstandingTenure(outstandTenure);

							totalPrincipalPay = totalPrincipalPay + principalPay + 0;

							totalInterestPay = totalInterestPay + interestPay;

							calLookUp.setTotalPrincipalPaid((int) Math.round(totalPrincipalPay));
							calLookUp.setTotalInterestPaid((int) Math.round(totalInterestPay));
							calulatorLookupDetails.add(calLookUp);
							count++;
							lastEffectiveDate = effectiveCAl;
							lastLoanCalResultRow = (AdvanceLoanCalLookup) calLookUp.clone();
							lastLoanCalResultRow.setNewEMI(newEMi);
							lastLoanCalResultRow.setTotalPrincipalPaid(totalPrincipalPay);
							lastLoanCalResultRow.setTotalInterestPaid(totalInterestPay);
						}

					}

				}

				if (i == jsonArr.length() - 1) {

					while (lastLoanCalResultRow.getOutstandingTenure() > 1) {
						AdvanceLoanCalLookup calLookUp = new AdvanceLoanCalLookup();
						effectiveAppliedDateCAl.setTime(refDate);
						effectiveAppliedDateCAl.add(Calendar.MONTH, 1);
						loanStartCal.setTime(refDate);
						loanStartCal.add(Calendar.MONTH, 1);
						Startoutput = Daysdf.format(loanStartCal.getTime());
						refDate = loanStartCal.getTime();
						try {
							calLookUp.setRefDate(Daysdf.parse(Startoutput));
							refDate = calLookUp.getRefDate();
							calLookUp.setDisplayRefDate(Daysdf.format(calLookUp.getRefDate()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						String strRefDate = effectivesdf.format(refDate);
						String strRefMonth = strRefDate;
						calLookUp.setReferenceMonth(strRefMonth.substring(3));
						fiscalYear = FinexaDateUtil.getFiscalYear(refDate);
						calLookUp.setFinancialYear(fiscalYear);
						calLookUp.setInstallmentNumber(count);
						begningBal = endingBal;
						calLookUp.setBegningBal(begningBal);

						calLookUp.setNewInterestRate("0.0");
						calLookUp.setPrePaymentAmount(0.0);

						// Interest Rate
						if (calLookUp.getBegningBal() == 0) {
							calLookUp.setInterestRate(0.0 + "%");
						} else {
							if (Double.parseDouble(
									lastLoanCalResultRow.getNewInterestRate().replaceAll("%", "").trim()) > 0) {
								calLookUp.setInterestRate(lastLoanCalResultRow.getNewInterestRate() + "%");
							} else {
								calLookUp.setInterestRate(lastLoanCalResultRow.getInterestRate());
							}

						}

						// calLookUp.setNewInterestRate(0.0 + "%");

						interestPay = calLookUp.getBegningBal()
								* ((Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim()) / 100)
										/ 12);
						calLookUp.setInterestPayment(interestPay);

						// new EMI amount
						if (lastLoanCalResultRow.getChangeInEMI() == 1
								&& (lastLoanCalResultRow.getPrePaymentAmount() > 0 || Double
										.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim()) > 0)) {
							newEMi = FinanceLib.pmt(
									(Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim()) / 100)
											/ 12,
									lastLoanCalResultRow.getNewTenure() - (lastLoanCalResultRow.getInstallmentNumber()),
									-calLookUp.getBegningBal(), 0.0, false);

						} else {
							newEMi = 0;
						}
						calLookUp.setNewEMI(newEMi);

						// EMI AMount
						if (calLookUp.getBegningBal() == 0) {
							calLookUp.setEmiAmount(0);
						} else {
							if (calLookUp.getNewEMI() == 0) {
								calLookUp.setEmiAmount(lastLoanCalResultRow.getEmiAmount());
							} else {
								calLookUp.setEmiAmount(calLookUp.getNewEMI());
							}

						}

						if (calLookUp.getBegningBal() == 0) {
							principalPay = 0;
						} else {
							principalPay = calLookUp.getEmiAmount() - calLookUp.getInterestPayment();
						}

						calLookUp.setPrincipalPayment(principalPay);

						endingBal = (calLookUp.getBegningBal() - calLookUp.getPrincipalPayment()
								- calLookUp.getPrePaymentAmount());
						calLookUp.setEndingBalance(endingBal);

						calLookUp.setChangeInEMI(0);
						calLookUp.setChangeInTenure(0);

						// new Tenure and outstanding tenure
						if (lastLoanCalResultRow.getChangeInTenure() > 0
								&& (lastLoanCalResultRow.getPrePaymentAmount() > 0 || !calLookUp.getInterestRate()
										.equals(lastLoanCalResultRow.getInterestRate()))) {
							newTenure = Math.round(FinanceLib.nper(
									((Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim()) / 100)
											/ 12),
									-calLookUp.getEmiAmount(), calLookUp.getBegningBal(), 0.0, false)
									+ lastLoanCalResultRow.getInstallmentNumber());

							outstandTenure = (int) Math.round(FinanceLib.nper(
									((Double.parseDouble(calLookUp.getInterestRate().replaceAll("%", "").trim()) / 100)
											/ 12),
									-calLookUp.getEmiAmount(), calLookUp.getBegningBal(), 0.0, false));
						} else {
							newTenure = lastLoanCalResultRow.getNewTenure();
							outstandTenure = lastLoanCalResultRow.getOutstandingTenure() - 1;
						}
						calLookUp.setNewTenure(newTenure);
						calLookUp.setOutstandingTenure(outstandTenure);

						totalPrincipalPay = totalPrincipalPay + principalPay + calLookUp.getPrePaymentAmount();

						totalInterestPay = totalInterestPay + interestPay;

						calLookUp.setTotalPrincipalPaid(totalPrincipalPay);
						calLookUp.setTotalInterestPaid(totalInterestPay);
						calulatorLookupDetails.add(calLookUp);
						count++;
						lastEffectiveDate = effectiveCAl;
						lastLoanCalResultRow = (AdvanceLoanCalLookup) calLookUp.clone();
						lastLoanCalResultRow.setNewEMI(newEMi);
						lastLoanCalResultRow.setTotalPrincipalPaid(totalPrincipalPay);
						lastLoanCalResultRow.setTotalInterestPaid(totalInterestPay);
					} // break; }

				}
			}

		} catch (Exception exp) {
			FinexaBussinessException finexaBuss = new FinexaBussinessException(FinexaConstant.PRODUCT_CAL_ADVANCE_LOAN,
					FinexaConstant.PRODUCT_CAL_ADVANCE_LOAN_CODE, FinexaConstant.PRODUCT_CAL_ADVANCE_LOAN_DESC, exp);
			FinexaBussinessException.logFinexaBusinessException(finexaBuss);
		}
		return calulatorLookupDetails;
	}

	public AdvanceLoanCalculator calculateAdvanceCalculator(int loanType, double loanAmount, double interestRate,
			int loanTenure, Date loanStartDate) throws RuntimeException {
		AdvanceLoanCalculator advCalLoanCal = new AdvanceLoanCalculator();
		int originalEmiCount = loanTenure * 12;
		double originalEMI = (FinanceUtil.pmt(((interestRate / 100) / 12), loanTenure * 12, -loanAmount, 0, false));
		effectiveEndDateCAl.setTime(loanStartDate);
		effectiveEndDateCAl.add(Calendar.MONTH, (int) (originalEmiCount - 1));
		advCalLoanCal.setOriginalEMiCount(originalEmiCount);
		advCalLoanCal.setOriginalEmiAmount(originalEMI);
		advCalLoanCal.setLoanEndDate(effectiveEndDateCAl.getTime());
		advCalLoanCal.setLoanType(loanType);
		advCalLoanCal.setLoanAmount(loanAmount);
		advCalLoanCal.setInterestRate(interestRate);
		advCalLoanCal.setLoanTenure(loanTenure);
		advCalLoanCal.setLoanStartDate(loanStartDate);
		return advCalLoanCal;
	}
}

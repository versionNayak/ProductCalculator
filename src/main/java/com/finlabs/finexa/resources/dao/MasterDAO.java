package com.finlabs.finexa.resources.dao;

import java.util.Date;
import java.util.List;

import com.finlabs.finexa.resources.model.MasterEPF2Calculator;
import com.finlabs.finexa.resources.model.MasterEquityCalculator;
import com.finlabs.finexa.resources.model.MasterKisanVikasPatra;
import com.finlabs.finexa.resources.model.MasterMutualFundLumpsumSip;
import com.finlabs.finexa.resources.model.MasterPONSC;
import com.finlabs.finexa.resources.model.MasterPORecurringDeposit;
import com.finlabs.finexa.resources.model.MasterPPFFixedAmountDeposit;
import com.finlabs.finexa.resources.model.MasterPostOfficeMonthlyIncomeScheme;
import com.finlabs.finexa.resources.model.MasterSeniorCitizenSavingScheme;
import com.finlabs.finexa.resources.model.MasterSukanyaSamriddhiScheme;

public interface MasterDAO {
	public List<MasterSukanyaSamriddhiScheme> getSukanyaSamridhiInterestRate();

	public List<MasterSeniorCitizenSavingScheme> getSeniorCitizenSavingSchemeInterestRates();

	public List<MasterEPF2Calculator> getMasterEPF2CalculatorInterestRates();

	public List<MasterPPFFixedAmountDeposit> getMasterPPFFixedAmountDepositInterestRates();

	public double getMasterAtalPensionYojanaContribution(int entryAge, int vestingPeriod, int frequency,
			double monthlyPension);

	public double getAtalPensionYojanaCorpusValue(double monthlyPension);

	public List<MasterPostOfficeMonthlyIncomeScheme> getMasterPOMISRates();

	public List<MasterPORecurringDeposit> getMasterPORecurringDepositRates();

	public List<MasterPONSC> getMasterPONSCRates();

	public double getMasterPONSCRates(Date currentDate, String assetClassType);

	public double getMasterPONSCIncomeCagr(Date currentDate, String incomeCategory);

	public List<MasterKisanVikasPatra> getKvpInterestRates();

	public MasterKisanVikasPatra getKvpTerm(Date currentDate);

	public MasterKisanVikasPatra getKvpCompoundFreq(Date currentDate);

	public List<MasterMutualFundLumpsumSip>  getMasterMutualFundLumpsumSipNavs(Date startdate, Date enddate,String isin);
	
	public Date getMaxDateByIsin(String isin);
	
	public MasterMutualFundLumpsumSip getNavByDateAndIsin(Date date, String isin);
	
	public String getIsinByRTACode (String schemeRTACode);

	public Double getMasterMutualFundLumpsumSipNavsLastValue();

	public String getFrequencyDescById(int frequencyId);

	public double[] getClientBasicDAById(int investMentId);

	public double getMasterEPFRates(Date date);

	public List<MasterEquityCalculator> getMasterEquityCalculatorClosingPrice(String isin);

	public double getMasterEquityCalculatorClosingPrice(String isin, Date date);

	
}
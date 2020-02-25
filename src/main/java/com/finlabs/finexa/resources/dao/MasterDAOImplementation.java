package com.finlabs.finexa.resources.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.finlabs.finexa.resources.util.DBUtil;

public class MasterDAOImplementation implements MasterDAO {
	private Connection conn;

	public List<MasterSukanyaSamriddhiScheme> getSukanyaSamridhiInterestRate() {
		List<MasterSukanyaSamriddhiScheme> masterSukanyaSchemeList = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			String query = "SELECT * from masterSukanyaSamriddhiInterestRate msi  ";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				MasterSukanyaSamriddhiScheme masterSukanyaScheme = new MasterSukanyaSamriddhiScheme();
				masterSukanyaScheme.setStartDate(resultSet.getDate("startDate"));
				masterSukanyaScheme.setEndDate(resultSet.getDate("endDate"));
				masterSukanyaScheme.setInterestRate(resultSet.getDouble("interestRate"));
				masterSukanyaSchemeList.add(masterSukanyaScheme);
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return masterSukanyaSchemeList;
	}

	@Override
	public List<MasterSeniorCitizenSavingScheme> getSeniorCitizenSavingSchemeInterestRates() {
		List<MasterSeniorCitizenSavingScheme> masterSeniorCitizenSavingSchemeList = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			String query = "SELECT * from masterSCSSInterestRate mscss";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				MasterSeniorCitizenSavingScheme masterSeniorCitizenScheme = new MasterSeniorCitizenSavingScheme();
				masterSeniorCitizenScheme.setStartDate(resultSet.getDate("depositDateFrom"));
				masterSeniorCitizenScheme.setEndDate(resultSet.getDate("depositDateTo"));
				masterSeniorCitizenScheme.setInterestRate(resultSet.getDouble("interestRate"));
				masterSeniorCitizenSavingSchemeList.add(masterSeniorCitizenScheme);
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return masterSeniorCitizenSavingSchemeList;
	}

	@Override
	public List<MasterEPF2Calculator> getMasterEPF2CalculatorInterestRates() {
		List<MasterEPF2Calculator> masterSeniorCitizenSavingSchemeList = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			String query = "SELECT * FROM masterEPFInterestRate mepi";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				MasterEPF2Calculator masterEfp2Cal = new MasterEPF2Calculator();
				masterEfp2Cal.setStartDate(resultSet.getDate("fromDate"));
				masterEfp2Cal.setEndDate(resultSet.getDate("toDate"));
				masterEfp2Cal.setInterestRate(resultSet.getDouble("interestRate"));
				masterSeniorCitizenSavingSchemeList.add(masterEfp2Cal);
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return masterSeniorCitizenSavingSchemeList;
	}
	@Override
	public double getMasterEPFRates(Date currentDate) {
		MasterEPF2Calculator masterEfp2Cal = new MasterEPF2Calculator();
		double interestRate = 0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateInString = dateFormat.format(currentDate);
		PreparedStatement preparedStatement = null;
		try {
			conn = DBUtil.getConnection();
			String query = "SELECT * FROM masterEPFInterestRate mepi WHERE ? BETWEEN  mepi.fromDate AND mepi.toDate";
			preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, java.sql.Date.valueOf(currentDateInString));
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				masterEfp2Cal.setInterestRate(resultSet.getDouble("interestRate"));
				interestRate = masterEfp2Cal.getInterestRate();
			}
			resultSet.close();
			preparedStatement.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return interestRate;
	}

	@Override
	public List<MasterPPFFixedAmountDeposit> getMasterPPFFixedAmountDepositInterestRates() {
		List<MasterPPFFixedAmountDeposit> mastermasterPPfFDList = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			String query = "select * from masterPPFInterestRate";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				MasterPPFFixedAmountDeposit masterPPfFD = new MasterPPFFixedAmountDeposit();
				masterPPfFD.setStartDate(resultSet.getDate("validFromDate"));
				masterPPfFD.setEndDate(resultSet.getDate("validToDate"));
				masterPPfFD.setInterestRate(resultSet.getDouble("interestRate"));
				mastermasterPPfFDList.add(masterPPfFD);
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return mastermasterPPfFDList;
	}

	@Override
	public double getMasterAtalPensionYojanaContribution(int entryAge, int vestingPeriod, int frequency,
			double monthlyPension) {
		double masterAtalPensionContr = 0.0;
		try {
			conn = DBUtil.getConnection();
			String query = "SELECT  mac.monthlyContribution  FROM masterAPYContributionChart mac WHERE mac.entryAge=? and mac.vestingPeriod=? and mac.frequency=? and mac.monthlyPension=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, entryAge);
			preparedStatement.setInt(2, vestingPeriod);
			preparedStatement.setInt(3, frequency);
			preparedStatement.setDouble(4, monthlyPension);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				masterAtalPensionContr = resultSet.getDouble("monthlyContribution");
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return masterAtalPensionContr;
	}

	@Override
	public double getAtalPensionYojanaCorpusValue(double monthlyPension) {
		double corpus = 0.0;
		try {
			conn = DBUtil.getConnection();
			String query = "SELECT map.corpus FROM  masterAPYMonthlyPensionCorpus map WHERE map.monthlyPension=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);

			preparedStatement.setDouble(1, monthlyPension);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				corpus = resultSet.getDouble("corpus");
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return corpus;
	}

	@Override
	public List<MasterPostOfficeMonthlyIncomeScheme> getMasterPOMISRates() {
		List<MasterPostOfficeMonthlyIncomeScheme> mastermasterPostOfficeMonthlyList = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			String query = "select * from masterPOMIS";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				MasterPostOfficeMonthlyIncomeScheme masterPostOffice = new MasterPostOfficeMonthlyIncomeScheme();
				masterPostOffice.setStartDate(resultSet.getDate("depositDateFrom"));
				masterPostOffice.setEndDate(resultSet.getDate("depositDateTo"));
				masterPostOffice.setInterestRate(resultSet.getDouble("interestRate"));
				mastermasterPostOfficeMonthlyList.add(masterPostOffice);
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return mastermasterPostOfficeMonthlyList;
	}

	@Override
	public List<MasterPORecurringDeposit> getMasterPORecurringDepositRates() {
		List<MasterPORecurringDeposit> masterMasterPORecurringDepositList = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			String query = "select * from masterPORD";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				MasterPORecurringDeposit masterPORecurringDeposit = new MasterPORecurringDeposit();
				masterPORecurringDeposit.setStartDate(resultSet.getDate("depositDateFrom"));
				masterPORecurringDeposit.setEndDate(resultSet.getDate("depositDateTo"));
				masterPORecurringDeposit.setInterestRate(resultSet.getDouble("interestRate"));
				masterMasterPORecurringDepositList.add(masterPORecurringDeposit);
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return masterMasterPORecurringDepositList;
	}

	@Override
	public List<MasterPONSC> getMasterPONSCRates() {
		List<MasterPONSC> masterMasterPORecurringDepositList = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			String query = "select * from masterNSCInterestRate";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				MasterPONSC masterPORecurringDeposit = new MasterPONSC();
				masterPORecurringDeposit.setPeriodStart(resultSet.getDate("periodStart"));
				masterPORecurringDeposit.setPeriodEnd(resultSet.getDate("periodEnd"));
				masterPORecurringDeposit.setInterestRate(resultSet.getDouble("interestRate"));
				masterMasterPORecurringDepositList.add(masterPORecurringDeposit);
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return masterMasterPORecurringDepositList;
	}

	@Override
	public double getMasterPONSCRates(Date currentDate, String assetClassType) {
		double interestRate = 0.0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateInString = dateFormat.format(currentDate);
		try {
			conn = DBUtil.getConnection();
			String query = "SELECT  mne.assetClassEExpectedReturns FROM masterNPSAssetClassExpectedReturn mne WHERE ? BETWEEN  mne.fromDate AND mne.toDate AND mne.assetClassType=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, java.sql.Date.valueOf(currentDateInString));
			preparedStatement.setString(2, assetClassType);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				interestRate = resultSet.getDouble("assetClassEExpectedReturns");
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return interestRate;
	}

	@Override
	public double getMasterPONSCIncomeCagr(Date currentDate, String incomeCategory) {
		double cagr = 0.0;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateInString = dateFormat.format(currentDate);
		try {
			conn = DBUtil.getConnection();
			String query = "SELECT  mig.cagr FROM masterIncomeGrowthRate mig WHERE ? BETWEEN  mig.fromDate AND mig.toDate AND mig.incomeCategory=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, java.sql.Date.valueOf(currentDateInString));
			preparedStatement.setString(2, incomeCategory);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				cagr = resultSet.getDouble("cagr");
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return cagr;
	}

	@Override
	public List<MasterKisanVikasPatra> getKvpInterestRates() {
		List<MasterKisanVikasPatra> masterMasterKisanVikasPatraList = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			String query = "select * from masterKVPInterestRate";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				MasterKisanVikasPatra kisanVikasPatra = new MasterKisanVikasPatra();
				kisanVikasPatra.setDepositFromDate(resultSet.getDate("depositFromDate"));
				kisanVikasPatra.setDepositToDate(resultSet.getDate("depositToDate"));
				// kisanVikasPatra.setInvestmentPeriod(resultSet.getDouble("investmentPeriodInYears"));
				kisanVikasPatra.setInterestRate(resultSet.getDouble("interestRate"));
				masterMasterKisanVikasPatraList.add(kisanVikasPatra);
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return masterMasterKisanVikasPatraList;
	}

	@Override
	public MasterKisanVikasPatra getKvpTerm(Date currentDate) {
		MasterKisanVikasPatra masterKvp = new MasterKisanVikasPatra();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateInString = dateFormat.format(currentDate);
		try {
			conn = DBUtil.getConnection();
			String query = "SELECT mkt.termYears,mkt.termMonths FROM masterKVPTerm mkt where ? BETWEEN mkt.depositFromDate AND mkt.depositToDate";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, java.sql.Date.valueOf(currentDateInString));
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				masterKvp.setYear(resultSet.getInt("termYears"));
				masterKvp.setMonth(resultSet.getInt("termMonths"));
			}
			resultSet.close();
			preparedStatement.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return masterKvp;
	}

	@Override
	public MasterKisanVikasPatra getKvpCompoundFreq(Date currentDate) {
		MasterKisanVikasPatra masterKvp = new MasterKisanVikasPatra();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDateInString = dateFormat.format(currentDate);
		try {
			conn = DBUtil.getConnection();
			String query = "SELECT mkcf.compoundingFrequency FROM masterKVPCompoundingFrequency mkcf where ? BETWEEN mkcf.depositFromDate AND mkcf.depositToDate";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setDate(1, java.sql.Date.valueOf(currentDateInString));
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				masterKvp.setCompundFreq(resultSet.getInt("compoundingFrequency"));
				//System.out.println("masterKvp "+masterKvp.getCompundFreq());
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return masterKvp;
	}

	@Override
	public List<MasterMutualFundLumpsumSip> getMasterMutualFundLumpsumSipNavs(Date startdate, Date endate,
			String isin) {
		List<MasterMutualFundLumpsumSip> masterMutualFundLumpsumSipList = new ArrayList<>();
		try {
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String startDateInString = dateFormat.format(startdate);
			String endDateInString = dateFormat.format(endate);
			conn = DBUtil.getConnection();
			String query = "select * from masterMFDailyNAV WHERE isin = ? and date between ? and ?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, isin);
			preparedStatement.setDate(2, java.sql.Date.valueOf(startDateInString));
			preparedStatement.setDate(3, java.sql.Date.valueOf(endDateInString));
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				MasterMutualFundLumpsumSip masterMutualFundLumpsumSip = new MasterMutualFundLumpsumSip();
				masterMutualFundLumpsumSip.setDate(resultSet.getDate("date"));
				masterMutualFundLumpsumSip.setIsin(resultSet.getString("isin"));
				masterMutualFundLumpsumSip.setNav(resultSet.getDouble("nav"));
				//System.out.println(resultSet.getDate("date")+" "+resultSet.getString("isin")+" "+resultSet.getDouble("nav"));
				
				masterMutualFundLumpsumSipList.add(masterMutualFundLumpsumSip);
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return masterMutualFundLumpsumSipList;
	}

	@Override
	public Double getMasterMutualFundLumpsumSipNavsLastValue() {
		Double nav = 0d;
		try {
			conn = DBUtil.getConnection();
			String query = "SELECT * FROM masterMFDailyNAV ORDER BY date DESC LIMIT 1;";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				nav = (resultSet.getDouble("nav"));
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return nav;
	}

	@Override
	public List<MasterEquityCalculator> getMasterEquityCalculatorClosingPrice(String isin) {
		List<MasterEquityCalculator> masterEquityCalculatorList = new ArrayList<>();
		try {
			conn = DBUtil.getConnection();
			String query = "select * from masterEquityDailyPrice where isin='"+isin+"'";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				MasterEquityCalculator masterEquityCalculator = new MasterEquityCalculator();
				masterEquityCalculator.setDate(resultSet.getDate("date"));
				masterEquityCalculator.setIsin(resultSet.getString("isin"));
				masterEquityCalculator.setClosingBal(resultSet.getDouble("closingPrice"));
				masterEquityCalculatorList.add(masterEquityCalculator);
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return masterEquityCalculatorList;
	}
	
	@Override
	public double getMasterEquityCalculatorClosingPrice(String isin,Date date) {
		 double closingPrice = 0;
		try {
			conn = DBUtil.getConnection();
			String query = "select * from masterEquityDailyPrice where isin= ? and Date= ?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1, isin); 
			preparedStatement.setDate(2, new java.sql.Date(date.getTime()));
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				closingPrice = resultSet.getDouble("closingPrice");
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return closingPrice;
	}

	@Override
	public String getFrequencyDescById(int frequencyId) {
		String frequencyDesc = "";
		try {
			conn = DBUtil.getConnection();
			String query = "SELECT lf.description from lookupFrequency lf WHERE lf.ID=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);

			preparedStatement.setDouble(1, frequencyId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				frequencyDesc = resultSet.getString("description");
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return frequencyDesc;
	}

	@Override
	public double[] getClientBasicDAById(int annuityId) {
		double epfValues[] = new double[4];
		try {
			conn = DBUtil.getConnection();
			String query = "select cnn.monthlyBasicDA,cnn.annualContributionIncrease,cepf.epfWithdrawalAge,cnn.serviceYears  from clientAnnuity cnn LEFT JOIN clientEPF cepf on cepf.ID=cnn.clientEPFID WHERE cnn.ID=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, annuityId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				epfValues[0] = resultSet.getDouble("monthlyBasicDA");
				epfValues[1] = resultSet.getDouble("annualContributionIncrease");
				epfValues[2] = resultSet.getDouble("epfWithdrawalAge");
				epfValues[3] = resultSet.getDouble("serviceYears");

			}
			resultSet.close();
			preparedStatement.close();
			DBUtil.closeConnection(conn);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return epfValues;
	}

	@Override
	public Date getMaxDateByIsin(String isin) {
		Date date = null;
		try {
			conn = DBUtil.getConnection();
			System.out.println("isin" + isin);
			String query = "SELECT max(date) as date FROM masterMFDailyNAV where isin='"+isin+"';";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				date = (resultSet.getDate("date"));
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return date;
	}

	@Override
	public MasterMutualFundLumpsumSip getNavByDateAndIsin(Date date, String isin) {
		double nav = 0.0d;
		MasterMutualFundLumpsumSip obj = new MasterMutualFundLumpsumSip();
		try {
			conn = DBUtil.getConnection();
			String query = "SELECT * FROM masterMFDailyNAV where isin='"+isin+"' and date = '"+date+"';";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				nav = (resultSet.getDouble("nav"));
			}
			resultSet.close();
			preparedStatement.close();
			obj.setDate(date);
			obj.setNav(nav);
			obj.setIsin(isin);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return obj;
	}

	@Override
	public String getIsinByRTACode(String schemeRTACode) {
		String isin = null;
		try {
			conn = DBUtil.getConnection();
			String query = "SELECT isin FROM schemeIsinMasterBO where cams_code='"+schemeRTACode+"' and status = 'NEW';";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				isin = (resultSet.getString("isin"));
			}
			resultSet.close();
			preparedStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			DBUtil.closeConnection(conn);
		}
		return isin;
	}

}
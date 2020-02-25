package com.finlabs.finexa.resources.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.finlabs.finexa.resources.model.GainCalculator;
import com.finlabs.finexa.resources.util.DBUtil;

public class GainCalculatorService {
	private Connection conn;
	public final String ADDITION_RULE = "Addition";
	public final String SUBTRACTION_RULE = "Subtraction";
	

	
	public static void main (String args[]) throws ParseException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date fromDate = formatter.parse("2018-01-01");
		Date toDate = formatter.parse("2018-06-07");
		GainCalculatorService gc = new GainCalculatorService();
		List<GainCalculator> list = gc.getTransationDetails("Akil Tarvadi", "D555", fromDate, toDate);
		System.out.println("LIST: "+list);
	}
	
	public GainCalculatorService() {
		this.conn = DBUtil.getConnection();
	}

	public Connection getConnection() {
		return this.conn;
	}
	
	public List<GainCalculator> getTransationDetails(String investorName, String isin, Date startDate, Date endDate) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<GainCalculator> transationList = new ArrayList<>();
		double cumulativeBuyQty = 0.0;
		double cumulativeSellQty = 0.0;
		double cumulativeBuyAmt = 0.00;
		int cumulativeBuySeq = 0;
		int cumulativeSellSeq = 0;
		
		try {
			
			String query = "select transactionDate,schemeName,transTypeCode,transUnits,nav,transAmt from transactionMasterBO"
					+ " where investorName = ? and schemeRTACode = ? and transactionDate between ? and ? order by transactionDate";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1,investorName);
			preparedStatement.setString(2,isin);
			preparedStatement.setDate(3, new java.sql.Date(startDate.getTime()));
			preparedStatement.setDate(4, new java.sql.Date(endDate.getTime()));
			ResultSet resultSet = preparedStatement.executeQuery();
			
			
			while (resultSet.next()) {
				GainCalculator gainCalculator = new GainCalculator();
				try {
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					String s = sdf.format(resultSet.getDate("transactionDate"));
					Date date = sdf.parse(s);
					gainCalculator.setTransationDate(date);
				} catch (ParseException ex) {
					ex.printStackTrace();
				}
				gainCalculator.setSchemeName(resultSet.getString("schemeName"));
				String code = resultSet.getString("transTypeCode");
				String transactionRuleQuery = "select effect from lookupTransactionRule where code = ?";
				PreparedStatement preparedStatementTransRule = conn.prepareStatement(transactionRuleQuery);
				preparedStatementTransRule.setString(1,code);
				
				ResultSet resultSetQuery = preparedStatementTransRule.executeQuery();
				
				if(resultSetQuery.next()) {
				String effect = resultSetQuery.getString("effect");
				gainCalculator.setTransactionType(effect);				
					/*
					 * resultSetQuery.close(); preparedStatementTransRule.close();
					 */
					 
				
				
				if (effect.equals(ADDITION_RULE)) {
					gainCalculator.setBuyQuantity(resultSet.getDouble("transUnits"));
					gainCalculator.setResidualBuyQty(resultSet.getDouble("transUnits"));
					gainCalculator.setSellQuantity(0.00);
					cumulativeBuyQty = cumulativeBuyQty + 1;
				} else {
					gainCalculator.setSellQuantity(resultSet.getDouble("transUnits"));
					gainCalculator.setBuyQuantity(0.00);
					gainCalculator.setResidualBuyQty(0.00);
					cumulativeSellQty = cumulativeSellQty + 1;
				}
				gainCalculator.setRate(resultSet.getDouble("nav"));
				cumulativeBuyQty = cumulativeBuyQty + (gainCalculator.getBuyQuantity() - gainCalculator.getSellQuantity());
				cumulativeSellQty = cumulativeSellQty + gainCalculator.getSellQuantity();
				gainCalculator.setBalanceQuantity(cumulativeBuyQty);
				gainCalculator.setNetQuantity((gainCalculator.getBuyQuantity() - gainCalculator.getSellQuantity()));
				double transactionAmt = resultSet.getInt("transAmt");
				gainCalculator.setTransAmt(transactionAmt);
				if (effect.equals(ADDITION_RULE)) {
					cumulativeBuyAmt = cumulativeBuyAmt + transactionAmt;
				}
				}
				resultSetQuery.close(); 
				preparedStatementTransRule.close();
				if ((gainCalculator.getBuyQuantity() - gainCalculator.getSellQuantity()) < 0.00) {
					gainCalculator.setCumSellQuantity((int)cumulativeSellQty);
				} else {
					gainCalculator.setCumSellQuantity(0);
				}
				if ((gainCalculator.getBuyQuantity() - gainCalculator.getSellQuantity()) > 0.00) {
					gainCalculator.setCumBuyQuantity((int)cumulativeBuyQty);
				} else {
					gainCalculator.setCumBuyQuantity(0);
				}
				if ((gainCalculator.getBuyQuantity() - gainCalculator.getSellQuantity()) > 0.00) {
					gainCalculator.setCumBuyCost((int)cumulativeBuyAmt);
				} else {
					gainCalculator.setCumBuyCost(0);
				}
				
				if (gainCalculator.getBuyQuantity() > 0.00) {
					gainCalculator.setBuySeq(cumulativeBuySeq);
				} else {
					gainCalculator.setBuySeq(0);
				}
				if (gainCalculator.getSellQuantity() > 0.00) {
					gainCalculator.setSellSeq(cumulativeSellSeq);
				} else {
					gainCalculator.setSellSeq(0);
				}
				
				
				//gainCalculator.setInterestRate(resultSet.getDouble("interest_rate"));
				//gainCalculator.setLoanTenure((resultSet.getInt("loan_tenure")));
				Date date = null;

				//advanceLoanCal.setLoanStartDate(date);
				//advanceLoanList.add(gainCalculator);
				transationList.add(gainCalculator);
				
			}
			
			/*
			 * Map<Date, Integer> diffCostMap = new HashMap<Date, Integer>(); diffCostMap =
			 * differentialCostCalculator(transationList);
			 * 
			 * for( GainCalculator gc : transationList ) { for (Map.Entry<Date, Integer>
			 * entry : diffCostMap.entrySet()) { if (
			 * entry.getKey().equals(gc.getTransationDate()))
			 * gc.setDifferentialCost(entry.getValue()); break; }
			 * 
			 * }
			 */
			
			differentialCostCalculator ( transationList );
			System.out.println("DIFFERENTIAL COST CALCI: "+transationList);
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return transationList;
	}
	
	private void differentialCostCalculator ( List<GainCalculator> transationList ) {
		//Map<Date, Integer> diffCostList = new HashMap<Date, Integer>();
		int count = 0;
		// Iterate to find out a positive 'Sell Quantity'
		for (GainCalculator gc : transationList) {
			
				
			//If positive 'Sell Quantity' found	
			if(gc.getSellQuantity() > 0) {
				double sellquantity = gc.getSellQuantity();									//Stored positive 'Sell Quantity'
				int diffCost = 0;														//Calculated Differential Cost to be stored
				double buyQuantity = 0.0d;													
				
				//Iterate from the first row to calculate units of 'Buy Quantity' 
				for (GainCalculator gainCalci : transationList) {
					
					if (gainCalci.getResidualBuyQty() > 0) {
						//Keeps calculating until 'Buy Quantity' becomes more than 'Sell Quantity' 
						if (buyQuantity <= sellquantity) {
							double unitsToBeConsidered = 0.0;
							if ((buyQuantity + gainCalci.getResidualBuyQty()) <= sellquantity) {
								buyQuantity += gainCalci.getResidualBuyQty();
								diffCost += (int)(gainCalci.getResidualBuyQty() * gainCalci.getRate());
								gainCalci.setResidualBuyQty(0.00);
							} else {
								unitsToBeConsidered = sellquantity - buyQuantity;
								buyQuantity += (sellquantity - buyQuantity);
								//Extra units needed from 'Buy Quantity' to match the 'Sell Quantity'
								diffCost += (int)((sellquantity - buyQuantity) * gainCalci.getRate());
								gainCalci.setResidualBuyQty(gainCalci.getResidualBuyQty() - unitsToBeConsidered);
								gc.setDifferentialCost(diffCost);
								break;
							}
							
						}
					}
				}	
			}
		}
		
	}	
	
}

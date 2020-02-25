package com.finlabs.finexa.resources.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.finlabs.finexa.resources.dao.MasterDAO;
import com.finlabs.finexa.resources.dao.MasterDAOImplementation;
import com.finlabs.finexa.resources.model.GainCalculator;
import com.finlabs.finexa.resources.model.GainLossMapDTO;
import com.finlabs.finexa.resources.model.MasterMutualFundLumpsumSip;
import com.finlabs.finexa.resources.util.DBUtil;
import com.finlabs.finexa.resources.util.XirrTest;

public class GainCalculatorBOService {
	private Connection conn;
	public final String ADDITION_RULE = "Addition";
	public final String SUBTRACTION_RULE = "Subtraction";
	public final String NO_EFFECT_RULE = "No Effect";
	private static  DecimalFormat numberFormatter = new DecimalFormat("0.00");
	  public static void main (String args[]) throws ParseException,
	  InstantiationException, IllegalAccessException, ClassNotFoundException {
		  
		  
		  
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
		  Date fromDate = formatter.parse("2016-01-01"); 
		  Date toDate = formatter.parse("2019-09-20"); 
		  GainCalculatorBOService gc = new GainCalculatorBOService(); 
		  GainCalculator gcMap = gc.getGainLossDetailsMap("Mahesh GokulDas Gattani", "1818524/96","GFRG", fromDate,toDate); 
		  
		  List<GainCalculator> list = gc.getTransationDetails("Mahesh GokulDas Gattani", "1818524/96","GFRG", fromDate,toDate); 
		  try {
			  PrintStream o = new PrintStream(new File("A.txt")); 
		      System.setOut(o); 
		        
			  for ( GainCalculator gc1 : list) {
				  System.out.println("LIST: "+gc1); 
			  } 
			//Blank workbook
		        XSSFWorkbook workbook = new XSSFWorkbook();
		         
		        //Create a blank sheet
		        XSSFSheet sheet = workbook.createSheet("Gain Calculator Projections");
		        int rownum = 0;
		        int cellnum = 0;
		        Row row = sheet.createRow(rownum++);
		        Cell cell = row.createCell(cellnum++);
	            cell.setCellValue((String)"Transaction Date");
	  
	            Cell cellSchemeName = row.createCell(cellnum++);
	            cellSchemeName.setCellValue((String)"Scheme Type");
	            
	            Cell cellTransactionType = row.createCell(cellnum++);
	            cellTransactionType.setCellValue((String)"Transaction Type");
	            
	            Cell cellBuyQuantity = row.createCell(cellnum++);
	            cellBuyQuantity.setCellValue((String)"Buy Quantity");
	            
	            Cell cellResidualBuy = row.createCell(cellnum++);
	            cellResidualBuy.setCellValue((String)"Residual Buy Quantity");
	            
	            Cell cellSellQty = row.createCell(cellnum++);
	            cellSellQty.setCellValue((String)"Sell Quantity");
	            
	            Cell cellRate = row.createCell(cellnum++);
	            cellRate.setCellValue((String)"Cell Rate");
	            
	            Cell blncQty = row.createCell(cellnum++);
	            blncQty.setCellValue((String)"Balance Quantity");
	            
	            Cell netQty = row.createCell(cellnum++);
	            netQty.setCellValue((String)"Net Quantity");
	            
	            Cell cellTransAmt = row.createCell(cellnum++);
	            cellTransAmt.setCellValue((String)"Transaction Amount");
	            
	            Cell cellDc = row.createCell(cellnum++);
	            cellDc.setCellValue((String)"Differential Cost");
	            
	            Cell cellRealized = row.createCell(cellnum++);
	            cellRealized.setCellValue((String)"Dividend Earned Realized");
	            
	            Cell unRealized = row.createCell(cellnum++);
	            unRealized.setCellValue((String)"Dividend Earned Unrealized");
	            
	            Cell cellDividendRealized = row.createCell(cellnum++);
	            cellDividendRealized.setCellValue((String)"Dividend Payout Realized");
	            
	            Cell cellDividendUnRealized = row.createCell(cellnum++);
	            cellDividendUnRealized.setCellValue((String)"Dividend Payout UnRealized");
	            
	            Cell cumSellQty = row.createCell(cellnum++);
	            cumSellQty.setCellValue((String)"Cum Sell Qty");
	            
	            
	            Cell cumBuyQty = row.createCell(cellnum++);
	            cumBuyQty.setCellValue((String)"Cum Buy Qty");
	            
	            Cell cumBuy = row.createCell(cellnum++);
	            cumBuy.setCellValue((String)"Cum Buy cost");
	            
	            Cell buySeq = row.createCell(cellnum++);
	            buySeq.setCellValue((String)"Cum Buy Seq");
	            
	            Cell sellSeq = row.createCell(cellnum++);
	            sellSeq.setCellValue((String)"Cum Sell Sequence");
	            
	            Cell capitalGain = row.createCell(cellnum++);
	            capitalGain.setCellValue((String)"Capital Gain");
		        
	            for ( GainCalculator gc1 : list) {
	            	row = sheet.createRow(rownum++);
	            	//Object [] objArr = data.get(key);
	            	cellnum = 0;
	            	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	            	String dateInString = sdf.format(gc1.getTransationDate());
	            	cell = row.createCell(cellnum++);
	            	cell.setCellValue((String)dateInString);

	            	cellSchemeName = row.createCell(cellnum++);
	            	cellSchemeName.setCellValue((String)gc1.getSchemeName());

	            	cellTransactionType = row.createCell(cellnum++);
	            	cellTransactionType.setCellValue((String)gc1.getTransactionType());

	            	cellBuyQuantity = row.createCell(cellnum++);
	            	cellBuyQuantity.setCellValue((double)gc1.getBuyQuantity());

	            	cellResidualBuy = row.createCell(cellnum++);
	            	cellResidualBuy.setCellValue((double)gc1.getResidualBuyQty());

	            	cellSellQty = row.createCell(cellnum++);
	            	cellSellQty.setCellValue((double)gc1.getSellQuantity());

	            	cellRate = row.createCell(cellnum++);
	            	cellRate.setCellValue((double)gc1.getRate());

	            	blncQty = row.createCell(cellnum++);
	            	blncQty.setCellValue((double)gc1.getBalanceQuantity());

	            	netQty = row.createCell(cellnum++);
	            	netQty.setCellValue((double)gc1.getNetQuantity());

	            	cellTransAmt = row.createCell(cellnum++);
	            	cellTransAmt.setCellValue((double)gc1.getTransAmt());

	            	cellDc = row.createCell(cellnum++);
	            	cellDc.setCellValue((double)gc1.getDifferentialCost());
	            	
	            	cellRealized = row.createCell(cellnum++);
	            	cellRealized.setCellValue((double)gc1.getDividendReinvestRealized());
	            	
	            	unRealized = row.createCell(cellnum++);
	            	unRealized.setCellValue((double)gc1.getDividendReinvestUnrealized());

	            	cellDividendRealized = row.createCell(cellnum++);
	            	cellDividendRealized.setCellValue((double)gc1.getDividendPayoutRealized());
	            	
	            	cellDividendUnRealized = row.createCell(cellnum++);
	            	cellDividendUnRealized.setCellValue((double)gc1.getDividendPayoutUnrealized());


	            	cumSellQty = row.createCell(cellnum++);
	            	cumSellQty.setCellValue((double)gc1.getCumSellQuantity());


	            	cumBuyQty = row.createCell(cellnum++);
	            	cumBuyQty.setCellValue((double)gc1.getCumBuyQuantity());

	            	cumBuy = row.createCell(cellnum++);
	            	cumBuy.setCellValue((double)gc1.getCumBuyCost());

	            	buySeq = row.createCell(cellnum++);
	            	buySeq.setCellValue((double)gc1.getBuySeq());

	            	sellSeq = row.createCell(cellnum++);
	            	sellSeq.setCellValue((double)gc1.getSellSeq());

	            	capitalGain = row.createCell(cellnum++);
	            	capitalGain.setCellValue((double)gc1.getCapitalGain());

	            }
		      //Write the workbook in file system
	            FileOutputStream out = new FileOutputStream(new File("/home/subhasree/Desktop/Calculations1.xlsx"));
	            workbook.write(out);
	            out.close();
	            System.out.println("Successffully Completed");
		        
		  } catch (Exception e) {
			  
		  }
	}
	
	public GainCalculatorBOService() {
		this.conn = DBUtil.getConnection();
	}

	public Connection getConnection() {
		return this.conn;
	}
	
	public List<GainCalculator> getTransationDetails(String investorName,String folioNo, String rtaCode, Date startDate, Date endDate) 
	throws InstantiationException, IllegalAccessException, ClassNotFoundException, ParseException {
		// TODO Auto-generated method stub
		List<GainCalculator> transationList = new ArrayList<>();
		double balanceQty = 0.00;
		double cumBuyQty = 0.00;
		double cumSellQty = 0.00;
		double cumBuyCost = 0.00;
		int buySeq = 0;
		int sellSeq = 0;
		double xirrTotal = 0.0;
		List<Double> paymentsTotal = new ArrayList<Double>();
		List<Date> dateTotal = new ArrayList<Date>();
		try {
			
			String query = "select transactionDate,schemeName,transTypeCode,transUnits,nav,transAmt from transactionMasterBO"
					+ " where investorName = ? and folioNo = ? and schemeRTACode = ? and transactionDate between ? and ? order by transactionDate";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1,investorName);
			preparedStatement.setString(2, folioNo);
			preparedStatement.setString(3,rtaCode);
			preparedStatement.setDate(4, new java.sql.Date(startDate.getTime()));
			preparedStatement.setDate(5, new java.sql.Date(endDate.getTime()));
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
				String transactionRuleQuery = "select effect,description from lookupTransactionRule where code = ?";
				PreparedStatement preparedStatementTransRule = conn.prepareStatement(transactionRuleQuery);
				preparedStatementTransRule.setString(1,code);
				
				ResultSet resultSetQuery = preparedStatementTransRule.executeQuery();
				
				if(resultSetQuery.next()) {
				String effect = resultSetQuery.getString("effect");
				String description = resultSetQuery.getString("description");
				gainCalculator.setTransactionType(effect);				
				gainCalculator.setTransactionCode(code);
				gainCalculator.setTransactionDescription(description);
				
				gainCalculator.setTransAmt(resultSet.getDouble("transAmt"));
				if (effect.equals(ADDITION_RULE)) {
					gainCalculator.setBuyQuantity(resultSet.getDouble("transUnits"));
					gainCalculator.setResidualBuyQty(resultSet.getDouble("transUnits"));
					gainCalculator.setSellQuantity(0.00);
					balanceQty += resultSet.getDouble("transUnits");
					cumBuyCost += resultSet.getDouble("transAmt");
					gainCalculator.setCumBuyCost(cumBuyCost);
					gainCalculator.setNetQuantity(resultSet.getDouble("transUnits"));
					if (gainCalculator.getTransactionCode().equals("DIR") || gainCalculator.getTransactionCode().equals("DR") || 
							gainCalculator.getTransactionCode().equals("Dividend Reinvestment")) {
					} else if (gainCalculator.getTransactionCode().equals("DP")) {
						
					} else {
						paymentsTotal.add(resultSet.getDouble("transAmt"));
						dateTotal.add(resultSet.getDate("transactionDate"));
					}
				} else if (effect.equals(NO_EFFECT_RULE) && code.equals("DP")) {
					gainCalculator.setBuyQuantity(0.0);
					gainCalculator.setResidualBuyQty(0.0);
					gainCalculator.setSellQuantity(0.0);
					gainCalculator.setRate(0.0);
					gainCalculator.setNetQuantity(0.0);
					double dividendRate = gainCalculator.getTransAmt()/balanceQty;
					gainCalculator.setDividendRate(dividendRate);
					gainCalculator.setDividendPayoutUnrealized(dividendRate * balanceQty);
					gainCalculator.setCumBuyCost(dividendRate * balanceQty);
					
				} else {
					gainCalculator.setSellQuantity(resultSet.getDouble("transUnits"));
					gainCalculator.setBuyQuantity(0.00);
					gainCalculator.setResidualBuyQty(0.00);
					balanceQty -= resultSet.getDouble("transUnits");
					gainCalculator.setCumBuyCost(0);
					gainCalculator.setNetQuantity(0.00-resultSet.getDouble("transUnits"));
					gainCalculator.setTransAmt(resultSet.getDouble("nav") * resultSet.getDouble("transUnits"));
					if (gainCalculator.getTransactionCode().equals("DIR") || gainCalculator.getTransactionCode().equals("DR") || 
							gainCalculator.getTransactionCode().equals("Dividend Reinvestment")) {
					} else if (gainCalculator.getTransactionCode().equals("DP")) {
						
					} else {
						paymentsTotal.add(-resultSet.getDouble("transAmt"));
						dateTotal.add(resultSet.getDate("transactionDate"));
					}
					//System.out.println("***************************************");
					//System.out.println("SO" +gainCalculator.getTransAmt());
				}
				gainCalculator.setRate(resultSet.getDouble("nav"));
				gainCalculator.setBalanceQuantity(balanceQty);

				if (gainCalculator.getNetQuantity() > 0) {
					cumBuyQty += resultSet.getDouble("transUnits");
					gainCalculator.setCumBuyQuantity(cumBuyQty);
					gainCalculator.setCumSellQuantity(0);
					gainCalculator.setBuySeq(++ buySeq);
					gainCalculator.setSellSeq(0);

				} else if (gainCalculator.getNetQuantity() == 0) {
					gainCalculator.setCumBuyQuantity(0);
					gainCalculator.setCumSellQuantity(0);
					gainCalculator.setBuySeq(0);
					gainCalculator.setSellSeq(0);
				}
				
				else {
					cumSellQty += gainCalculator.getSellQuantity();
					gainCalculator.setCumSellQuantity(cumSellQty);
					gainCalculator.setCumBuyQuantity(0);
					gainCalculator.setSellSeq(++ sellSeq);
					gainCalculator.setBuySeq(0);
				}
				}
				
				
				resultSetQuery.close(); 
				preparedStatementTransRule.close();
				transationList.add(gainCalculator);
				
			}
			
			transationList = differentialCostCalculator (transationList, rtaCode);
			
			// find today's date and mutual fund Nav on current date
			
			String isin = null;
			MasterDAO masterDao = new MasterDAOImplementation();
			Date maxDate = null;
			double currentNav = 0.0;
			try {
				isin = masterDao.getIsinByRTACode(rtaCode);
				if (isin != null) {
					maxDate = masterDao.getMaxDateByIsin(isin);
					if (maxDate != null) {
						MasterMutualFundLumpsumSip mutualFundSip = masterDao.getNavByDateAndIsin(maxDate, isin);
						if (mutualFundSip != null) {
							currentNav = mutualFundSip.getNav();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			paymentsTotal.add(-(balanceQty * currentNav));
			dateTotal.add(maxDate);
			
			double[] paymentTotal = new double[paymentsTotal.size()];
			Date[] dateArrayTotal = new Date[dateTotal.size()];
			int i = 0;
			for (double payment : paymentsTotal) {
			int dummy = (int) (Math.round(payment));
			paymentTotal[i] = dummy;
			i++;
			}
			
			int j = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			for (Date dates : dateTotal) {
			String dateString = sdf.format(dates);
			Date dt = sdf.parse(dateString);
			dateArrayTotal[j] = dt;
			j++;
			}
			
			// calculate XIRR
			XirrTest xirrCalculation = new XirrTest();
			System.out.println("payments" + paymentsTotal.toString());
			System.out.println("date" + dateTotal.toString());
			xirrTotal = xirrCalculation.findXirr(paymentTotal, dateArrayTotal);
			System.out.println("XIRR Unrtealized "+xirrTotal);
			
			for (GainCalculator gc : transationList) {
				gc.setXirrTotal(xirrTotal);
			}
			
			//System.out.println("DIFFERENTIAL COST CALCI: "+transationList);
			// you will get two arrayList
			// calulate the XIRR here
			resultSet.close();
			preparedStatement.close();
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		return transationList;
	}
	
	private List<GainCalculator>  differentialCostCalculator ( List<GainCalculator> transationList, String schemeRTACode) throws ParseException {
		//Map<Date, Integer> diffCostList = new HashMap<Date, Integer>();
		int count = 0;
		double buyUnits = 0.0d;	
		// Iterate to find out a positive 'Sell Quantity'
		Map<String, GainLossMapDTO> gainLossMapDTO = new HashMap<>();
		
		List<Date> dateForCalculation = new ArrayList<>();
		List<Double> paymentsForCalculation = new ArrayList<Double>();
		
		List<Date> dateForCalculationUnrealized = new ArrayList<>();
		List<Double> paymentsForCalculationUnrealized = new ArrayList<Double>();
		
		double xirr = 0.0d;
		for (GainCalculator gc : transationList) {
			
				
			//If positive 'Sell Quantity' found	
			if(gc.getSellQuantity() > 0) { // In order to find capital gain/loss
				Date sellDate = gc.getTransationDate();
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				String sellDateInString = format.format(sellDate);
				String sellType = gc.getTransactionType();
				String mapKey = sellDateInString + "-" + sellType;
				double sellquantity = gc.getSellQuantity();									//Stored positive 'Sell Quantity'
				double diffCost = 0.0d;	
				//Calculated Differential Cost to be stored
				double realizedDividend = 0.0;
				double unrealizedDividend = 0.0;
				double dividendPaidRealized = 0.0;
				double dividendPaidUnrealized = 0.0;
				double buyQuantity = 0.0d;													
				Date redeemDate = gc.getTransationDate();
				double redeemNav = gc.getRate();
				double sellAmt = -(sellquantity * redeemNav);
				//Iterate from the first row to calculate units of 'Buy Quantity' 
				for (GainCalculator gainCalci : transationList) {
					if (gainCalci.getResidualBuyQty() != 0) {
						
						//dateForCalculation.add(gainCalci.getTransationDate());
						//Keeps calculating until 'Buy Quantity' becomes more than 'Sell Quantity' 
						if (buyQuantity <= sellquantity) {
							double unitsToBeConsidered = 0.0;
							if ((buyQuantity + gainCalci.getResidualBuyQty()) <= sellquantity) {
								buyQuantity += gainCalci.getResidualBuyQty();
								//System.out.println(buyQuantity+" "+ sellquantity);
								if (gainCalci.getTransactionCode().equals("DIR") || gainCalci.getTransactionCode().equals("DR") || 
									gainCalci.getTransactionCode().equals("Dividend Reinvestment")) {
									realizedDividend += (gainCalci.getResidualBuyQty() * gainCalci.getRate());									
									paymentsForCalculation.add(gainCalci.getResidualBuyQty()*gainCalci.getRate());
									dateForCalculation.add(gainCalci.getTransationDate());
									
								} else if (gainCalci.getTransactionCode().equals("DP")) {
									if (gainCalci.getDividendRate() > 0.0) {
										dividendPaidRealized += (gainCalci.getResidualBuyQty() * gainCalci.getDividendRate());
										paymentsForCalculation.add(-(gainCalci.getResidualBuyQty()*gainCalci.getRate()));
										dateForCalculation.add(gainCalci.getTransationDate());
									}
								} else {
									diffCost += (gainCalci.getResidualBuyQty() * gainCalci.getRate());
									paymentsForCalculation.add(gainCalci.getResidualBuyQty()*gainCalci.getRate());
									dateForCalculation.add(gainCalci.getTransationDate());
								}
								gainCalci.setResidualBuyQty(0.00);
								gainCalci.setDividendReinvestUnrealized(0.00);
								gainCalci.setDividendPayoutUnrealized(0.0);
								gc.setDividendReinvestRealized(realizedDividend);
								gc.setDividendPayoutRealized(dividendPaidRealized);
								gc.setDifferentialCost(diffCost);
							} else {
								unitsToBeConsidered = sellquantity - buyQuantity;
								buyQuantity += unitsToBeConsidered;
								//System.out.println(buyQuantity+" "+ sellquantity);
								//Extra units needed from 'Buy Quantity' to match the 'Sell Quantity'
								double residue = gainCalci.getResidualBuyQty() - unitsToBeConsidered;
								gainCalci.setResidualBuyQty(residue);
								unrealizedDividend = (residue) * gainCalci.getRate();
								gainCalci.setDividendReinvestUnrealized(unrealizedDividend);
								if (gainCalci.getTransactionCode().equals("DIR") || gainCalci.getTransactionCode().equals("DR") || 
										gainCalci.getTransactionCode().equals("Dividend Reinvestment")) {
									realizedDividend += (unitsToBeConsidered) * gainCalci.getRate();
									//paymentsForCalculation.add((unitsToBeConsidered) * gainCalci.getRate());
									//dateForCalculation.add(gainCalci.getTransationDate());
									
								} else if (gainCalci.getTransactionCode().equals("DP")) {
									if (gainCalci.getDividendRate() > 0.0) {
										dividendPaidRealized += (unitsToBeConsidered * gainCalci.getDividendRate());
										//paymentsForCalculation.add(-(unitsToBeConsidered * gainCalci.getDividendRate()));
										//dateForCalculation.add(gainCalci.getTransationDate());
									}
								}
								else {
									diffCost += (unitsToBeConsidered) * gainCalci.getRate();
									
									paymentsForCalculation.add((unitsToBeConsidered) * gainCalci.getRate());
									dateForCalculation.add(gainCalci.getTransationDate());
									
								}
								gc.setDividendPayoutRealized(dividendPaidRealized);
								gc.setDividendReinvestRealized(realizedDividend);
								gc.setDifferentialCost(diffCost);
								gc.setCapitalGain((gc.getTransAmt()+ gc.getDividendReinvestRealized()) - gc.getDifferentialCost());
								//System.out.println("CAP: "+gc.getCapitalGain());
								paymentsForCalculation.add(sellAmt);
								dateForCalculation.add(redeemDate);
								System.out.println(paymentsForCalculation.toString());
								System.out.println(dateForCalculation.toString());
								break;
							}
							//System.out.println("CAPITAL GAIN: "+(gc.getDifferentialCost() - gc.getTransAmt()));
						}
					} /*else {
						//in case of capital loss and sell
						gc.setCapitalGain(gc.getTransAmt() - gc.getDifferentialCost());
						break;
					}*/
					
					if(gainCalci.getBuyQuantity() == 0 &&  Math.round(buyUnits)<Math.round(sellquantity)){
						transationList = new ArrayList<>();
						return transationList;
					}
					
				}
				// Sell Quantity is there but no capital gain loss is calculated
				if (gc.getCapitalGain() == 0) {
					gc.setCapitalGain(gc.getTransAmt() - diffCost);
					paymentsForCalculation.add(sellAmt);
					dateForCalculation.add(redeemDate);
				}
			} else {
				
				//dateForCalculation.add(gc.getTransationDate());
				buyUnits += gc.getBuyQuantity();
				if (gc.getTransactionCode().equals("DIR") || gc.getTransactionCode().equals("DR") || 
						gc.getTransactionCode().equals("Dividend Reinvestment")) {
					gc.setDividendReinvestUnrealized(gc.getBuyQuantity() * gc.getRate());
					gc.setDifferentialCost(0.0);
				} else if (gc.getTransactionCode().equals("DP")){
					gc.setDifferentialCost(0.0);
					gc.setDividendReinvestUnrealized(0.0);
				} else {
					gc.setDifferentialCost(gc.getBuyQuantity() * gc.getRate());
					gc.setDividendReinvestUnrealized(0.0);
					//paymentsForCalculation.add(gc.getBuyQuantity() * gc.getRate());

				}
			}
		}
		
		double residualBuyQty = 0.0;
		for (GainCalculator gc : transationList) {
			if (gc.getResidualBuyQty() > 0) {
				if (gc.getTransactionCode().equals("DIR") || gc.getTransactionCode().equals("DR") || 
						gc.getTransactionCode().equals("Dividend Reinvestment")) {
				}else if (gc.getTransactionCode().equals("DP")){
					
				} else {
					paymentsForCalculationUnrealized.add(gc.getResidualBuyQty() * gc.getRate());
					dateForCalculationUnrealized.add(gc.getTransationDate());
					residualBuyQty += gc.getResidualBuyQty();
				}
			}
		}
		
		// find today's date and mutual fund Nav on current date
		
		String isin = null;
		MasterDAO masterDao = new MasterDAOImplementation();
		Date maxDate = null;
		double currentNav = 0.0;
		try {
			isin = masterDao.getIsinByRTACode(schemeRTACode);
			if (isin != null) {
				maxDate = masterDao.getMaxDateByIsin(isin);
				if (maxDate != null) {
					MasterMutualFundLumpsumSip mutualFundSip = masterDao.getNavByDateAndIsin(maxDate, isin);
					if (mutualFundSip != null) {
						currentNav = mutualFundSip.getNav();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		paymentsForCalculationUnrealized.add(-(residualBuyQty * currentNav));
		if (maxDate == null) {
			dateForCalculationUnrealized.add(new Date());
		} else {
			dateForCalculationUnrealized.add(maxDate);
		}
		
		
		// Unrealized
		double[] paymentsUnrealized = new double[paymentsForCalculationUnrealized.size()];
		Date[] dateUnrealized = new Date[dateForCalculationUnrealized.size()];
		int i = 0;
		for (double payment : paymentsForCalculationUnrealized) {
		int dummy = (int) (Math.round(payment));
		paymentsUnrealized[i] = dummy;
		i++;
		}
		
		int j = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for (Date dates : dateForCalculationUnrealized) {
		String dateString = sdf.format(dates);
		Date dt = sdf.parse(dateString);
		dateUnrealized[j] = dt;
		j++;
		}
		j--;
		System.out.println(dateUnrealized[j]);
		// calculate XIRR
		XirrTest xirrCalculation = new XirrTest();
		System.out.println("payments" + paymentsForCalculationUnrealized.toString());
		System.out.println("date" + dateForCalculationUnrealized.toString());
		double xirrUnrealized = xirrCalculation.findXirr(paymentsUnrealized, dateUnrealized);
		System.out.println("XIRR Unrtealized "+xirrUnrealized);
		
		
		
		// For Realized
		double[] payments = new double[paymentsForCalculation.size()];
		Date[] date = new Date[dateForCalculation.size()];
		i = 0;
		for (double payment : paymentsForCalculation) {
		int dummy = (int) (Math.round(payment));
		payments[i] = dummy;
		
		System.out.println("Payments " +dummy );
		
		i++;
		}
		
		
		j = 0;
		for (Date dates : dateForCalculation) {
		String dateString = sdf.format(dates);
		Date dt = sdf.parse(dateString);
		date[j] = dt;
		System.out.println("Dates " +dt );
		j++;
		}
		
		// calculate XIRR ReaLIZED
		System.out.println("payments" + paymentsForCalculation.toString());
		System.out.println("date" + dateForCalculation.toString());
		xirr = xirrCalculation.findXirr(payments, date);
		System.out.println("XIRR "+xirr);
	
		for (GainCalculator gc : transationList) {
			gc.setXirr(xirr);
			gc.setXirrUnrealized(xirrUnrealized);
		}
		
		return transationList;
		
	}


	public GainCalculator getGainLossDetailsMap(String investorName,String folioNo, String rtaCode, Date startDate, Date endDate) 
	throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		// TODO Auto-generated method stub
		List<GainCalculator> transationList = new ArrayList<>();
		GainCalculator output = new GainCalculator();
		SortedMap<Date,List<GainLossMapDTO>> gainLossMapDTO = new TreeMap<Date, List<GainLossMapDTO>>();
		double balanceQty = 0.00;
		double cumBuyQty = 0.00;
		double cumSellQty = 0.00;
		double cumBuyCost = 0.00;
		int buySeq = 0;
		int sellSeq = 0;
		
		try {
			
			String query = "select transactionDate,schemeName,transTypeCode,transUnits,nav,transAmt from transactionMasterBO"
					+ " where investorName = ? and folioNo = ? and schemeRTACode = ? and transactionDate between ? and ? order by transactionDate";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setString(1,investorName);
			preparedStatement.setString(2, folioNo);
			preparedStatement.setString(3,rtaCode);
			preparedStatement.setDate(4, new java.sql.Date(startDate.getTime()));
			preparedStatement.setDate(5, new java.sql.Date(endDate.getTime()));
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
				String transactionRuleQuery = "select effect,description from lookupTransactionRule where code = ?";
				PreparedStatement preparedStatementTransRule = conn.prepareStatement(transactionRuleQuery);
				preparedStatementTransRule.setString(1,code);
				
				ResultSet resultSetQuery = preparedStatementTransRule.executeQuery();
				
				if(resultSetQuery.next()) {
				String effect = resultSetQuery.getString("effect");
				String description = resultSetQuery.getString("description");
				gainCalculator.setTransactionType(effect);				
				gainCalculator.setTransactionCode(code);
				gainCalculator.setTransactionDescription(description);
				
				gainCalculator.setTransAmt(resultSet.getDouble("transAmt"));
				if (effect.equals(ADDITION_RULE)) {
					gainCalculator.setBuyQuantity(resultSet.getDouble("transUnits"));
					gainCalculator.setResidualBuyQty(resultSet.getDouble("transUnits"));
					gainCalculator.setSellQuantity(0.00);
					balanceQty += resultSet.getDouble("transUnits");
					cumBuyCost += resultSet.getDouble("transAmt");
					gainCalculator.setCumBuyCost(cumBuyCost);
					gainCalculator.setNetQuantity(resultSet.getDouble("transUnits"));
				} else if (effect.equals(NO_EFFECT_RULE) && code.equals("DP")) {
					gainCalculator.setBuyQuantity(0.0);
					gainCalculator.setResidualBuyQty(0.0);
					gainCalculator.setSellQuantity(0.0);
					gainCalculator.setRate(0.0);
					gainCalculator.setNetQuantity(0.0);
					double dividendRate = gainCalculator.getTransAmt()/balanceQty;
					gainCalculator.setDividendRate(dividendRate);
					gainCalculator.setDividendPayoutUnrealized(dividendRate * balanceQty);
					gainCalculator.setCumBuyCost(dividendRate * balanceQty);
				} else {
					gainCalculator.setSellQuantity(resultSet.getDouble("transUnits"));
					gainCalculator.setBuyQuantity(0.00);
					gainCalculator.setResidualBuyQty(0.00);
					balanceQty -= resultSet.getDouble("transUnits");
					gainCalculator.setCumBuyCost(0);
					gainCalculator.setNetQuantity(0.00-resultSet.getDouble("transUnits"));
				}
				gainCalculator.setRate(resultSet.getDouble("nav"));
				gainCalculator.setBalanceQuantity(balanceQty);

				if (gainCalculator.getNetQuantity() > 0) {
					cumBuyQty += resultSet.getDouble("transUnits");
					gainCalculator.setCumBuyQuantity(cumBuyQty);
					gainCalculator.setCumSellQuantity(0);
					gainCalculator.setBuySeq(++ buySeq);
					gainCalculator.setSellSeq(0);

				} else if (gainCalculator.getNetQuantity() == 0) {
					gainCalculator.setCumBuyQuantity(0);
					gainCalculator.setCumSellQuantity(0);
					gainCalculator.setBuySeq(0);
					gainCalculator.setSellSeq(0);
				}
				
				else {
					cumSellQty += gainCalculator.getSellQuantity();
					gainCalculator.setCumSellQuantity(cumSellQty);
					gainCalculator.setCumBuyQuantity(0);
					gainCalculator.setSellSeq(++ sellSeq);
					gainCalculator.setBuySeq(0);
				}
				}
				resultSetQuery.close(); 
				preparedStatementTransRule.close();
				transationList.add(gainCalculator);
				
			}
			
			gainLossMapDTO = getGainLossMap( transationList );
			output.setGainLossMap(gainLossMapDTO);
		
			//System.out.println("DIFFERENTIAL COST CALCI: "+transationList);
			resultSet.close();
			preparedStatement.close();
		}  catch (SQLException e) {
			e.printStackTrace();
		}
		return output;
	}



	private SortedMap<Date, List<GainLossMapDTO>> getGainLossMap(List<GainCalculator> transationList) {
		//Map<Date, Integer> diffCostList = new HashMap<Date, Integer>();
		int count = 0;
		// Iterate to find out a positive 'Sell Quantity'
		SortedMap<Date, List<GainLossMapDTO>> gainLossMapDTO = new TreeMap<Date, List<GainLossMapDTO>>();
		for (GainCalculator gc : transationList) {
			List<GainLossMapDTO>  gainLossMapDTOList = new ArrayList<>();
			Date mapKey;
			//If positive 'Sell Quantity' found	
			if(gc.getSellQuantity() > 0) { // In order to find capital gain/loss
				Date sellDate = gc.getTransationDate();
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				String sellDateInString = format.format(sellDate);
				String sellType = gc.getTransactionType();
//				mapKey = sellDateInString + "-" + sellType;
				mapKey = sellDate;
				double sellquantity = gc.getSellQuantity();									//Stored positive 'Sell Quantity'
				double diffCost = 0.0d;														//Calculated Differential Cost to be stored
				double buyQuantity = 0.0d;													
				
				//Iterate from the first row to calculate units of 'Buy Quantity' 
				for (GainCalculator gainCalci : transationList) {
					GainLossMapDTO gainLossDTO = new GainLossMapDTO();
					if (gainCalci.getResidualBuyQty() > 0) {
						//Keeps calculating until 'Buy Quantity' becomes more than 'Sell Quantity' 
						if (buyQuantity <= sellquantity) {
							double unitsToBeConsidered = 0.0;
							gainLossDTO.setSellTransactionType(gc.getTransactionDescription());
							gainLossDTO.setSoldUnits(gc.getSellQuantity());
							gainLossDTO.setSoldAmt(gc.getTransAmt());
							gainLossDTO.setSoldNav(gc.getRate());
							gainLossDTO.setSellTransationDate(gc.getTransationDate());
							if ((buyQuantity + gainCalci.getResidualBuyQty()) <= sellquantity) {
								buyQuantity += gainCalci.getResidualBuyQty();
								gainLossDTO.setPurchaseUnits(gainCalci.getResidualBuyQty());
								gainLossDTO.setPurchaseTransactionType(gainCalci.getTransactionDescription());
								gainLossDTO.setPurchaseTransationDate(gainCalci.getTransationDate());
								gainLossDTO.setPurchaseAmt(gainCalci.getResidualBuyQty() * gainCalci.getRate());
								gainLossDTO.setPurchaseNav(gainCalci.getRate());
								gainLossMapDTOList.add(gainLossDTO);
								diffCost += (gainCalci.getResidualBuyQty() * gainCalci.getRate());
								gainCalci.setResidualBuyQty(0.00);
								gc.setDifferentialCost(diffCost);
								
							} else {
								unitsToBeConsidered = sellquantity - buyQuantity;
								buyQuantity += unitsToBeConsidered;
								//Extra units needed from 'Buy Quantity' to match the 'Sell Quantity'
								gainLossDTO.setPurchaseUnits(unitsToBeConsidered);
								gainLossDTO.setPurchaseTransactionType(gainCalci.getTransactionDescription());
								gainLossDTO.setPurchaseTransationDate(gainCalci.getTransationDate());
								gainLossDTO.setPurchaseAmt(unitsToBeConsidered * gainCalci.getRate());
								gainLossDTO.setPurchaseNav(gainCalci.getRate());
								diffCost += (unitsToBeConsidered * gainCalci.getRate());
								gainCalci.setResidualBuyQty(gainCalci.getResidualBuyQty() - unitsToBeConsidered);
								gc.setDifferentialCost(diffCost);
								gc.setCapitalGain(gc.getTransAmt() - gc.getDifferentialCost());
								//System.out.println("CAP: "+gc.getCapitalGain());
								gainLossMapDTOList.add(gainLossDTO);
								break;
							}
							//System.out.println("CAPITAL GAIN: "+(gc.getDifferentialCost() - gc.getTransAmt()));
						}
					} /*else {
						//in case of capital loss and sell
						gc.setCapitalGain(gc.getTransAmt() - gc.getDifferentialCost());
						break;
					}*/
				}
				gainLossMapDTO.put(mapKey, gainLossMapDTOList);
			} else {
				gc.setDifferentialCost(gc.getBuyQuantity() * gc.getRate());
			}
		}
		return gainLossMapDTO;
		
	}	
	
}

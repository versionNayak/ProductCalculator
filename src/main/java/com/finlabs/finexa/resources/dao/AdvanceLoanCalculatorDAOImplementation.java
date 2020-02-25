package com.finlabs.finexa.resources.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.finlabs.finexa.resources.model.AdvanceLoanCalculator;
import com.finlabs.finexa.resources.util.DBUtil;

public class AdvanceLoanCalculatorDAOImplementation implements AdvanceLoanCalculatorDAO {
	private Connection conn;

	public AdvanceLoanCalculatorDAOImplementation() {
		this.conn = DBUtil.getConnection();
	}

	public Connection getConnection() {
		return this.conn;
	}

	public List<AdvanceLoanCalculator> getAdvanceLoanCalDetails(int clientId) {
		List<AdvanceLoanCalculator> advanceLoanList = new ArrayList<AdvanceLoanCalculator>();
		try {
			String query = "select loan_type,loan_amount,interest_rate,loan_tenure,loan_start_date from client_loans  where client_id=?";
			PreparedStatement preparedStatement = conn.prepareStatement(query);
			preparedStatement.setInt(1, clientId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				AdvanceLoanCalculator advanceLoanCal = new AdvanceLoanCalculator();
				advanceLoanCal.setLoanType(resultSet.getInt("loan_type"));
				advanceLoanCal.setLoanAmount(resultSet.getDouble("loan_amount"));
				advanceLoanCal.setInterestRate(resultSet.getDouble("interest_rate"));
				advanceLoanCal.setLoanTenure((resultSet.getInt("loan_tenure")));
				Date date = null;

				try {
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					String s = sdf.format(resultSet.getDate("loan_start_date"));
					date = sdf.parse(s);
				} catch (ParseException ex) {
					ex.printStackTrace();
				}
				advanceLoanCal.setLoanStartDate(date);
				advanceLoanList.add(advanceLoanCal);
			}
			resultSet.close();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return advanceLoanList;
	}

}

package com.finlabs.finexa.resources.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBUtil {
	private static Connection conn;

	/*
	 * public static Connection getConnection() { Context initialContext; Connection
	 * conn = null; try { initialContext = new InitialContext(); Object
	 * jndiContextObj = initialContext.lookup("java:/comp/env"); Context
	 * environmentContext = (Context) jndiContextObj; DataSource dataSource =
	 * (DataSource) environmentContext.lookup("jdbc/finexa_v3"); //
	 * jdbc:mysql://localhost:3306/finexa_v2?useSSL=false conn =
	 * dataSource.getConnection(); } catch (NamingException e) {
	 * //getConnectionFromProperties(); e.printStackTrace();
	 * 
	 * } catch (SQLException e) { e.printStackTrace(); }
	 * 
	 * return conn; }
	 */

	public static Connection getConnection() {
		InputStream inputStream = DBUtil.class.getClassLoader().getResourceAsStream("db.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
			String driver = properties.getProperty("driver");
			String url = properties.getProperty("url");
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public static void closeConnection(Connection toBeClosed) {
		if (toBeClosed == null)
			return;
		try {
			toBeClosed.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

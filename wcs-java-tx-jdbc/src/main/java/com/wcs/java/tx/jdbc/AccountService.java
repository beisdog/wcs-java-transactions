package com.wcs.java.tx.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AccountService {
	
	private Connection con;
	
	public AccountService(Connection con) {
		super();
		this.con = con;
	}

	public void deleteAllAccounts() throws SQLException {
		String sql = "DELETE FROM bankaccounts";
		Statement st = con.createStatement();
		st.execute(sql);
		st.close();
	}
	
	public void insertNewAccount(String user, BigDecimal balance) throws SQLException {
		String sql = "INSERT INTO bankaccounts (user, balance) values (?, ?)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, user);
		st.setBigDecimal(2, balance);

		st.execute();
		st.close();
	}

	public BigDecimal getBalanceOfUser(String user) throws SQLException {
		String sql = "SELECT balance FROM bankaccounts where user = ?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(1, user);
		ResultSet rs = st.executeQuery();

		rs.next();
		BigDecimal dec = rs.getBigDecimal(1);
		rs.close();
		st.close();
		return dec;
	}

	public void updateBalanceOfUser(String user, BigDecimal balance) throws SQLException {
		String sql = "UPDATE bankaccounts set balance=? where user=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setString(2, user);
		st.setBigDecimal(1, balance);

		st.execute();
		st.close();
	}
	
	public void withdraw(String user, BigDecimal amount) throws SQLException, InsufficientFundsException {
		BigDecimal oldBalance = getBalanceOfUser(user);
		BigDecimal newBalance = oldBalance.subtract(amount);
		
		if (newBalance.doubleValue() < 0 ) {
			throw new InsufficientFundsException(user + " has not enough funds to withdraw " + amount);
		}
		updateBalanceOfUser(user, newBalance);
	}
	
	public void deposit(String user, BigDecimal amount) throws SQLException, InsufficientFundsException {
		BigDecimal oldBalance = getBalanceOfUser(user);
		BigDecimal newBalance = oldBalance.add(amount);
		updateBalanceOfUser(user, newBalance);
	}
	

}

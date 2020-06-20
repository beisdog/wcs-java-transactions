package com.wcs.java.tx.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class TransferService {
	
	private AccountService accountService;
	private Connection connection;
	
	public TransferService(AccountService accountService, Connection connection) {
		super();
		this.accountService = accountService;
		this.connection = connection;
	}

	public void transferMoney(String userFrom, String userTo, BigDecimal amount)
			throws SQLException, InsufficientFundsException {
		accountService.deposit(userTo, amount);
		accountService.withdraw(userFrom, amount);
	}

}

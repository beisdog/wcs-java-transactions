package com.wcs.java.tx.springboot;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SetupUtil {

	private AccountService accService;

	@Autowired
	public SetupUtil(AccountService accService) {
		super();
		this.accService = accService;
	}

	public void setup() {

		// createTable(con);
		accService.insertNewAccount("david", new BigDecimal("0"));
		accService.insertNewAccount("andre", new BigDecimal("0"));
		accService.insertNewAccount("markus", new BigDecimal("0"));
	}

	public void tearDown() {
		accService.deleteAllAccounts();
	}

}

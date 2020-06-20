package com.wcs.java.tx.springboot;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class TransactionTest {

	@Autowired
	SetupUtil setupUtil;

	@Autowired
	private AccountService accService;

	@Autowired
	private TransferService transferService;

	@BeforeEach
	public void setup() throws SQLException {
		setupUtil.tearDown();
		setupUtil.setup();
	}

	@Test
	public void testWithdraw() throws Exception {
		// 2 accounts with 1000 EUR

		accService.deposit("david", new BigDecimal("1000"));

		BigDecimal balance = accService.getBalanceOfUser("david");
		// overdraw
		try {
			accService.withdraw("david", balance);
			assertTrue( new BigDecimal(0).compareTo(accService.getBalanceOfUser("david")) == 0, "Balance should be 0");
		} catch (InsufficientFundsException e) {
			fail("Should not be able to withdraw his account");
		}

	}

	@Test
	public void testWithdrawTooMuch() throws Exception {

		accService.deposit("david", new BigDecimal("1000"));

		BigDecimal balance = accService.getBalanceOfUser("david");
		// overdraw
		try {
			accService.withdraw("david", balance.add(new BigDecimal(1)));
			fail("Should not be able to overdraw his account");
		} catch (InsufficientFundsException e) {
			System.out.println("Correct exception:" + e);
		}
	}

	@Test
	public void testTransferGood() throws Exception {

		accService.deposit("david", new BigDecimal("1000"));
		accService.deposit("andre", new BigDecimal("1000"));

		transferService.transferMoney("david", "andre", new BigDecimal(1000));

		// TODO assert that davids balance is 0
		// TODO assert that andres balance is 2000
		fail("TODO: implement checks");
	}

	@Test
	public void testTransferBad() throws Exception {

		accService.deposit("david", new BigDecimal("1000"));
		accService.deposit("andre", new BigDecimal("1000"));

		try {
			transferService.transferMoney("david", "andre", new BigDecimal(2000));
			fail("should not be able to transfer");
		} catch (InsufficientFundsException e) {
			System.out.println("Correct should not overdraw");

			fail("implement check if the balances are still at 1000");
		}
	}
	
	

}

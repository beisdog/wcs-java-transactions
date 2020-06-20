package com.wcs.java.tx.jpa.simple;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.math.BigDecimal;
import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TransactionTest {
	
	private EntityManager em;
	
	
	@Before
	public void setup() {
		SetupUtil.tearDown();
		SetupUtil.setup();
		this.em = SetupUtil.openEntityManager();
	}
	
	@After
	public void tearDown() {
		if (this.em !=null && this.em.isOpen()) {
			this.em.close();
		}
	}

	@Test
	public void testWithdraw() {
				// 2 accounts with 1000 EUR
		AccountService accService = new AccountService(em);

		accService.deposit("david", new BigDecimal("1000"));

		BigDecimal balance = accService.getBalanceOfUser("david");
		// overdraw
		try {
			accService.withdraw("david", balance);
			assertTrue("Balance should be 0", new BigDecimal(0).compareTo(accService.getBalanceOfUser("david")) == 0);
		} catch (InsufficientFundsException e) {
			fail("Should not be able to withdraw his account");
		}

		em.close();
	}

	@Test
	public void testWithdrawTooMuch() {
		AccountService accService = new AccountService(em);

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

		// 2 accounts with 1000 EUR
		AccountService accService = new AccountService(em);
		TransferService transferService = new TransferService(accService, em);

		accService.deposit("david", new BigDecimal("1000"));
		accService.deposit("andre", new BigDecimal("1000"));

		transferService.transferMoney("david", "andre", new BigDecimal(1000));

		// TODO assert that davids balance is 0
		// TODO assert that andres balance is 2000
		fail("TODO: implement checks");
	}

	@Test
	public void testTransferBad() {

		// 2 accounts with 1000 EUR
		AccountService accService = new AccountService(em);
		TransferService transferService = new TransferService(accService, em);

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

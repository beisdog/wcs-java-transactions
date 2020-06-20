package com.wcs.java.tx.jpa.simple;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger log = LoggerFactory.getLogger(Main.class);


	public static void main(String[] args) {
		
		EntityManager em = SetupUtil.openEntityManager();
		EntityTransaction tx = em.getTransaction();
		
		AccountService accService = new AccountService(em);
		TransferService service = new TransferService(accService,em);
		
		SetupUtil.tearDown();
		SetupUtil.setup();

		try {
			tx.begin();
			log.info("tx begin");
			for (int i = 0; i < 10; i++) {
				System.out.println("----------------------------------");
				System.out.println("trying transfer: " + i + " ...");
				
				service.transferMoney("david", "markus", new BigDecimal(200));
				System.out.println("transfered!");
				System.out.println("david: " + accService.getBalanceOfUser("david"));
				System.out.println("markus: " + accService.getBalanceOfUser("markus"));

			}
			tx.commit();
			log.info("tx committed");
		} catch (Exception ex) {
			log.error("must rollback", ex);
			tx.rollback();
			log.info("tx rolledback");

		} finally {
			em.close();
		}
	}

	

}

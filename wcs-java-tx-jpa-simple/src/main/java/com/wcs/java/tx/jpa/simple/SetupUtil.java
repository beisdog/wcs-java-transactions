package com.wcs.java.tx.jpa.simple;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class SetupUtil {
	
	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("Bank");
	
	public static EntityManager openEntityManager() {
		EntityManager em = ENTITY_MANAGER_FACTORY.createEntityManager();
		return em;
	}

	/**
	 * Set up 3 accounts with david, markus, andre with 0 EUR
	 */
	public static void setup() {
		EntityManager em = openEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		AccountService accService = new AccountService(em);
		//createTable(con);
		accService.insertNewAccount("david", new BigDecimal("0"));
		accService.insertNewAccount("markus", new BigDecimal("0"));
		accService.insertNewAccount("andre", new BigDecimal("0"));
		tx.commit();
		em.close();
	}

	public static void tearDown() {
		EntityManager em = openEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		AccountService accService = new AccountService(em);
		accService.deleteAllAccounts();
		tx.commit();
		em.close();
	}

}

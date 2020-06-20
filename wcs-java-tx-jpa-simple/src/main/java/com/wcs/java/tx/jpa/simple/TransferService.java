package com.wcs.java.tx.jpa.simple;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class TransferService {

	private AccountService service;
	private EntityManager em;

	public TransferService(AccountService service, EntityManager em) {
		super();
		this.service = service;
		this.em = em;
	}

	public List<BankAccount> transferMoney(String userFrom, String userTo, BigDecimal amount)
			throws InsufficientFundsException {

		//TODO implement correct behaviour
		BankAccount toAccount = service.deposit(userTo, amount);
		BankAccount fromAccount = service.withdraw(userFrom, amount);
		return Arrays.asList(fromAccount, toAccount);
	}
	
	private EntityTransaction getTransaction() {
		return em.getTransaction();
	}
	
	

}

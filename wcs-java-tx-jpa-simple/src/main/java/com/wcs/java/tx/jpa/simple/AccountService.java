package com.wcs.java.tx.jpa.simple;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

public class AccountService {

	private EntityManager em;

	public AccountService(EntityManager em) {
		super();
		this.em = em;
	}
	
	public BankAccount insertNewAccount(String user, BigDecimal balance) {
		BankAccount acc = new BankAccount();
		acc.setUser(user);
		acc.setBalance(balance);
		
		this.em.persist(acc);
		return acc;
	}

	public BankAccount saveAccount(BankAccount acc) {
		return this.em.merge(acc);
	}

	@SuppressWarnings("unchecked")
	public List<BankAccount> getAccounts() {
		return (List<BankAccount>) this.em.createQuery("From BankAccount b").getResultList();
	}

	public BankAccount deposit(String user, BigDecimal amount) {

		BankAccount fromAccount = getAccountByUser(user);
		BigDecimal newBalance = fromAccount.getBalance().add(amount);
		fromAccount.setBalance(newBalance);
		return fromAccount;
	}

	public BankAccount withdraw(String user, BigDecimal amount) throws InsufficientFundsException {

		BankAccount fromAccount = getAccountByUser(user);
		BigDecimal oldBalance = fromAccount.getBalance();
		BigDecimal newBalance = oldBalance.subtract(amount);
		fromAccount.setBalance(newBalance);
		if (newBalance.doubleValue() < 0d) {
			throw new InsufficientFundsException(user + " has not enough funds " + oldBalance);
		}

		return fromAccount;
	}

	public BankAccount getAccountByUser(String user) {
		return (BankAccount) this.em.createQuery("FROM BankAccount b where b.user = :user").setParameter("user", user)
				.getSingleResult();
	}

	public BigDecimal getBalanceOfUser(String user) {
		BankAccount acc = getAccountByUser(user);
		return acc.getBalance();
	}

	public void deleteAllAccounts() {
		em.createQuery("DELETE from BankAccount").executeUpdate();
	}

}

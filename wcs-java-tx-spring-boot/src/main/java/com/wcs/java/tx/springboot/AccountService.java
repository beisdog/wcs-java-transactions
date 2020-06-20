package com.wcs.java.tx.springboot;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class AccountService {

	private BankAccountRepo repo;

	@Autowired
	public AccountService(BankAccountRepo repo) {
		super();
		this.repo = repo;
	}

	
	public BankAccount saveAccount(BankAccount acc) {
		return this.repo.save(acc);
	}
	
	public List<BankAccount> getAccounts() {
		return this.repo.findAll();
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
	
	public BankAccount getAccountByUser(@PathVariable String user) {
		return this.repo.findByUser(user);
	}

	public void insertNewAccount(String user, BigDecimal balance) {
		BankAccount acc = new BankAccount();
		acc.setUser(user);
		acc.setBalance(balance);
		saveAccount(acc);
		
	}

	public void deleteAllAccounts() {
		repo.deleteAll();
	}

	public BigDecimal getBalanceOfUser(String user) {
		BankAccount fromAccount = getAccountByUser(user);
		return fromAccount.getBalance();
	}
	
	
}

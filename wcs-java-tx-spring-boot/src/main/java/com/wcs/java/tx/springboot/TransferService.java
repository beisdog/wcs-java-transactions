package com.wcs.java.tx.springboot;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransferService {

	private AccountService service;

	@Autowired
	public TransferService(AccountService service) {
		super();
		this.service = service;
	}

	public List<BankAccount> transferMoney(String userFrom, String userTo, BigDecimal amount)
			throws InsufficientFundsException {

		BankAccount toAccount = service.deposit(userTo, amount);
		BankAccount fromAccount = service.withdraw(userFrom, amount);
		return Arrays.asList(fromAccount, toAccount);
	}

}

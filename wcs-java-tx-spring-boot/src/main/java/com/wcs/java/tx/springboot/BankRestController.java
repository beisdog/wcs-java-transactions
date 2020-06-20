package com.wcs.java.tx.springboot;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/bank")
public class BankRestController {
	
	private AccountService accountService;

	private TransferService service;

	@Autowired
	public BankRestController(TransferService service) {
		super();
		this.service = service;
	}

	@PostMapping("/accounts")
	public BankAccount saveAccount(BankAccount acc) {
		return this.accountService.saveAccount(acc);
	}
	
	@GetMapping("/accounts")
	public List<BankAccount> getAccounts() {
		return this.accountService.getAccounts();
	}
	
	@PostMapping("/deposit")
	public BankAccount deposit(@RequestBody DepositRequest req) {
		return accountService.deposit(req.getUser(), req.getAmount());
	}
	
	@PostMapping("/withdraw")
	public BankAccount withdraw(@RequestBody DepositRequest req) throws InsufficientFundsException {
		return accountService.withdraw(req.getUser(), req.getAmount());
	}
	
	@GetMapping("/accounts/byUser/{user}")
	public BankAccount getAccountByUser(@PathVariable String user) {
		return accountService.getAccountByUser(user);
	}
	
	@PostMapping("/transfer")
	public List<BankAccount> transfer(TransferRequest req) throws InsufficientFundsException {
		return service.transferMoney(req.getUserFrom(), req.getUserTo(), req.getAmount());
	}
	
	
}

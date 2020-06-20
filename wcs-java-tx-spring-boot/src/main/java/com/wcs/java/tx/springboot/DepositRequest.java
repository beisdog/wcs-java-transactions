package com.wcs.java.tx.springboot;

import java.math.BigDecimal;

public class DepositRequest {
	
	private String user;
	private BigDecimal amount;
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}

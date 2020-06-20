package com.wcs.java.tx.jpa.simple;

public class InsufficientFundsException extends Exception {

	private static final long serialVersionUID = -2197283355716188960L;

	public InsufficientFundsException() {
		super();
	}

	public InsufficientFundsException(String message, Throwable cause) {
		super(message, cause);
	}

	public InsufficientFundsException(String message) {
		super(message);
	}

	public InsufficientFundsException(Throwable cause) {
		super(cause);
	}

}

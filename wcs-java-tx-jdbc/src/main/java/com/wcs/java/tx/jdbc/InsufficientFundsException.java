package com.wcs.java.tx.jdbc;

public class InsufficientFundsException extends Exception {

	private static final long serialVersionUID = 3362862071818521811L;

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

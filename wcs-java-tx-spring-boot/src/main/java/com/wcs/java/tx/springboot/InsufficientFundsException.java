package com.wcs.java.tx.springboot;

public class InsufficientFundsException extends Exception {

	private static final long serialVersionUID = 1252085653055233720L;

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

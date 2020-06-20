package com.wcs.java.tx.jpa.simple;

import java.math.BigDecimal;

public class BigDecimalUtil {
	
	public static BigDecimal ZERO = new BigDecimal("0");
	
	
	public static boolean isZero(BigDecimal dec) {
		return dec.compareTo(ZERO) == 0;
	}
	
	public static boolean isFirstSmallerThanSecond(BigDecimal first, BigDecimal second) {
		return first.compareTo(second) < 0;
	}
	
	public static boolean isMinus(BigDecimal dec) {
		return dec.compareTo(ZERO) < 0;
	}
	
	public static void main(String[] args) {
		System.out.println(isZero(new BigDecimal(0)));
		System.out.println(isFirstSmallerThanSecond(new BigDecimal(0), new BigDecimal(0.1)));
		System.out.println(isFirstSmallerThanSecond(new BigDecimal(0.2), new BigDecimal(0.1)));
		System.out.println(isMinus(new BigDecimal(-2)));
	}

}

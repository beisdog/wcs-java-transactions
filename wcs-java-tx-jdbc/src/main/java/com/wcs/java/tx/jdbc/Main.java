package com.wcs.java.tx.jdbc;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws Exception {

		Class.forName("com.mysql.jdbc.Driver");
		Connection con = SetupUtil.openConnection();
		
		AccountService accService = new AccountService(con);
		TransferService service = new TransferService(accService, con);
		
		//createTable(con);
		SetupUtil.tearDown();
		SetupUtil.setup();

		//commit explicitly (use transaction)
		con.setAutoCommit(false);

		try {
			for (int i = 1; i <= 10; i++) {
				System.out.println("----------------------------------");
				System.out.println("trying transfer: " + i + " ...");
				
				service.transferMoney("david", "markus", new BigDecimal(200));
				System.out.println("transfered!");
				System.out.println("david: " + accService.getBalanceOfUser("david"));
				System.out.println("markus: " + accService.getBalanceOfUser("markus"));
			}

			con.commit();

		} catch (SQLException | InsufficientFundsException e) {
			e.printStackTrace();
			con.rollback();
		} finally {
			con.close();
		}

	}

	

	

}

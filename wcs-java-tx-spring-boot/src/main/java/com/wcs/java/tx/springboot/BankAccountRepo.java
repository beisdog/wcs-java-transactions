package com.wcs.java.tx.springboot;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepo extends JpaRepository<BankAccount, Long> {

	BankAccount findByUser(String user);

}

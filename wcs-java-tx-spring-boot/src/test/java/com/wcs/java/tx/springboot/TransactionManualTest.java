package com.wcs.java.tx.springboot;

import java.math.BigDecimal;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@SpringBootTest
@org.springframework.transaction.annotation.Transactional(propagation = Propagation.NOT_SUPPORTED)
public class TransactionManualTest {
	
	@Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    AccountService accService;
    
    @Autowired
    private SetupUtil setupUtil;
	
	
	@Test
    void transferManualTest() throws Exception{
		setupUtil.setup();
		
        DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
        definition.setIsolationLevel(TransactionDefinition.ISOLATION_REPEATABLE_READ);
        definition.setTimeout(3);

        TransactionStatus status = transactionManager.getTransaction(definition);
        try {
            accService.withdraw("david", new BigDecimal("1000"));
            accService.deposit("andre", new BigDecimal("1000"));
            transactionManager.commit(status);
        } catch (Exception ex) {
            transactionManager.rollback(status);
        }

        //TODO assert
    }

}

package com.n26;


import static org.junit.Assert.assertEquals;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.n26.model.Statistics;
import com.n26.model.Transaction;
import com.n26.services.TransactionServiceImpl;


@SpringBootTest
public class TransactionServiceTests {
	
	  private TransactionServiceImpl transactionService;// = new TransactionServiceImpl();
	    
	  @Before
	  public void setUp() {
		  transactionService = new TransactionServiceImpl();
		  
		  Transaction transactionOne = new Transaction();
		  transactionOne.setAmount("12.3456");
		  ZonedDateTime currentTimestamp = ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(10);
	      transactionOne.setTimestamp(currentTimestamp.toString());
		  	
		  Transaction transactionTwo = new Transaction();
		  transactionTwo.setAmount("78.9101");
		  currentTimestamp = ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(30);
		  transactionTwo.setTimestamp(currentTimestamp.toString());
			
		  Transaction transactionThree = new Transaction();
		  transactionThree.setAmount("11.1111");
		  transactionThree.setTimestamp("2000-06-03T17:05:51.312Z");
		  	
		  transactionService.addTransaction(transactionTwo);
		  transactionService.addTransaction(transactionOne);
		  transactionService.addTransaction(transactionThree);
	  }

	  @After
	  public void teardown() {
		  transactionService.deleteAllTransactions();
	  }

	  @Test
	  public void allTransactionsRead() {
		  Collection<Transaction> foundTransactions = transactionService.retrieveTransactions();
		  assertEquals(foundTransactions.size(), 3);
	  }
	  
	  @Test
	  public void allTransactionsDeleted() {
		int deletionsDone = transactionService.deleteAllTransactions();
		
		assertEquals(deletionsDone, 3);
	  }
	  
	  @Test
	  public void oneTransactionPersited() {
		transactionService.deleteAllTransactions();
		  
		Transaction transaction = new Transaction();
	  	transaction.setAmount("11.1111");
	  	transaction.setTimestamp("2021-06-03T17:05:51.312Z");
	  	
		Transaction resultingTransaction = transactionService.addTransaction(transaction);

		assertEquals(transaction, resultingTransaction);
	    
	  }

	  @Test
	  public void olderTransactionsCleanedUp() {
		  	  	
		transactionService.cleanUpTransactionsOlderThan();

		assertEquals(transactionService.retrieveTransactions().size(), 2);
	    
	  }

	  @Test
	  public void statisticsCalculated() {
		Statistics statsNeeded = new Statistics();
		statsNeeded.setSum("91.26");
		statsNeeded.setAvg("45.63");
		statsNeeded.setMax("78.91");
		statsNeeded.setMin("12.35");
		statsNeeded.setCount(Long.valueOf(2));
	    
		Statistics calculatedStats = transactionService.retrieveStatistics();
		assertEquals(calculatedStats, statsNeeded);
	    
	  }
	  
}
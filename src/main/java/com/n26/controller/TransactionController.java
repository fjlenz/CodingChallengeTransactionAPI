package com.n26.controller;

import org.springframework.web.bind.annotation.RestController;

import com.n26.model.Transaction;
import com.n26.services.TransactionService;

import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	Logger logger = LoggerFactory.getLogger("jsonConsoleAppender");

	@GetMapping(value = "/transactions")
    public Collection<Transaction> getTransaction() {

		Collection<Transaction> foundTransactions = transactionService.retrieveTransactions();
		
		logger.info("This is the response im sending: '{}'", foundTransactions);
		
        return foundTransactions;
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/transactions")
    public ResponseEntity<Void> addTransaction(@RequestBody Transaction transaction) {
				
		// Validate if amount can be parsed from String to BigDec
		try {  
			new BigDecimal(transaction.getAmount());
		  } catch(NumberFormatException e){
			  //422 – if any of the fields are not parsable
				logger.info("Amount not parsable {}", transaction.getAmount());
			  return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
		  } 
				
		// Validate TimeStamp - can String be parsed to ZonedDateTime?
		ZonedDateTime validatedTimestamp = null;

		try {  
			validatedTimestamp = ZonedDateTime.parse(transaction.getTimestamp()).toInstant().atZone(ZoneOffset.UTC);
		  } catch(DateTimeParseException e){
			  //422 – if any of the fields are not parsable
				logger.info("Timestamp not parsable {}, {}", transaction.getTimestamp());
			  return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
		  } 	
		
		if (validatedTimestamp.isAfter(ZonedDateTime.now(ZoneOffset.UTC))) {
			//422 – if transaction date is in the future
			logger.info("Transaction date is in future {}", transaction.getTimestamp());
			return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
		if (validatedTimestamp.isBefore(ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(60))) {
			//204 – if the transaction is older than 60 seconds
			logger.info("Transaction is older than 60 seconds {}", transaction.getTimestamp());
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		
		transactionService.addTransaction(transaction);
		
		logger.info("Adding Transaction: done - {}", transaction.toString());
	    
		//201 – in case of success
		return new ResponseEntity<Void>(HttpStatus.CREATED);
        
    }
	
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/transactions")
    public ResponseEntity<Void> deleteAllTransaction() {

		int deletedElements = transactionService.deleteAllTransactions();		
		
		logger.info("Deletion done: {}", deletedElements);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
	
}

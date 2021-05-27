package com.n26.controller;

import org.springframework.web.bind.annotation.RestController;

import com.n26.model.Transaction;
import com.n26.services.TransactionService;

import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
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
			  return new ResponseEntity<Void>(HttpStatus.UNPROCESSABLE_ENTITY);
		  } 
		
		transactionService.addTransaction(transaction);
		
		// TODO: Validation timestamp (!= older than 60 secs, ...) - see Reqs. if any other validation needed
	
		logger.info("Adding Transaction: done - {}", transaction.toString());
    
		// TODO: Send specific HTTPStatus depending on validation
		return new ResponseEntity<Void>(HttpStatus.CREATED);
        
    }
	
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/transactions")
    public ResponseEntity<Void> deleteAllTransaction() {

		int deletedElements = transactionService.deleteAllTransactions();		
		
		logger.info("Deletion done: {}", deletedElements);
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
	
}

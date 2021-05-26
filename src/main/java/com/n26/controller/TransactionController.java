package com.n26.controller;

import org.springframework.web.bind.annotation.RestController;

import com.n26.model.Transaction;
import com.n26.services.TransactionService;

import org.slf4j.LoggerFactory;

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
		
		//logger.info("This is the response: '{}'", foundTransactions);
		
        return foundTransactions;
    }
	
	@RequestMapping(method = RequestMethod.POST, value = "/transactions")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
		
		// TODO: Validation (datetime in future, check if amount parsable to bigdec, ...)
		
		Transaction persistedTransaction = transactionService.addTransaction(transaction);
		//logger.info("This is the persisted Transcation: '{}'", persistedTransaction);
		
        return new ResponseEntity<>(persistedTransaction, HttpStatus.CREATED);
        
    }
	
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/transactions")
    public ResponseEntity<Void> deleteAllTransaction() {

		transactionService.deleteAllTransactions();
		
		logger.info("Deletion done");
		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }	
	
}

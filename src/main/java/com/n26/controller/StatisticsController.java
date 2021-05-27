package com.n26.controller;

import org.springframework.web.bind.annotation.RestController;

import com.n26.model.Statistics;
import com.n26.services.TransactionService;

import org.slf4j.LoggerFactory;

import java.util.Collection;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class StatisticsController {

	@Autowired
	private TransactionService transactionService;
	
	Logger logger = LoggerFactory.getLogger("jsonConsoleAppender");

	@GetMapping(value = "/statistics")
    public ResponseEntity<Statistics> getStatistics() {

		Statistics calculatedStatistics = transactionService.retrieveStatistics();
		
		logger.info("This is the statistics im sending: '{}'", calculatedStatistics);
		
        return new ResponseEntity<>(calculatedStatistics, HttpStatus.ACCEPTED);
        
    }

	
}

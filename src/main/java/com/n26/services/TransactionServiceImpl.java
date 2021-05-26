package com.n26.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.n26.model.Statistics;
import com.n26.model.Transaction;

@Service
public class TransactionServiceImpl implements TransactionService{

    private static Collection<Transaction> transactionRepo = new ArrayList<>();

	@Override
	public Transaction addTransaction(Transaction transaction) {
		transactionRepo.add(transaction);
		
		return transaction;
	}

	@Override
	public void deleteAllTransactions() {
		transactionRepo.clear();
	}

	@Override
	public void cleanUpTransactionsOlderThan() {
		// TODO: Remoce Transactions older than 60 seconds
		
	}

	@Override
	public Collection<Transaction> retrieveTransactions() {
		return transactionRepo;
	}

	@Override
	public Statistics retrieveStatistics() {
		// cleanup and delete all Transactions older than 60 second
			// TODO
		cleanUpTransactionsOlderThan();
		
		int sum = transactionRepo.stream()
							.mapToInt(x -> Integer.parseInt(x.getAmount()))
							.sum();
		
		// calculate Statistics
		Statistics calculatedStats = new Statistics();
		calculatedStats.setAvg("12.22");
		calculatedStats.setCount(Long.valueOf(transactionRepo.size()));
		calculatedStats.setMax("44.44");
		calculatedStats.setMin("11.11");
		calculatedStats.setSum(Integer.toString(sum));
		
		return calculatedStats;
		
	}

}


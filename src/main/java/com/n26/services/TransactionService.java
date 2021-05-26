package com.n26.services;

import java.util.Collection;

import com.n26.model.Statistics;
import com.n26.model.Transaction;


public interface TransactionService {

	public abstract Transaction addTransaction(Transaction transaction);
	public abstract void deleteAllTransactions();
	public abstract void cleanUpTransactionsOlderThan();
	public abstract Collection<Transaction> retrieveTransactions();
	public abstract Statistics retrieveStatistics();

}
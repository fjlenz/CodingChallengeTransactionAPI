package com.n26.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

import org.springframework.stereotype.Service;

import com.n26.entity.TransactionEntity;
import com.n26.mapper.TransactionMapper;
import com.n26.model.Statistics;
import com.n26.model.Transaction;

@Service
public class TransactionServiceImpl implements TransactionService{

	// TODO: Verify if ThreadSafe - Or apply Queue/Map or Conc.List
    private static Collection<TransactionEntity> transactionRepo = new ArrayList<>();

	private TransactionMapper transactionMapper = new TransactionMapper(); 

	@Override
	public void addTransaction(Transaction transaction) {
		
		TransactionEntity mappedTransaction = transactionMapper.mapDtoToEntity(transaction);
		transactionRepo.add(mappedTransaction);		
		
	}

	@Override
	public int deleteAllTransactions() {
		int size = transactionRepo.size();
		transactionRepo.clear();
		
		return size;
	}

	@Override
	public void cleanUpTransactionsOlderThan() {
		// TODO: Remove Transactions older than 60 seconds
	}

	@Override
	public Collection<Transaction> retrieveTransactions() {
		
		Collection<Transaction> mappedTransactionList = new ArrayList<>();
		for (TransactionEntity t : transactionRepo) {
			mappedTransactionList.add(transactionMapper.mapEntityToDto(t));
		}
		
		return mappedTransactionList;
	}

	@Override
	public Statistics retrieveStatistics() {
		// cleanup and delete all Transactions older than 60 second
		cleanUpTransactionsOlderThan();
		
		// Calculate Stats
			// TODO: Apply correct Rounding according to Reqs.

		BigDecimal sum = transactionRepo.stream().map(t -> t.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
		 
		BigDecimal avg = sum.divide(new BigDecimal(transactionRepo.size()), RoundingMode.HALF_UP);
		 
		BigDecimal max = transactionRepo.stream()
				 .map(t -> t.getAmount())
				 .max(Comparator.naturalOrder())
				 .orElse(BigDecimal.ZERO);

		BigDecimal min = transactionRepo.stream()
				 .map(t -> t.getAmount())
				 .min(Comparator.naturalOrder())
				 .orElse(BigDecimal.ZERO);

		Statistics calculatedStats = new Statistics();
		calculatedStats.setSum(sum.toString());
		calculatedStats.setAvg(avg.toString());
		calculatedStats.setCount(Long.valueOf(transactionRepo.size()));
		
		calculatedStats.setMax(max.toString());
		calculatedStats.setMin(min.toString());
		
		return calculatedStats;
		
	}

}


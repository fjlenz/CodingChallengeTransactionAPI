package com.n26.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.n26.entity.TransactionEntity;
import com.n26.mapper.TransactionMapper;
import com.n26.model.Statistics;
import com.n26.model.Transaction;

@Service
public class TransactionServiceImpl implements TransactionService{

	// Concurrent & ThreadSafe:
		// assumption: combination of timestamp && amount is not unique - thus Map does not help
		// CopyOnWriteArrayList - not used as we will have more write operations than read operations (copy at every write would happen)
		// used: ConcurrentLinkedQueue - as read/write operations will be around equal
		// looking forward on what u guys to propose to use :)
	private static ConcurrentLinkedQueue<TransactionEntity> transactionRepo = new ConcurrentLinkedQueue<>();
	
	private TransactionMapper transactionMapper = new TransactionMapper(); 

	Logger logger = LoggerFactory.getLogger("jsonConsoleAppender");
	
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
		// Remove Transactions older than 60 seconds		
		logger.info("Number of Transaction before cleanup: {} with date {}", transactionRepo.size(), ZonedDateTime.now(ZoneOffset.UTC).toString());

		transactionRepo.removeIf(t -> t.getTimeStamp().isBefore(ZonedDateTime.now(ZoneOffset.UTC).minusSeconds(60)));
		
		logger.info("Number of Transaction after cleanup: {}", transactionRepo.size());

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
		logger.info("Starting transac. retrieval for stat. calulcation");

		// Cleanup and Delete Transactions older than 60 second
		cleanUpTransactionsOlderThan();

		Statistics calculatedStats = new Statistics();
		
		BigDecimal sum = new BigDecimal(0.00);
		BigDecimal avg = new BigDecimal(0.00);
		BigDecimal max = new BigDecimal(0.00);
		BigDecimal min = new BigDecimal(0.00);
		int cnt = transactionRepo.size();
		
		// only calculate stats if transaction-repo has entries
		if (cnt > 0) {
			sum = transactionRepo.stream().map(t -> t.getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
			logger.info("SumCalculated: '{}', SumRounding: '{}'", sum.toString(), sum.setScale(2, RoundingMode.HALF_UP).toString());
			
			avg = sum.divide(new BigDecimal(transactionRepo.size()),4, RoundingMode.HALF_UP);
			logger.info("AvgCalculated: '{}', AvgRounding: '{}'", avg.toString(), avg.setScale(2, RoundingMode.HALF_UP).toString());
			
			max = transactionRepo.stream()
					 .map(t -> t.getAmount())
					 .max(Comparator.naturalOrder())
					 .orElse(BigDecimal.ZERO);
			logger.info("MaxCalculated: '{}', MaxRounding: '{}'", max.toString(), max.setScale(2, RoundingMode.HALF_UP).toString());
			
			min = transactionRepo.stream()
					 .map(t -> t.getAmount())
					 .min(Comparator.naturalOrder())
					 .orElse(BigDecimal.ZERO);
			logger.info("MinCalculated: '{}', MinRounding: '{}'", min.toString(), min.setScale(2, RoundingMode.HALF_UP).toString());

		}
		
		// set values for response
		calculatedStats.setSum(sum.setScale(2, RoundingMode.HALF_UP).toString());
		calculatedStats.setAvg(avg.setScale(2, RoundingMode.HALF_UP).toString());
		calculatedStats.setCount(Long.valueOf(cnt));
		
		calculatedStats.setMax(max.setScale(2, RoundingMode.HALF_UP).toString());
		calculatedStats.setMin(min.setScale(2, RoundingMode.HALF_UP).toString());

		logger.info("Finishing stat. calulcation");
		
		return calculatedStats;
		
	}

}


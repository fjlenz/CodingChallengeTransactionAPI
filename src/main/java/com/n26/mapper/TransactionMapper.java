package com.n26.mapper;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import com.n26.entity.TransactionEntity;
import com.n26.model.Transaction;

public class TransactionMapper {

	public TransactionEntity mapDtoToEntity (Transaction transaction) {

		TransactionEntity mappedEntity = new TransactionEntity();
		mappedEntity.setAmount(new BigDecimal(transaction.getAmount()));
		mappedEntity.setTimeStamp(ZonedDateTime.parse(transaction.getTimestamp()).toInstant().atZone(ZoneOffset.UTC));
				
		return mappedEntity;
	}
	
	public Transaction mapEntityToDto (TransactionEntity transactionEntity) {
		
		Transaction mappedTransactionDto = new Transaction();
		mappedTransactionDto.setAmount(transactionEntity.getAmount().toString());
		mappedTransactionDto.setTimestamp(transactionEntity.getTimeStamp().toString());		
		
		return mappedTransactionDto;
	}

}
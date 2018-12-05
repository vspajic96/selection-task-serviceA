package com.vedran.servicea.service;

import com.vedran.servicea.domain.Transaction;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface TransactionServiceInterface {

    void performTransaction(Transaction transaction);
    boolean transactionValidAmount(double amount);
    boolean transactionValidCurrency(String currency);

    void sendTransactionPerformedEvent(Transaction transaction);
    BigInteger amountToCents(BigDecimal amountInEuros);
}

package com.vedran.servicea.service;

import com.vedran.servicea.domain.Transaction;
import com.vedran.servicea.event.TransactionPerformedEvent;

public interface TransactionServiceInterface {

    void performTransaction(Transaction transaction);
    boolean transactionValidAmount(double amount);
    boolean transactionValidCurrency(String currency);

    void sendTransactionPerformedEvent(Transaction transaction);
}

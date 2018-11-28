package com.vedran.servicea.service;

import com.vedran.servicea.domain.Transaction;
import com.vedran.servicea.event.EventPublisher;
import com.vedran.servicea.event.TransactionPerformedEvent;
import com.vedran.servicea.exception.InvalidAmountException;
import com.vedran.servicea.exception.InvalidCurrencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;


@Service
class TransactionService implements TransactionServiceInterface{

    private EventPublisher eventPublisher;

    @Autowired
    public TransactionService(final EventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void performTransaction(Transaction transaction) {
        if (transactionValidAmount(transaction.getAmount())) {
            if (transactionValidCurrency(transaction.getCurrency())) {
                sendTransactionPerformedEvent(transaction);
            } else {
                throw new InvalidCurrencyException();
            }
        }
        else {
            throw new InvalidAmountException();
        }
    }

    @Override
    public boolean transactionValidAmount(double amount) {
        return amount >= - 100000000 && amount <= 100000000;
    }

    @Override
    public boolean transactionValidCurrency(String currency) {
        return currency.equals("EUR");
    }

    @Transactional
    @Override
    public void sendTransactionPerformedEvent(Transaction transaction) {
        BigInteger amount = new BigDecimal(transaction.getAmount()).toBigInteger();
        String currency = transaction.getCurrency();
        eventPublisher.send(new TransactionPerformedEvent(amount, currency));
    }


}

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

    /*
        Check if criteria for valid transaction are satisfied then send a TransactionPerformedEvent
     */
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

    /*
        Call the EventPublisher class' send() function to send a RabbitMQ message for consumption in service-b
     */
    @Transactional
    @Override
    public void sendTransactionPerformedEvent(Transaction transaction) {
        BigDecimal amountInEuros = new BigDecimal(transaction.getAmount());
        // Convert euros to cents before publishing message
        BigInteger amountInCents = amountToCents(amountInEuros);
        String currency = transaction.getCurrency();
        eventPublisher.send(new TransactionPerformedEvent(amountInCents, currency));
    }

    @Override
    public BigInteger amountToCents(BigDecimal amountInEuros) {
        amountInEuros = amountInEuros.multiply(new BigDecimal("100"));
        return amountInEuros.toBigInteger();
    }


}

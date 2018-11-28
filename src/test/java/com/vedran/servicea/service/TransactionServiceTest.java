package com.vedran.servicea.service;

import com.vedran.servicea.domain.Transaction;
import com.vedran.servicea.event.EventPublisher;
import com.vedran.servicea.event.TransactionPerformedEvent;
import com.vedran.servicea.exception.InvalidAmountException;
import com.vedran.servicea.exception.InvalidCurrencyException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;


public class TransactionServiceTest {

    private TransactionService transactionService;

    @Mock
    private EventPublisher eventPublisher;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        transactionService = new TransactionService(eventPublisher);
    }

    @Test
    public void transactionValidAmountTest() {
        assertThat(transactionService.transactionValidAmount(50)).isTrue();
        assertThat(transactionService.transactionValidAmount(-50)).isTrue();
        assertThat(transactionService.transactionValidAmount(100000000.1)).isFalse();
        assertThat(transactionService.transactionValidAmount(-100000000.1)).isFalse();
    }

    @Test
    public void transactionValidCurrencyTest() {
        assertThat(transactionService.transactionValidCurrency("EUR")).isTrue();
        assertThat(transactionService.transactionValidCurrency("foo")).isFalse();
    }

    @Test
    public void sendTransactionPerformedEventTest() {
        Transaction transaction = new Transaction(50, "EUR");
        BigInteger amount = new BigDecimal(transaction.getAmount()).toBigInteger();
        String currency = transaction.getCurrency();

        TransactionPerformedEvent event = new TransactionPerformedEvent(amount, currency);
        transactionService.sendTransactionPerformedEvent(transaction);

        verify(eventPublisher).send(eq(event));
    }

    @Test
    public void performInvalidTransactionTest() {
        Throwable e1 = null;
        Throwable e2 = null;

        try {
            Transaction transaction = new Transaction(100000000.1, "EUR");
            transactionService.performTransaction(transaction);
        } catch (Throwable ex) {
            e1 = ex;
        }

        try {
            Transaction transaction = new Transaction(100000000.0, "foo");
            transactionService.performTransaction(transaction);
        } catch (Throwable ex) {
            e2 = ex;
        }

        assertEquals(InvalidAmountException.class, e1.getClass());
        assertEquals(InvalidCurrencyException.class, e2.getClass());
    }




}

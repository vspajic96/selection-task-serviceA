package com.vedran.servicea.controller;

import com.vedran.servicea.domain.Transaction;
import com.vedran.servicea.service.TransactionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    private final TransactionServiceInterface transactionServiceInterface;

    @Autowired
    public TransactionController(final TransactionServiceInterface transactionServiceInterface) {
        this.transactionServiceInterface = transactionServiceInterface;
    }

    /*
        Receive JSON message from POST request body.
        JSON message must be formatted as following:
                 {
                "amount": 10,
                "currency": "EUR"
                }
       where amount is not lower than -100000000 or higher than -100000000, and currency is EUR

       Upon correct request calls TransactionService's performTransaction() method
     */
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
                 produces = {MediaType.APPLICATION_JSON_VALUE},
                 path="/send")
    ResponseEntity<Transaction> makeTransaction(@RequestBody Transaction transaction) {
        transactionServiceInterface.performTransaction(transaction);
        return ResponseEntity.ok(transaction);
    }
}

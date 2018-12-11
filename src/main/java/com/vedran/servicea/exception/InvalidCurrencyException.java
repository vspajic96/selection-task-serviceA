package com.vedran.servicea.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
    Exception to be thrown upon receiving a transaction request with invalid currency
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid currency : Currency must be 'EUR'")
public class InvalidCurrencyException extends RuntimeException{
    public InvalidCurrencyException() {
        super("Currency must be 'EUR'");
    }
}


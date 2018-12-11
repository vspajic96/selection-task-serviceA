package com.vedran.servicea.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/*
    Exception to be thrown upon receiving a transaction request with invalid amount
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid amount : Amount must be between -100000000 and 100000000")
public class InvalidAmountException extends RuntimeException{
    public InvalidAmountException() {
        super("Amount must be between -100000000 and 100000000");
    }
}

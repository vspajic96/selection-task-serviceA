package com.vedran.servicea.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid amount : Amount must be between -100000000 and 100000000")
public class InvalidAmountException extends RuntimeException{
    public InvalidAmountException() {
        super("Amount must be between -100000000 and 100000000");
    }
}

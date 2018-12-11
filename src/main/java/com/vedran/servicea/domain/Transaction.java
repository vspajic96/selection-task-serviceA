package com.vedran.servicea.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/*
    Transaction model
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class Transaction implements Serializable {


    private final double amount;
    private final String currency;

    Transaction() {
        this( 0, null);
    }

}

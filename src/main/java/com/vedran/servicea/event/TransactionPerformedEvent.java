package com.vedran.servicea.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigInteger;

/*
    Model of an event to be sent through RabbitMQ for consumption in service-b
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class TransactionPerformedEvent implements Serializable {

    private final BigInteger amount;
    private final String currency;

}

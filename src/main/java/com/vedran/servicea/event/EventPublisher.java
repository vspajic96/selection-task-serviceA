package com.vedran.servicea.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

    private RabbitTemplate rabbitTemplate;

    private String transactionExchange;

    private String transactionRoutingKey;

    @Autowired
    EventPublisher(final RabbitTemplate rabbitTemplate,
                   @Value("${transaction.exchange}") final String transactionExchange,
                   @Value("${transaction.key}") final String transactionRoutingKey) {

        this.rabbitTemplate = rabbitTemplate;
        this.transactionExchange = transactionExchange;
        this.transactionRoutingKey = transactionRoutingKey;
    }

    public void send(final TransactionPerformedEvent transactionPerformedEvent) {
        rabbitTemplate.convertAndSend(transactionExchange, transactionRoutingKey, transactionPerformedEvent);
    }

}

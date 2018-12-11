# selection-task-serviceA
This microservice is developed using Spring Boot, and it's tasked with receiving HTTP POST requests with JSON payload containing information of the transaction the user wants to perform.
It is also tasked with 
JSON payload must be formatted as in following example: 
```           
   {
    "amount": 10.45,
    "currency": "EUR"
   }
               
```
Amount must not be lower than -100000000 or higher than -100000000, and currency must be "EUR".

Valid requests will generate HTTP/200 responses, while invalid requests will generate HTTP/400 responses.

Upon receiving a valid request, the service generates AMQP messages, in which the amount is sent as minimal currency denomination representation(e.g. cents).
The message, represented by a `TransactionPerformedEvent`, is then logged to the console and sent to the defined RabbitMQ exchange for consumption in Service B.


Rest API has a single endpoint, `/send`, that receives POST requests with previously described JSON payload.

To run Service B you **must** have:
1. RabbitMQ server running with default configuration

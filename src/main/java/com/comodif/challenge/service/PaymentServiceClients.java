package com.comodif.challenge.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Service
public class PaymentServiceClients {

    private final PaymentService paymentService;

    public PaymentServiceClients(PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @Async
    public CompletableFuture<String> call(BigDecimal price) {
        paymentService.pay(price);
        return CompletableFuture.completedFuture("success");
    }
}

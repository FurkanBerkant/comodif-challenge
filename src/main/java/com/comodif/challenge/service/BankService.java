package com.comodif.challenge.service;

import com.comodif.challenge.entity.dto.BankPaymentRequest;
import com.comodif.challenge.entity.dto.BankPaymentResponse;
import org.springframework.stereotype.Service;

@Service
public class BankService {

    /**
     * Bank Latency Simulation (avg: 5 seconds)
     */
    public BankPaymentResponse pay(BankPaymentRequest request) {
        try {
            Thread.sleep(5000);
            return new BankPaymentResponse("200");
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }
}

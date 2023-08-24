package com.comodif.challenge.controller;

import com.comodif.challenge.entity.dto.BankPaymentRequest;
import com.comodif.challenge.exception.PaymentFailedException;
import com.comodif.challenge.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<String> makePayment(@RequestBody BankPaymentRequest paymentRequest) {
        try {
            BigDecimal amount = paymentRequest.getPrice();

            paymentService.pay(amount);
            paymentService.bookSeat(paymentRequest.getSeatId());

            return ResponseEntity.ok("Rezerve edilen tum koltuklar icin odeme başarili.");
        } catch (PaymentFailedException e) {
            return ResponseEntity.badRequest().body("Odeme basarisiz oldu: " + e.getMessage());
        }
    }
}

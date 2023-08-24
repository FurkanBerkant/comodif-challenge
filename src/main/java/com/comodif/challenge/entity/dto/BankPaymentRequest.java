package com.comodif.challenge.entity.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankPaymentRequest {

    private BigDecimal price;
    private Long seatId;
    private Long flightId;


}

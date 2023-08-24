package com.comodif.challenge.entity.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentDto {
        private BigDecimal price;
        private String bankResponse;
        private Long seatId;
}

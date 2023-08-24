package com.comodif.challenge.entity.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class SeatRequest {
    private String seatNumber;
    private boolean booked;
    private BigDecimal price;
    private Long flightId;
}

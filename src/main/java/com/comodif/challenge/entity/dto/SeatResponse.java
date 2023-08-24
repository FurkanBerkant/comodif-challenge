package com.comodif.challenge.entity.dto;

import lombok.Data;

@Data
public class SeatResponse {
    private Long seatId;
    private String seatNumber;
    private boolean booked;
    private FlightResponse flight;
}

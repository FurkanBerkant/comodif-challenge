package com.comodif.challenge.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class FlightResponse {
    private Long flightId;
    private Long seatId;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
}

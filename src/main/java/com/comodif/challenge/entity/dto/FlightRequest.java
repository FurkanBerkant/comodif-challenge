package com.comodif.challenge.entity.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FlightRequest {

    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
}

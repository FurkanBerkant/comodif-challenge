package com.comodif.challenge.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Seat {
    @Id
    @GeneratedValue
    private Long seatId;
    private String seatNumber;
    private boolean booked;
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "flightId")
    private Flight flight;
}

package com.comodif.challenge.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue
    private Long paymentId;
    private BigDecimal price;
    private String bankResponse;
    @ManyToOne
    @JoinColumn(name = "seatId")
    private Seat seat;
}

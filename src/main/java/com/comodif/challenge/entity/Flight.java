package com.comodif.challenge.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Flight {
    @Id
    @GeneratedValue
    private Long flightId;
    private String flightNumber;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    @JsonIgnore
    @OneToMany(mappedBy = "flight", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Seat> seats = new ArrayList<>();
}

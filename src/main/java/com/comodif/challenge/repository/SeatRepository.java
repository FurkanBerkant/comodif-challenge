package com.comodif.challenge.repository;

import com.comodif.challenge.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByBookedTrue();
    boolean existsBySeatNumberAndBookedIsTrue(String seatNumber);

    List<Seat> findByFlightFlightId(Long flightId);

}

package com.comodif.challenge.service.abstracts;

import com.comodif.challenge.entity.Seat;
import com.comodif.challenge.entity.dto.SeatRequest;
import com.comodif.challenge.entity.dto.SeatResponse;

import java.util.List;

public interface SeatService {
    public List<Seat> findAll();
    public Seat findById(Long id);
    public Seat save(SeatRequest seatDto);
    public void deleteById(Long id);
    public Seat update(Long id,SeatRequest seatRequest);
    public boolean isSeatBooked(String seatNumber);
    public List<SeatResponse> findByFlightId(Long flightId);
}

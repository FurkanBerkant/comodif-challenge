package com.comodif.challenge.service.abstracts;

import com.comodif.challenge.entity.Flight;
import com.comodif.challenge.entity.dto.FlightRequest;
import com.comodif.challenge.entity.dto.FlightResponse;

import java.util.List;

public interface FlightService {
    public List<FlightResponse> findAll();
    public FlightResponse findById(Long id);
    public Flight save(FlightRequest flightDto);
    public void deleteById(Long id);
    public Flight update(Long id,FlightRequest flightRequest);

}

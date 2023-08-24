package com.comodif.challenge.controller;

import com.comodif.challenge.entity.Flight;
import com.comodif.challenge.entity.dto.FlightRequest;
import com.comodif.challenge.entity.dto.FlightResponse;
import com.comodif.challenge.entity.dto.SeatResponse;
import com.comodif.challenge.service.abstracts.FlightService;
import com.comodif.challenge.service.abstracts.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flights")
@AllArgsConstructor
public class FlightController {
    private final FlightService flightService;
    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<List<FlightResponse>> getAllFlight(){
        return new ResponseEntity<>(flightService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<FlightResponse> getFlightById(@PathVariable Long id){
        return new ResponseEntity<>(flightService.findById(id), HttpStatus.OK);
    }
    @GetMapping("/{flightId}/seats")
    public ResponseEntity<List<SeatResponse>> getSeatsForFlight(@PathVariable Long flightId) {
        List<SeatResponse> seatResponses = seatService.findByFlightId(flightId);
        return new ResponseEntity<>(seatResponses, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Flight> saveFlight(@RequestBody FlightRequest flightDto){
        return new ResponseEntity<>(flightService.save(flightDto),HttpStatus.CREATED);

    }
    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id,@RequestBody FlightRequest flightRequest){
        Flight updatedFlight=flightService.update(id,flightRequest);
        if (updatedFlight != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedFlight);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id){
        flightService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
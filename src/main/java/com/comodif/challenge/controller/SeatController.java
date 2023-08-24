package com.comodif.challenge.controller;
import com.comodif.challenge.entity.Seat;
import com.comodif.challenge.entity.dto.SeatRequest;
import com.comodif.challenge.exception.FlightNotFoundException;
import com.comodif.challenge.service.abstracts.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@AllArgsConstructor
public class SeatController {
    private final SeatService seatService;

    @GetMapping
    public ResponseEntity<List<Seat>> getAllSeat(){
        return new ResponseEntity<>(seatService.findAll(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable Long id){
        return new ResponseEntity<>(seatService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveSeat(@RequestBody SeatRequest seatDto) {

        try {
            Seat savedSeat = seatService.save(seatDto);
            return new ResponseEntity<>(savedSeat, HttpStatus.CREATED);
        } catch (FlightNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ucus bulunamadi.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Seat> updateSeat(@PathVariable Long id,@RequestBody SeatRequest seatRequest){
        Seat updatedSeat=seatService.update(id,seatRequest);
        if (updatedSeat!= null){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(updatedSeat);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id){
        seatService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}

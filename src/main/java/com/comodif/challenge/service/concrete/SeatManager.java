package com.comodif.challenge.service.concrete;

import com.comodif.challenge.configuration.dtoConverter.DtoConverterService;
import com.comodif.challenge.entity.Flight;
import com.comodif.challenge.entity.Seat;
import com.comodif.challenge.entity.dto.SeatRequest;
import com.comodif.challenge.entity.dto.SeatResponse;
import com.comodif.challenge.exception.FlightNotFoundException;
import com.comodif.challenge.exception.SeatNotFoundException;
import com.comodif.challenge.repository.FlightRepository;
import com.comodif.challenge.repository.SeatRepository;
import com.comodif.challenge.service.abstracts.SeatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class SeatManager implements SeatService {

    private final SeatRepository seatRepository;
    private final FlightRepository flightRepository;
    private DtoConverterService dtoConverterService;

    @Override
    public List<Seat> findAll() {
        return seatRepository.findAll();
    }

    @Override
    public Seat findById(Long id) {
        Optional<Seat> seat = seatRepository.findById(id);
        return seat.orElseGet(() -> isSeatAvailable(id));
    }

    @Override
    public Seat save(SeatRequest seatDto) {

        Flight flight = flightRepository.findById(seatDto.getFlightId())
                .orElseThrow(() -> new FlightNotFoundException("Ucus bulunamadi"));
        String seatNumber = seatDto.getSeatNumber();

        if (isSeatBooked(seatNumber)) {
            throw new SeatNotFoundException("Koltuk zaten rezerve edilmis.");
        }
        boolean isSeatBookedOnFlight = flight.getSeats().stream()
                .anyMatch(seat -> seat.isBooked() && seat.getSeatNumber().equals(seatDto.getSeatNumber()));
        if (isSeatBookedOnFlight) {
            throw new IllegalArgumentException("Bu koltuk secilen ucus icin zaten rezerve edilmis.");
        }

        Seat seat = dtoConverterService.dtoClassConverter(seatDto, Seat.class);
        seat.setFlight(flight);
        return seatRepository.save(seat);
    }


    @Override
    public void deleteById(Long id) {
        Optional<Seat> seatOptional = seatRepository.findById(id);

        if (seatOptional.isPresent()) {
            Seat seat = seatOptional.get();
            Flight flight = seat.getFlight();

            if (flight != null) {
                flight.getSeats().remove(seat);
                flightRepository.save(flight);
            }

            seatRepository.deleteById(id);
        } else {
            isSeatAvailable(id);
        }
    }

    @Override
    public Seat update(Long id, SeatRequest seatRequest) {
        Optional<Seat> seatOptional = seatRepository.findById(id);
        if (seatOptional.isPresent()) {
            Seat foundSeat = seatOptional.get();
            foundSeat.setSeatNumber(seatRequest.getSeatNumber());
            foundSeat.setBooked(seatRequest.isBooked());
            foundSeat.setPrice(seatRequest.getPrice());
            return seatRepository.save(foundSeat);
        } else {
            return isSeatAvailable(id);
        }
    }

    @Override
    public boolean isSeatBooked(String  seatNumber){
        return seatRepository.existsBySeatNumberAndBookedIsTrue(seatNumber);
    }

    @Override
    public List<SeatResponse> findByFlightId(Long flightId) {
        List<Seat> seats = seatRepository.findByFlightFlightId(flightId);

        return seats.stream()
                .map(seat -> dtoConverterService.dtoClassConverter(seat, SeatResponse.class))
                .collect(Collectors.toList());
    }

    public Seat isSeatAvailable(Long id){
        return seatRepository.findById(id)
                .orElseThrow(() -> new SeatNotFoundException("Koltuk bulunamadi"));
    }
}

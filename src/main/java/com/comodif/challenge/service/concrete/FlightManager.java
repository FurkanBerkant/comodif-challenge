package com.comodif.challenge.service.concrete;

import com.comodif.challenge.configuration.dtoConverter.DtoConverterService;
import com.comodif.challenge.entity.Flight;
import com.comodif.challenge.entity.dto.FlightRequest;
import com.comodif.challenge.entity.dto.FlightResponse;
import com.comodif.challenge.exception.FlightNotFoundException;
import com.comodif.challenge.repository.FlightRepository;
import com.comodif.challenge.service.abstracts.FlightService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FlightManager implements FlightService {
    private final FlightRepository flightRepository;
    private DtoConverterService dtoConverterService;

    @Override
    public List<FlightResponse> findAll() {

        List<Flight> flights = flightRepository.findAll();
        return dtoConverterService.dtoConverter(flights, FlightResponse.class);
    }

    @Override
    public FlightResponse findById(Long id) {
        return dtoConverterService.dtoClassConverter(isFlightAvailable(id), FlightResponse.class);
    }

    @Override
    public Flight save(FlightRequest flightDto) {
        Flight flight = dtoConverterService.dtoClassConverter(flightDto, Flight.class);
        flight.getSeats().forEach(seat -> seat.setFlight(flight));
        return flightRepository.save(flight);
    }
    @Override
    public void deleteById(Long id) {
        flightRepository.deleteById(id);

    }
    @Override
    public Flight update(Long id,FlightRequest flightRequest) {
        Optional<Flight> flight = flightRepository.findById(id);
        if(flight.isPresent()){
            Flight foundFlight = flight.get();
            foundFlight.setDestination(flightRequest.getDestination());
            foundFlight.setFlightNumber(flightRequest.getFlightNumber());
            foundFlight.setOrigin(flightRequest.getOrigin());
            foundFlight.setArrivalTime(flightRequest.getArrivalTime());
            foundFlight.setDepartureTime(flightRequest.getDepartureTime());
            return flightRepository.save(foundFlight);
        }
        else {
            return isFlightAvailable(id);
        }
    }

    public Flight isFlightAvailable(Long id){
        return flightRepository.findById(id)
                .orElseThrow(() -> new FlightNotFoundException("Ucus bulunamadi"));
    }
}


package com.jaideep.bookingservice.service.serviceimpl;

import com.jaideep.bookingservice.entity.BookingStatus;
import com.jaideep.bookingservice.entity.FlightBooking;
import com.jaideep.bookingservice.external.FlightService;
import com.jaideep.bookingservice.model.BookingRequest;
import com.jaideep.bookingservice.model.BookingResponse;
import com.jaideep.bookingservice.model.FlightBookingRequest;
import com.jaideep.bookingservice.model.FlightBookingResponse;
import com.jaideep.bookingservice.repository.FlightBookingRepository;
import com.jaideep.bookingservice.service.BookingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
@Qualifier("flightBookingService")
@Log4j2
@RequiredArgsConstructor
public class FlightBookingService implements BookingService {
    private final FlightBookingRepository flightBookingRepository;
//    private final FlightService flightService;
    private final RestTemplate restTemplate;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        log.info("Create booking for passenger {}", bookingRequest.getPassengerName());
        if (!(bookingRequest instanceof FlightBookingRequest)) {
            throw new IllegalArgumentException("Invalid booking type");
        }

        FlightBooking flightBooking = mapToFlightBooking(bookingRequest);
        flightBooking = flightBookingRepository.save(flightBooking);

        log.info("Booking status is {}", flightBooking.getStatus());

        FlightBookingResponse flightBookingResponse = new FlightBookingResponse();
        BeanUtils.copyProperties(flightBooking, flightBookingResponse);
        this.reserveSeats(bookingRequest);
        return flightBookingResponse;
    }

    @Override
    public String reserveSeats(BookingRequest bookingRequest) {
        FlightBookingRequest flightBookingRequest = (FlightBookingRequest) bookingRequest;
        restTemplate.put("http://FLIGHT-SERVICE/v1/api/flight/reserveSeats/" + flightBookingRequest.getFlightNumber() + "?seats={seats}", null, flightBookingRequest.getSeats());
 //       flightService.reserveSeats(flightBookingRequest.getFlightNumber(), flightBookingRequest.getSeats());
        log.info("Seats are reserved for booking {}", flightBookingRequest.getFlightNumber());
        return "Booking Id created successfully";
    }

    private FlightBooking mapToFlightBooking(BookingRequest bookingRequest) {
        FlightBookingRequest flightBookingRequest = (FlightBookingRequest) bookingRequest;
        FlightBooking flightBooking = new FlightBooking();
        flightBooking.setBookingNumber(UUID.randomUUID().toString());
        flightBooking.setFlightNumber(flightBookingRequest.getFlightNumber());

        flightBooking.setBookingDate(LocalDate.now());
        flightBooking.setPassengerName(flightBookingRequest.getPassengerName());

        flightBooking.setAmount(flightBookingRequest.getAmount());
        flightBooking.setPaymentMode(flightBookingRequest.getPaymentMode());
        flightBooking.setStatus(BookingStatus.CREATED.name());
        flightBooking.setSeats(flightBookingRequest.getSeats());
        return flightBooking;
    }
}

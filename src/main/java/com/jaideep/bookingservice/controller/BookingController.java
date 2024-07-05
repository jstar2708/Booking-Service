package com.jaideep.bookingservice.controller;

import com.jaideep.bookingservice.entity.BookingStatus;
import com.jaideep.bookingservice.model.BookingResponse;
import com.jaideep.bookingservice.model.FlightBookingRequest;
import com.jaideep.bookingservice.model.FlightBookingResponse;
import com.jaideep.bookingservice.model.HotelBookingRequest;
import com.jaideep.bookingservice.service.BookingService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/v1/api/booking")
@Log4j2
public class BookingController {
    @Qualifier("hotelBookingService")
    private final BookingService hotelBookingService;
    private final BookingService flightBookingService;

    public BookingController(@Qualifier("flightBookingService") BookingService flightBookingService, @Qualifier("hotelBookingService") BookingService hotelBookingService) {
        this.flightBookingService = flightBookingService;
        this.hotelBookingService = hotelBookingService;
    }

    @PostMapping("/flight")
    @CircuitBreaker(name = "bookingService", fallbackMethod = "getDefaultFlightBooking")
    public BookingResponse createFlightBooking(@RequestBody FlightBookingRequest flightBookingRequest) {
        log.info("save {} ", flightBookingRequest.getFlightNumber());
        return flightBookingService.createBooking(flightBookingRequest);
    }

    BookingResponse getDefaultFlightBooking(Exception e) {
        FlightBookingResponse flightBookingResponse = new FlightBookingResponse();
        flightBookingResponse.setBookingDate(LocalDate.now());
        flightBookingResponse.setDepartureDate(LocalDate.now());
        flightBookingResponse.setBookingNumber("O");
        flightBookingResponse.setId(0L);
        flightBookingResponse.setStatus(BookingStatus.UNABLE_TO_BOOK.name());
        flightBookingResponse.setAmount(0);
        return flightBookingResponse;
    }

    @PostMapping("/hotel")
    public BookingResponse createHotelBooking(@RequestBody HotelBookingRequest hotelBookingRequest) {
        log.info("save {} ", hotelBookingRequest.getHotelName());
        return hotelBookingService.createBooking(hotelBookingRequest);
    }
}

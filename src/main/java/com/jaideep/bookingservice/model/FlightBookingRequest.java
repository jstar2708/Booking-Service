package com.jaideep.bookingservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public final class FlightBookingRequest extends BookingRequest {
    private String flightNumber;
    private int seats;
    private LocalDate departureDate;
}

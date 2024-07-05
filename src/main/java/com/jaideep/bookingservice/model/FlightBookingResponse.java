package com.jaideep.bookingservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public final class FlightBookingResponse extends BookingResponse {
    private String flightNumber;
    private LocalDate departureDate;
}

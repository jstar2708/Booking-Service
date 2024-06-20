package com.jaideep.bookingservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public final class FlightBookingRequest extends BookingRequest {
    private String flightNumber;
    private int seats;
}

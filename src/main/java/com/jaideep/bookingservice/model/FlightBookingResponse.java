package com.jaideep.bookingservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public final class FlightBookingResponse extends BookingResponse {
    private String flightNumber;
}

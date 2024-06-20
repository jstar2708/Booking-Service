package com.jaideep.bookingservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public final class HotelBookingRequest extends BookingRequest {
    private String hotelName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}

package com.jaideep.bookingservice.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
@Data
@EqualsAndHashCode(callSuper = true)
public final class HotelBookingResponse extends BookingResponse {
    private String hotelName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}

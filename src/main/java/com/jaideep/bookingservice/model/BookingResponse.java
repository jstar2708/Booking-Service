package com.jaideep.bookingservice.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public sealed class BookingResponse permits FlightBookingResponse, HotelBookingResponse {
    private Long id;
    private String bookingNumber;
    private String passengerName;
    private String status;
    private double amount;
    private LocalDate bookingDate;
}

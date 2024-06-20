package com.jaideep.bookingservice.model;

import lombok.Data;

@Data
public sealed class BookingRequest permits FlightBookingRequest, HotelBookingRequest {
    String passengerName;
    double amount;
    PaymentMode paymentMode;
}

package com.jaideep.bookingservice.service;

import com.jaideep.bookingservice.model.BookingRequest;
import com.jaideep.bookingservice.model.BookingResponse;

public interface BookingService {
    BookingResponse createBooking(BookingRequest bookingRequest);

    String reserveSeats(BookingRequest bookingRequest);
}

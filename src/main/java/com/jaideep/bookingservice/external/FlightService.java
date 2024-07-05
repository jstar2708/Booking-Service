package com.jaideep.bookingservice.external;

import com.jaideep.bookingservice.exception.BookingException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "FS", url = "http://localhost:8082/v1/api/flight", value = "FS")
public interface FlightService {
    @PutMapping("/reserveSeats/{id}")
    void reserveSeats(@PathVariable("id") String flightNumber, @RequestParam int seats);

    default void fallback(Exception e) {
        throw new BookingException("Flight Service is not available", "Unavailable", 500);
    }
}

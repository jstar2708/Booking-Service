package com.jaideep.bookingservice.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("HOTEL")
public class HotelBooking extends Booking {
    private String hotelName;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}

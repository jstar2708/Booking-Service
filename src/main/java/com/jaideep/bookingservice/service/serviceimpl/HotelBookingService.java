package com.jaideep.bookingservice.service.serviceimpl;

import com.jaideep.bookingservice.entity.BookingStatus;
import com.jaideep.bookingservice.entity.HotelBooking;
import com.jaideep.bookingservice.model.BookingRequest;
import com.jaideep.bookingservice.model.BookingResponse;
import com.jaideep.bookingservice.model.HotelBookingRequest;
import com.jaideep.bookingservice.model.HotelBookingResponse;
import com.jaideep.bookingservice.repository.HotelBookingRepository;
import com.jaideep.bookingservice.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@Qualifier("hotelBookingService")
@RequiredArgsConstructor
public class HotelBookingService implements BookingService {
    private final HotelBookingRepository hotelBookingRepository;

    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        if (!(bookingRequest instanceof HotelBookingRequest)) {
            throw new IllegalArgumentException(("Invalid booking type"));
        }
        HotelBooking hotelBooking = mapToHotelBooking(bookingRequest);

        hotelBooking = hotelBookingRepository.save(hotelBooking);

        HotelBookingResponse hotelBookingResponse = new HotelBookingResponse();
        BeanUtils.copyProperties(hotelBooking, hotelBookingResponse);
        return hotelBookingResponse;
    }

    @Override
    public String reserveSeats(BookingRequest bookingRequest) {
        return null;
    }

    private HotelBooking mapToHotelBooking(BookingRequest bookingRequest) {
        HotelBookingRequest hotelBookingRequest = (HotelBookingRequest) bookingRequest;
        HotelBooking hotelBooking = new HotelBooking();
        hotelBooking.setBookingNumber(UUID.randomUUID().toString());
        hotelBooking.setBookingDate(LocalDate.now());

        hotelBooking.setPassengerName(hotelBookingRequest.getPassengerName());
        hotelBooking.setAmount(hotelBookingRequest.getAmount());
        hotelBooking.setPaymentMode(hotelBookingRequest.getPaymentMode());
        hotelBooking.setStatus(BookingStatus.CREATED.name());

        hotelBooking.setHotelName(hotelBookingRequest.getHotelName());
        hotelBooking.setCheckInDate(hotelBookingRequest.getCheckInDate());
        hotelBooking.setCheckOutDate(hotelBookingRequest.getCheckOutDate());

        return hotelBooking;
    }
}

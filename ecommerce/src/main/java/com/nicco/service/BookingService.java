package com.nicco.service;

import com.nicco.model.Booking;
import com.nicco.enums.BookingStatus;
import java.util.List;

public interface BookingService {
    Booking createBooking(Booking booking);
    Booking updateBooking(Booking booking);
    Booking getById(Integer id);
    List<Booking> getByCustomerId(Integer customerId);
    List<Booking> getByStatus(BookingStatus status);
    Booking confirmBooking(Integer id);
    Booking cancelBooking(Integer id);
    Booking completeBooking(Integer id);
}
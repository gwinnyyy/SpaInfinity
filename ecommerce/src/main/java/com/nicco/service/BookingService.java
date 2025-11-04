package com.nicco.service;

import com.nicco.model.Booking;
import java.util.List;

public interface BookingService {
    
    Booking createBooking(Booking booking);
    Booking getById(Integer id);
    List<Booking> getByEmail(String email);
    List<Booking> getByPhone(String phone);
    Booking cancelBooking(Integer id);
    
    List<Booking> getAllBookings();
    Booking confirmBooking(Integer id);
    Booking completeBooking(Integer id);
}
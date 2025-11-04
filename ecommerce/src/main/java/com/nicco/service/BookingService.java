package com.nicco.service;

import com.nicco.model.BookingRequest;
import com.nicco.model.BookingResponse;
import java.util.List;

public interface BookingService {
    List<BookingResponse> getAllBookings();
    BookingResponse createBooking(BookingRequest bookingRequest);
}
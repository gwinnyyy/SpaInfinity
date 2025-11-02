package com.nicco.model;

import java.time.LocalDateTime;
import java.util.List;

public class Booking {
    int id;
    String customerId;
    LocalDateTime bookingDate;
    List<BookingItem> items;
}

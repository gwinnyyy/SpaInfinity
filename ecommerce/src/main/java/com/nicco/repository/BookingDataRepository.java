package com.nicco.repository;

import com.nicco.entity.BookingData;
import org.springframework.data.repository.CrudRepository;
import java.time.LocalDateTime;
import java.util.List;


public interface BookingDataRepository extends CrudRepository<BookingData, Integer> {
    
    List<BookingData> findByCustomerEmail(String email);
    
    List<BookingData> findByCustomerPhone(String phone);
    
    List<BookingData> findByStatus(String status);
    
    List<BookingData> findByBookingDateBetween(LocalDateTime start, LocalDateTime end);
    
    List<BookingData> findByCustomerEmailAndStatus(String email, String status);
    List<BookingData> findByCustomerPhoneAndStatus(String phone, String status);
}
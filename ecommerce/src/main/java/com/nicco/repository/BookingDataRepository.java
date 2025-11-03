package com.nicco.repository;

import com.nicco.entity.BookingData;
import com.nicco.enums.BookingStatus;
import org.springframework.data.repository.CrudRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingDataRepository extends CrudRepository<BookingData, Integer> {
    List<BookingData> findByCustomerId(Integer customerId);
    List<BookingData> findByStatus(BookingStatus status);
    List<BookingData> findByBookingDateBetween(LocalDateTime start, LocalDateTime end);
    List<BookingData> findByCustomerIdAndStatus(Integer customerId, BookingStatus status);
}
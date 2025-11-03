package com.nicco.repository;

import com.nicco.entity.BookingItemData;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface BookingItemDataRepository extends CrudRepository<BookingItemData, Integer> {
    List<BookingItemData> findByBookingId(Integer bookingId);
    List<BookingItemData> findByCustomerId(Integer customerId);
}
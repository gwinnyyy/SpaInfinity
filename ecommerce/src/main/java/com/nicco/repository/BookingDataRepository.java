package com.nicco.repository;

import com.nicco.entity.BookingData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDataRepository extends JpaRepository<BookingData, Long> {
}
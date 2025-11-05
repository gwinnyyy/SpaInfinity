package com.nicco.repository;

import com.nicco.entity.AvailableTimeSlotData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AvailableTimeSlotDataRepository extends JpaRepository<AvailableTimeSlotData, Long> {

    List<AvailableTimeSlotData> findByIsBookedFalse();
    
    boolean existsBySlotDateAndStartTime(LocalDate slotDate, LocalTime startTime);
}
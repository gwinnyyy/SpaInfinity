package com.nicco.repository;

import com.nicco.entity.AvailableTimeSlotData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AvailableTimeSlotDataRepository extends JpaRepository<AvailableTimeSlotData, Long> {

    List<AvailableTimeSlotData> findByIsBookedFalse();
    
    @Query("SELECT a FROM AvailableTimeSlotData a WHERE a.isBooked = false AND a.slotDate >= :currentDate ORDER BY a.slotDate, a.startTime")
    List<AvailableTimeSlotData> findAvailableFutureSlots(LocalDate currentDate);
    
    boolean existsBySlotDateAndStartTime(LocalDate slotDate, LocalTime startTime);
}
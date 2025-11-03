package com.nicco.repository;

import com.nicco.entity.TimeSlotData;
import org.springframework.data.repository.CrudRepository;
import java.time.LocalDate;
import java.util.List;

public interface TimeSlotDataRepository extends CrudRepository<TimeSlotData, Integer> {
    List<TimeSlotData> findBySlotDateAndIsAvailable(LocalDate date, boolean available);
    List<TimeSlotData> findByTherapistIdAndSlotDate(Integer therapistId, LocalDate date);
    List<TimeSlotData> findBySlotDateBetweenAndIsAvailable(
        LocalDate startDate, LocalDate endDate, boolean available);
}
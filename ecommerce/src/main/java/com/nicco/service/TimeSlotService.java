package com.nicco.service;

import com.nicco.model.TimeSlot;
import java.time.LocalDate;
import java.util.List;

public interface TimeSlotService {
    List<TimeSlot> getAvailableSlots(LocalDate date);
    List<TimeSlot> getSlotsByTherapist(Integer therapistId, LocalDate date);
    TimeSlot getById(Integer id);
    TimeSlot bookSlot(Integer slotId);
    TimeSlot releaseSlot(Integer slotId);
    List<TimeSlot> generateSlotsForDate(LocalDate date);
}
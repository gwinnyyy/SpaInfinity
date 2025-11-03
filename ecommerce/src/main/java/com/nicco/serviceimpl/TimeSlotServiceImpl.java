package com.nicco.serviceimpl;

import com.nicco.entity.TimeSlotData;
import com.nicco.model.TimeSlot;
import com.nicco.repository.TimeSlotDataRepository;
import com.nicco.service.TimeSlotService;
import com.nicco.util.Transform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TimeSlotServiceImpl implements TimeSlotService {

    @Autowired
    private TimeSlotDataRepository timeSlotRepository;

    private final Transform<TimeSlotData, TimeSlot> toModel = new Transform<>(TimeSlot.class);
    private final Transform<TimeSlot, TimeSlotData> toEntity = new Transform<>(TimeSlotData.class);

    @Override
    public List<TimeSlot> getAvailableSlots(LocalDate date) {
        List<TimeSlot> slots = new ArrayList<>();
        timeSlotRepository.findBySlotDateAndIsAvailable(date, true)
            .forEach(data -> slots.add(toModel.transform(data)));
        return slots;
    }

    @Override
    public List<TimeSlot> getSlotsByTherapist(Integer therapistId, LocalDate date) {
        List<TimeSlot> slots = new ArrayList<>();
        timeSlotRepository.findByTherapistIdAndSlotDate(therapistId, date)
            .forEach(data -> slots.add(toModel.transform(data)));
        return slots;
    }

    @Override
    public TimeSlot getById(Integer id) {
        Optional<TimeSlotData> optional = timeSlotRepository.findById(id);
        return optional.map(data -> toModel.transform(data)).orElse(null);
    }

    @Override
    public TimeSlot bookSlot(Integer slotId) {
        Optional<TimeSlotData> optional = timeSlotRepository.findById(slotId);
        if (optional.isPresent()) {
            TimeSlotData data = optional.get();
            data.setAvailable(false);
            TimeSlotData updated = timeSlotRepository.save(data);
            return toModel.transform(updated);
        }
        return null;
    }

    @Override
    public TimeSlot releaseSlot(Integer slotId) {
        Optional<TimeSlotData> optional = timeSlotRepository.findById(slotId);
        if (optional.isPresent()) {
            TimeSlotData data = optional.get();
            data.setAvailable(true);
            TimeSlotData updated = timeSlotRepository.save(data);
            return toModel.transform(updated);
        }
        return null;
    }

    @Override
    public List<TimeSlot> generateSlotsForDate(LocalDate date) {
        List<TimeSlot> generatedSlots = new ArrayList<>();
        
        // Generate slots from 9 AM to 7 PM (every hour)
        for (int hour = 9; hour <= 19; hour++) {
            TimeSlotData slotData = new TimeSlotData();
            slotData.setSlotDate(date);
            slotData.setSlotTime(LocalTime.of(hour, 0));
            slotData.setAvailable(true);
            
            TimeSlotData saved = timeSlotRepository.save(slotData);
            generatedSlots.add(toModel.transform(saved));
        }
        
        return generatedSlots;
    }
}
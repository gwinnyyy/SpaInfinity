package com.nicco.serviceimpl;

import com.nicco.entity.AvailableTimeSlotData;
import com.nicco.repository.AvailableTimeSlotDataRepository;
import com.nicco.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {

    @Autowired
    private AvailableTimeSlotDataRepository timeSlotRepository;

    @Override
    public List<AvailableTimeSlotData> getAvailableTimeSlots() {
        System.out.println("Fetching available time slots...");
        
        List<AvailableTimeSlotData> slots = timeSlotRepository.findByIsBookedFalse();
        
        System.out.println("Found " + slots.size() + " unbooked slots");
        
        List<AvailableTimeSlotData> futureSlots = slots.stream()
                .filter(slot -> !slot.getSlotDate().isBefore(LocalDate.now()))
                .sorted(Comparator
                        .comparing(AvailableTimeSlotData::getSlotDate)
                        .thenComparing(AvailableTimeSlotData::getStartTime))
                .collect(Collectors.toList());
        
        System.out.println("Returning " + futureSlots.size() + " future available slots");
        
        return futureSlots;
    }
}
package com.nicco.serviceimpl;

import com.nicco.entity.AvailableTimeSlotData;
import com.nicco.repository.AvailableTimeSlotDataRepository;
import com.nicco.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeSlotServiceImpl implements TimeSlotService {

    @Autowired
    private AvailableTimeSlotDataRepository timeSlotRepository;

    @Override
    public List<AvailableTimeSlotData> getAvailableTimeSlots() {
        return timeSlotRepository.findByIsBookedFalse();
    }
}
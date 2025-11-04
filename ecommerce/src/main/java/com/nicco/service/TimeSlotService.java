package com.nicco.service;

import com.nicco.entity.AvailableTimeSlotData;
import java.util.List;

public interface TimeSlotService {
    List<AvailableTimeSlotData> getAvailableTimeSlots();
}
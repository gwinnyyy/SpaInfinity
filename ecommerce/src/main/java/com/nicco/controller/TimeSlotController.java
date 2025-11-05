package com.nicco.controller;

import com.nicco.entity.AvailableTimeSlotData;
import com.nicco.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/timeslots")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class TimeSlotController {

    @Autowired
    private TimeSlotService timeSlotService;

    @GetMapping("/available")
    public List<AvailableTimeSlotData> getAvailableTimeSlots() {
        System.out.println("GET /api/timeslots/available called");
        List<AvailableTimeSlotData> slots = timeSlotService.getAvailableTimeSlots();
        System.out.println("Returning " + slots.size() + " available time slots");
        return slots;
    }
}
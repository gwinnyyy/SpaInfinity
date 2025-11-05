package com.nicco.controller;

import com.nicco.entity.WorkSchedule;
import com.nicco.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/schedule")
@CrossOrigin(origins = "${cors.allowed-origins}")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public List<WorkSchedule> getSchedule() {
        return scheduleService.getFullWeeklySchedule();
    }

    @PutMapping
    public ResponseEntity<WorkSchedule> updateSchedule(@RequestBody WorkSchedule schedule) {
        WorkSchedule updated = scheduleService.updateSchedule(schedule);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/generate")
    public ResponseEntity<Void> generateTimeSlots(@RequestBody Map<String, Integer> payload) {
        Integer days = payload.getOrDefault("days", 30);
        scheduleService.generateTimeSlots(days);
        return ResponseEntity.ok().build();
    }
}
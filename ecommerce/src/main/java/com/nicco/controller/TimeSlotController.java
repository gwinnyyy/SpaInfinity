package com.nicco.controller;

import com.nicco.model.TimeSlot;
import com.nicco.service.TimeSlotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/timeslots")
@Slf4j
public class TimeSlotController {

    @Autowired
    private TimeSlotService timeSlotService;

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableSlots(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<TimeSlot> slots = timeSlotService.getAvailableSlots(date);
            return ResponseEntity.ok(slots);
        } catch (Exception ex) {
            log.error("Error getting available slots: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
        }
    }

    @GetMapping("/therapist/{therapistId}")
    public ResponseEntity<?> getSlotsByTherapist(
            @PathVariable Integer therapistId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<TimeSlot> slots = timeSlotService.getSlotsByTherapist(therapistId, date);
            return ResponseEntity.ok(slots);
        } catch (Exception ex) {
            log.error("Error getting therapist slots: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
        }
    }

    @PostMapping("/generate")
    public ResponseEntity<?> generateSlots(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            List<TimeSlot> slots = timeSlotService.generateSlotsForDate(date);
            return ResponseEntity.ok(slots);
        } catch (Exception ex) {
            log.error("Error generating slots: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
        }
    }

    @PostMapping("/{id}/book")
    public ResponseEntity<?> bookSlot(@PathVariable Integer id) {
        try {
            TimeSlot slot = timeSlotService.bookSlot(id);
            if (slot != null) {
                return ResponseEntity.ok(slot);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Error booking slot: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
        }
    }

    @PostMapping("/{id}/release")
    public ResponseEntity<?> releaseSlot(@PathVariable Integer id) {
        try {
            TimeSlot slot = timeSlotService.releaseSlot(id);
            if (slot != null) {
                return ResponseEntity.ok(slot);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Error releasing slot: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
        }
    }
}
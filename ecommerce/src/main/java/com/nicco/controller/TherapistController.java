package com.nicco.controller;

import com.nicco.model.Therapist;
import com.nicco.service.TherapistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/therapists")
@Slf4j
public class TherapistController {

    @Autowired
    private TherapistService therapistService;

    @GetMapping
    public ResponseEntity<?> getAllTherapists() {
        try {
            List<Therapist> therapists = therapistService.getAllTherapists();
            return ResponseEntity.ok(therapists);
        } catch (Exception ex) {
            log.error("Error getting therapists: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
        }
    }

    @GetMapping("/available")
    public ResponseEntity<?> getAvailableTherapists() {
        try {
            List<Therapist> therapists = therapistService.getAvailableTherapists();
            return ResponseEntity.ok(therapists);
        } catch (Exception ex) {
            log.error("Error getting available therapists: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTherapist(@PathVariable Integer id) {
        try {
            Therapist therapist = therapistService.getById(id);
            if (therapist != null) {
                return ResponseEntity.ok(therapist);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Error getting therapist: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> createTherapist(@RequestBody Therapist therapist) {
        try {
            Therapist created = therapistService.create(therapist);
            return ResponseEntity.ok(created);
        } catch (Exception ex) {
            log.error("Error creating therapist: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> updateTherapist(@RequestBody Therapist therapist) {
        try {
            Therapist updated = therapistService.update(therapist);
            if (updated != null) {
                return ResponseEntity.ok(updated);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Error updating therapist: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTherapist(@PathVariable Integer id) {
        try {
            therapistService.delete(id);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            log.error("Error deleting therapist: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
        }
    }
}
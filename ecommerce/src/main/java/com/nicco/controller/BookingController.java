package com.nicco.controller;

import com.nicco.model.Booking;
import com.nicco.service.BookingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/bookings")
@Slf4j
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/lookup")
    public ResponseEntity<?> lookupBookings(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone) {
        try {
            if (email != null && !email.isEmpty()) {
                List<Booking> bookings = bookingService.getByEmail(email);
                return ResponseEntity.ok(bookings);
            } else if (phone != null && !phone.isEmpty()) {
                List<Booking> bookings = bookingService.getByPhone(phone);
                return ResponseEntity.ok(bookings);
            } else {
                return ResponseEntity.badRequest()
                    .body("Please provide either email or phone");
            }
        } catch (Exception ex) {
            log.error("Error looking up bookings: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving bookings: " + ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBooking(@PathVariable Integer id) {
        try {
            Booking booking = bookingService.getById(id);
            if (booking != null) {
                return ResponseEntity.ok(booking);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Error getting booking: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving booking: " + ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            log.info("Creating booking for: {}", booking.getCustomerName());
            Booking created = bookingService.createBooking(booking);
            log.info("Booking created with ID: {}", created.getId());
            return ResponseEntity.ok(created);
        } catch (Exception ex) {
            log.error("Error creating booking: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error creating booking: " + ex.getMessage());
        }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancelBooking(@PathVariable Integer id) {
        try {
            Booking cancelled = bookingService.cancelBooking(id);
            if (cancelled != null) {
                return ResponseEntity.ok(cancelled);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Error cancelling booking: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error cancelling booking: " + ex.getMessage());
        }
    }

    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllBookings() {
        try {
            List<Booking> bookings = bookingService.getAllBookings();
            return ResponseEntity.ok(bookings);
        } catch (Exception ex) {
            log.error("Error getting all bookings: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error retrieving bookings: " + ex.getMessage());
        }
    }

    @PostMapping("/{id}/confirm")
    public ResponseEntity<?> confirmBooking(@PathVariable Integer id) {
        try {
            Booking confirmed = bookingService.confirmBooking(id);
            if (confirmed != null) {
                return ResponseEntity.ok(confirmed);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Error confirming booking: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error confirming booking: " + ex.getMessage());
        }
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<?> completeBooking(@PathVariable Integer id) {
        try {
            Booking completed = bookingService.completeBooking(id);
            if (completed != null) {
                return ResponseEntity.ok(completed);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Error completing booking: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error completing booking: " + ex.getMessage());
        }
    }
}
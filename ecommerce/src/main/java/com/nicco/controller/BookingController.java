package com.nicco.controller;

import com.nicco.enums.BookingStatus;
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

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getCustomerBookings(@PathVariable Integer customerId) {
        try {
            List<Booking> bookings = bookingService.getByCustomerId(customerId);
            return ResponseEntity.ok(bookings);
        } catch (Exception ex) {
            log.error("Error getting bookings: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
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
                .body(ex.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        try {
            Booking created = bookingService.createBooking(booking);
            return ResponseEntity.ok(created);
        } catch (Exception ex) {
            log.error("Error creating booking: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> updateBooking(@RequestBody Booking booking) {
        try {
            Booking updated = bookingService.updateBooking(booking);
            if (updated != null) {
                return ResponseEntity.ok(updated);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            log.error("Error updating booking: {}", ex.getMessage(), ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.getMessage());
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
                .body(ex.getMessage());
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
                .body(ex.getMessage());
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
                .body(ex.getMessage());
        }
    }
}
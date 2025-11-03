
package com.nicco.serviceimpl;

import com.nicco.entity.BookingData;
import com.nicco.entity.BookingItemData;
import com.nicco.enums.BookingStatus;
import com.nicco.model.Booking;
import com.nicco.repository.BookingDataRepository;
import com.nicco.repository.BookingItemDataRepository;
import com.nicco.service.BookingService;
import com.nicco.service.TimeSlotService;
import com.nicco.util.Transform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDataRepository bookingRepository;

    @Autowired
    private BookingItemDataRepository bookingItemRepository;

    @Autowired
    private TimeSlotService timeSlotService;

    private final Transform<BookingData, Booking> toModel = new Transform<>(Booking.class);
    private final Transform<Booking, BookingData> toEntity = new Transform<>(BookingData.class);

    @Override
    @Transactional
    public Booking createBooking(Booking booking) {
        booking.setStatus(BookingStatus.PENDING);
        BookingData data = toEntity.transform(booking);
        BookingData saved = bookingRepository.save(data);
        
        // Book the time slot
        if (booking.getTimeSlotId() != null) {
            timeSlotService.bookSlot(booking.getTimeSlotId());
        }
        
        return toModel.transform(saved);
    }

    @Override
    public Booking updateBooking(Booking booking) {
        Optional<BookingData> optional = bookingRepository.findById(booking.getId());
        if (optional.isPresent()) {
            BookingData data = toEntity.transform(booking);
            BookingData updated = bookingRepository.save(data);
            return toModel.transform(updated);
        }
        return null;
    }

    @Override
    public Booking getById(Integer id) {
        Optional<BookingData> optional = bookingRepository.findById(id);
        return optional.map(data -> toModel.transform(data)).orElse(null);
    }

    @Override
    public List<Booking> getByCustomerId(Integer customerId) {
        List<Booking> bookings = new ArrayList<>();
        bookingRepository.findByCustomerId(customerId).forEach(data -> 
            bookings.add(toModel.transform(data))
        );
        return bookings;
    }

    @Override
    public List<Booking> getByStatus(BookingStatus status) {
        List<Booking> bookings = new ArrayList<>();
        bookingRepository.findByStatus(status).forEach(data -> 
            bookings.add(toModel.transform(data))
        );
        return bookings;
    }

    @Override
    @Transactional
    public Booking confirmBooking(Integer id) {
        Optional<BookingData> optional = bookingRepository.findById(id);
        if (optional.isPresent()) {
            BookingData data = optional.get();
            data.setStatus(BookingStatus.CONFIRMED);
            BookingData updated = bookingRepository.save(data);
            return toModel.transform(updated);
        }
        return null;
    }

    @Override
    @Transactional
    public Booking cancelBooking(Integer id) {
        Optional<BookingData> optional = bookingRepository.findById(id);
        if (optional.isPresent()) {
            BookingData data = optional.get();
            data.setStatus(BookingStatus.CANCELLED);
            
            // Release the time slot
            if (data.getTimeSlotId() != null) {
                timeSlotService.releaseSlot(data.getTimeSlotId());
            }
            
            BookingData updated = bookingRepository.save(data);
            return toModel.transform(updated);
        }
        return null;
    }

    @Override
    public Booking completeBooking(Integer id) {
        Optional<BookingData> optional = bookingRepository.findById(id);
        if (optional.isPresent()) {
            BookingData data = optional.get();
            data.setStatus(BookingStatus.COMPLETED);
            BookingData updated = bookingRepository.save(data);
            return toModel.transform(updated);
        }
        return null;
    }
}
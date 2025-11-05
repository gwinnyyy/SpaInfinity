package com.nicco.serviceimpl;

import com.nicco.entity.AvailableTimeSlotData;
import com.nicco.entity.BookingData;
import com.nicco.entity.SpaServiceData;
import com.nicco.enums.BookingStatus;
import com.nicco.model.BookingRequest;
import com.nicco.model.BookingResponse;
import com.nicco.repository.AvailableTimeSlotDataRepository;
import com.nicco.repository.BookingDataRepository;
import com.nicco.repository.SpaServiceDataRepository;
import com.nicco.service.BookingService;
import com.nicco.util.ResourceNotFoundException;
import com.nicco.util.SlotAlreadyBookedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingDataRepository bookingRepository;

    @Autowired
    private SpaServiceDataRepository spaServiceRepository;

    @Autowired
    private AvailableTimeSlotDataRepository timeSlotRepository;

    @Override
    @Transactional
    public BookingResponse createBooking(BookingRequest request) {

        SpaServiceData service = spaServiceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + request.getServiceId()));

        AvailableTimeSlotData timeSlot = timeSlotRepository.findById(request.getTimeSlotId())
                .orElseThrow(() -> new ResourceNotFoundException("Time slot not found with id: " + request.getTimeSlotId()));

        if (timeSlot.isBooked()) {
            throw new SlotAlreadyBookedException("This time slot is no longer available.");
        }

        timeSlot.setBooked(true);
        timeSlotRepository.save(timeSlot);

        BookingData booking = new BookingData();
        booking.setCustomerName(request.getCustomerName());
        booking.setCustomerEmail(request.getCustomerEmail());
        booking.setCustomerPhone(request.getCustomerPhone());
        booking.setSpaService(service);
        booking.setTimeSlot(timeSlot);
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setConfirmationCode(UUID.randomUUID().toString().substring(0, 8).toUpperCase());

        BookingData savedBooking = bookingRepository.save(booking);

        return convertToResponse(savedBooking);
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        return bookingRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookingResponse approveBooking(Long bookingId) {
        BookingData booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        booking.setBookingStatus(BookingStatus.APPROVED);
        BookingData savedBooking = bookingRepository.save(booking);
        
        return convertToResponse(savedBooking);
    }

    @Override
    @Transactional
    public BookingResponse cancelBooking(Long bookingId) {
        BookingData booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));
        AvailableTimeSlotData timeSlot = booking.getTimeSlot();
        if (timeSlot != null) {
            timeSlot.setBooked(false);
            timeSlotRepository.save(timeSlot);
        }

        booking.setBookingStatus(BookingStatus.CANCELLED);
        BookingData savedBooking = bookingRepository.save(booking);

        return convertToResponse(savedBooking);
    }

    private BookingResponse convertToResponse(BookingData booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getConfirmationCode(),
                booking.getBookingStatus().toString(),
                booking.getCustomerName(),
                booking.getSpaService().getName(),
                booking.getSpaService().getPrice(),
                booking.getTimeSlot().getSlotDate(),
                booking.getTimeSlot().getStartTime()
        );
    }
}
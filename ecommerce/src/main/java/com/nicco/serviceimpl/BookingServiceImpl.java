package com.nicco.serviceimpl;

import com.nicco.entity.BookingData;
import com.nicco.entity.BookingItemData;
import com.nicco.model.Booking;
import com.nicco.model.BookingItem;
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
    private final Transform<BookingItemData, BookingItem> itemToModel = new Transform<>(BookingItem.class);
    private final Transform<BookingItem, BookingItemData> itemToEntity = new Transform<>(BookingItemData.class);

    @Override
    @Transactional
    public Booking createBooking(Booking booking) {
        log.info("Creating booking for customer: {}", booking.getCustomerName());
        
        booking.setStatus("PENDING");
        
        BookingData data = toEntity.transform(booking);
        BookingData savedBooking = bookingRepository.save(data);
        log.info("Booking saved with ID: {}", savedBooking.getId());
        
        if (booking.getItems() != null && !booking.getItems().isEmpty()) {
            BookingItem item = booking.getItems().get(0);
            item.setBookingId(savedBooking.getId());
            BookingItemData itemData = itemToEntity.transform(item);
            bookingItemRepository.save(itemData);
            log.info("Booking item saved for service: {}", item.getServiceName());
        }
        
        if (booking.getTimeSlotId() != null && booking.getTimeSlotId() > 0) {
            timeSlotService.bookSlot(booking.getTimeSlotId());
            log.info("Time slot {} marked as unavailable", booking.getTimeSlotId());
        }
        
        return getById(savedBooking.getId());
    }

    @Override
    public Booking getById(Integer id) {
        Optional<BookingData> optional = bookingRepository.findById(id);
        if (optional.isPresent()) {
            Booking booking = toModel.transform(optional.get());
            
            List<BookingItemData> items = bookingItemRepository.findByBookingId(id);
            List<BookingItem> bookingItems = new ArrayList<>();
            items.forEach(itemData -> bookingItems.add(itemToModel.transform(itemData)));
            booking.setItems(bookingItems);
            
            return booking;
        }
        return null;
    }

    @Override
    public List<Booking> getByEmail(String email) {
        List<Booking> bookings = new ArrayList<>();
        List<BookingData> dataList = bookingRepository.findByCustomerEmail(email);
        
        dataList.forEach(data -> {
            Booking booking = toModel.transform(data);
            
            List<BookingItemData> items = bookingItemRepository.findByBookingId(data.getId());
            List<BookingItem> bookingItems = new ArrayList<>();
            items.forEach(itemData -> bookingItems.add(itemToModel.transform(itemData)));
            booking.setItems(bookingItems);
            
            bookings.add(booking);
        });
        
        return bookings;
    }

    @Override
    public List<Booking> getByPhone(String phone) {
        List<Booking> bookings = new ArrayList<>();
        List<BookingData> dataList = bookingRepository.findByCustomerPhone(phone);
        
        dataList.forEach(data -> {
            Booking booking = toModel.transform(data);
            
            List<BookingItemData> items = bookingItemRepository.findByBookingId(data.getId());
            List<BookingItem> bookingItems = new ArrayList<>();
            items.forEach(itemData -> bookingItems.add(itemToModel.transform(itemData)));
            booking.setItems(bookingItems);
            
            bookings.add(booking);
        });
        
        return bookings;
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        bookingRepository.findAll().forEach(data -> {
            Booking booking = toModel.transform(data);
            
            List<BookingItemData> items = bookingItemRepository.findByBookingId(data.getId());
            List<BookingItem> bookingItems = new ArrayList<>();
            items.forEach(itemData -> bookingItems.add(itemToModel.transform(itemData)));
            booking.setItems(bookingItems);
            
            bookings.add(booking);
        });
        return bookings;
    }

    @Override
    @Transactional
    public Booking confirmBooking(Integer id) {
        Optional<BookingData> optional = bookingRepository.findById(id);
        if (optional.isPresent()) {
            BookingData data = optional.get();
            data.setStatus("CONFIRMED");
            bookingRepository.save(data);
            log.info("Booking {} confirmed", id);
            return getById(id);
        }
        return null;
    }

    @Override
    @Transactional
    public Booking cancelBooking(Integer id) {
        Optional<BookingData> optional = bookingRepository.findById(id);
        if (optional.isPresent()) {
            BookingData data = optional.get();
            data.setStatus("CANCELLED");
            
            if (data.getTimeSlotId() != null) {
                timeSlotService.releaseSlot(data.getTimeSlotId());
                log.info("Time slot {} released", data.getTimeSlotId());
            }
            
            bookingRepository.save(data);
            log.info("Booking {} cancelled", id);
            return getById(id);
        }
        return null;
    }

    @Override
    @Transactional
    public Booking completeBooking(Integer id) {
        Optional<BookingData> optional = bookingRepository.findById(id);
        if (optional.isPresent()) {
            BookingData data = optional.get();
            data.setStatus("COMPLETED");
            bookingRepository.save(data);
            log.info("Booking {} completed", id);
            return getById(id);
        }
        return null;
    }
}
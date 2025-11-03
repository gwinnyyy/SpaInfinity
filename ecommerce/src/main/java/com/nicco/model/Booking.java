// Location: ecommerce/src/main/java/com/nicco/model/Booking.java
package com.nicco.model;

import com.nicco.enums.BookingStatus;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class Booking {
    private int id;
    private Integer customerId;
    private String customerName;
    private Integer therapistId;
    private String therapistName;
    private Integer timeSlotId;
    private LocalDateTime bookingDate;
    private BookingStatus status;
    private BigDecimal totalAmount;
    private String notes;
    private String paymentStatus;
    private List<BookingItem> items;
    private Date created;
    private Date lastUpdated;
}
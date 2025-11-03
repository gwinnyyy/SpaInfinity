package com.nicco.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nicco.enums.BookingStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "booking_data")
public class BookingData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private Integer customerId;
    private Integer therapistId;
    private Integer timeSlotId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bookingDate;
    
    @Enumerated(EnumType.STRING)
    private BookingStatus status;
    
    private BigDecimal totalAmount;
    private String notes;
    private String paymentStatus;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date lastUpdated;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date created;
}
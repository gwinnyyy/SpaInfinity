package com.nicco.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private String confirmationCode;
    private String bookingStatus;
    private String customerName;
    private String serviceName;
    private BigDecimal servicePrice;
    private LocalDate slotDate;
    private LocalTime startTime;
}
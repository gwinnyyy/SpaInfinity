package com.nicco.model;

import lombok.Data;

@Data
public class BookingRequest {
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private Long serviceId;
    private Long timeSlotId;
}
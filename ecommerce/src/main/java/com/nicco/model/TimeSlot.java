
package com.nicco.model;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TimeSlot {
    private int id;
    private LocalDate slotDate;
    private LocalTime slotTime;
    private Integer therapistId;
    private String therapistName;
    private boolean isAvailable;
}
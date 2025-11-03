
package com.nicco.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Data
@Entity
@Table(name = "time_slot_data")
public class TimeSlotData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate slotDate;
    
    @JsonFormat(pattern = "HH:mm")
    private LocalTime slotTime;
    
    private Integer therapistId;
    private boolean isAvailable;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date created;
}
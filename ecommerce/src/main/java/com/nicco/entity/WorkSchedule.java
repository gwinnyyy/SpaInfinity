package com.nicco.entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "work_schedule")
@Data
public class WorkSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "day_of_week", nullable = false, unique = true)
    private int dayOfWeek;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;
}
package com.nicco.service;

import com.nicco.entity.WorkSchedule;
import java.util.List;

public interface ScheduleService {
    List<WorkSchedule> getFullWeeklySchedule();
    WorkSchedule updateSchedule(WorkSchedule schedule);
    void generateTimeSlots(int daysInAdvance);
}
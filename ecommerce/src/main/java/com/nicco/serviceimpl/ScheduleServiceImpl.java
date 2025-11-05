package com.nicco.serviceimpl;

import com.nicco.entity.AvailableTimeSlotData;
import com.nicco.entity.WorkSchedule;
import com.nicco.repository.AvailableTimeSlotDataRepository;
import com.nicco.repository.WorkScheduleRepository;
import com.nicco.service.ScheduleService;
import com.nicco.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private WorkScheduleRepository scheduleRepository;

    @Autowired
    private AvailableTimeSlotDataRepository timeSlotRepository;

    @Override
    public List<WorkSchedule> getFullWeeklySchedule() {
        return scheduleRepository.findAllByOrderByDayOfWeekAsc();
    }

    @Override
    public WorkSchedule updateSchedule(WorkSchedule schedule) {
        WorkSchedule existing = scheduleRepository.findByDayOfWeek(schedule.getDayOfWeek());
        if (existing == null) {
            throw new ResourceNotFoundException("Schedule not found for day: " + schedule.getDayOfWeek());
        }
        existing.setActive(schedule.isActive());
        existing.setStartTime(schedule.getStartTime());
        existing.setEndTime(schedule.getEndTime());
        return scheduleRepository.save(existing);
    }

    @Override
    public void generateTimeSlots(int daysInAdvance) {
        List<WorkSchedule> activeSchedules = scheduleRepository.findAllByOrderByDayOfWeekAsc()
                .stream().filter(WorkSchedule::isActive).toList();

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(daysInAdvance);

        List<AvailableTimeSlotData> slotsToCreate = new ArrayList<>();

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            int dayOfWeekValue = date.getDayOfWeek().getValue() % 7; 

            for (WorkSchedule schedule : activeSchedules) {
                if (schedule.getDayOfWeek() == dayOfWeekValue) {
                    
                    LocalTime slotTime = schedule.getStartTime();
                    while (slotTime.isBefore(schedule.getEndTime())) {
                        LocalTime endTime = slotTime.plusHours(1);
                        
                        boolean exists = timeSlotRepository.existsBySlotDateAndStartTime(date, slotTime);
                        if (!exists) {
                            AvailableTimeSlotData newSlot = new AvailableTimeSlotData();
                            newSlot.setSlotDate(date);
                            newSlot.setStartTime(slotTime);
                            newSlot.setEndTime(endTime);
                            newSlot.setBooked(false);
                            slotsToCreate.add(newSlot);
                        }
                        slotTime = endTime;
                    }
                    break;
                }
            }
        }

        if (!slotsToCreate.isEmpty()) {
            timeSlotRepository.saveAll(slotsToCreate);
        }
    }
}
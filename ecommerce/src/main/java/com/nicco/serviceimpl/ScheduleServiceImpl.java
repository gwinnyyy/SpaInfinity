package com.nicco.serviceimpl;

import com.nicco.entity.AvailableTimeSlotData;
import com.nicco.entity.WorkSchedule;
import com.nicco.repository.AvailableTimeSlotDataRepository;
import com.nicco.repository.WorkScheduleRepository;
import com.nicco.service.ScheduleService;
import com.nicco.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Transactional
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
    @Transactional
    public void generateTimeSlots(int daysInAdvance) {
        System.out.println("Starting to generate time slots for " + daysInAdvance + " days");
        
        List<WorkSchedule> allSchedules = scheduleRepository.findAllByOrderByDayOfWeekAsc();
        List<WorkSchedule> activeSchedules = allSchedules.stream()
                .filter(WorkSchedule::isActive)
                .collect(Collectors.toList());

        System.out.println("Found " + activeSchedules.size() + " active schedules");
        
        if (activeSchedules.isEmpty()) {
            System.out.println("No active schedules found. Please activate at least one day.");
            return;
        }

        Map<Integer, WorkSchedule> scheduleMap = activeSchedules.stream()
                .collect(Collectors.toMap(WorkSchedule::getDayOfWeek, s -> s));

        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(daysInAdvance);

        List<AvailableTimeSlotData> slotsToCreate = new ArrayList<>();
        int totalDaysProcessed = 0;
        int slotsGenerated = 0;

        // Iterate through each date
        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            totalDaysProcessed++;
            
            // Convert Java DayOfWeek to your database format
            // Java: MONDAY=1, TUESDAY=2, ..., SUNDAY=7
            // Your DB: Sunday=0, Monday=1, Tuesday=2, ..., Saturday=6
            int javaDayValue = date.getDayOfWeek().getValue(); // 1-7
            int dbDayValue = javaDayValue % 7; // Convert to 0-6 (Sunday=0)
            
            System.out.println("Processing date: " + date + " (Java day: " + javaDayValue + ", DB day: " + dbDayValue + ")");

            // Check if this day has an active schedule
            WorkSchedule schedule = scheduleMap.get(dbDayValue);
            
            if (schedule != null) {
                System.out.println("Found schedule for " + date + ": " + schedule.getStartTime() + " - " + schedule.getEndTime());
                
                LocalTime slotTime = schedule.getStartTime();
                int dailySlots = 0;
                
                // Generate 1-hour slots
                while (slotTime.plusHours(1).isBefore(schedule.getEndTime()) || 
                       slotTime.plusHours(1).equals(schedule.getEndTime())) {
                    
                    LocalTime endTime = slotTime.plusHours(1);
                    
                    // Check if slot already exists
                    boolean exists = timeSlotRepository.existsBySlotDateAndStartTime(date, slotTime);
                    
                    if (!exists) {
                        AvailableTimeSlotData newSlot = new AvailableTimeSlotData();
                        newSlot.setSlotDate(date);
                        newSlot.setStartTime(slotTime);
                        newSlot.setEndTime(endTime);
                        newSlot.setBooked(false);
                        slotsToCreate.add(newSlot);
                        dailySlots++;
                        slotsGenerated++;
                    }
                    
                    slotTime = endTime;
                }
                
                System.out.println("Generated " + dailySlots + " slots for " + date);
            } else {
                System.out.println("No active schedule found for " + date + " (day " + dbDayValue + ")");
            }
        }

        System.out.println("Total days processed: " + totalDaysProcessed);
        System.out.println("Total slots to create: " + slotsGenerated);

        if (!slotsToCreate.isEmpty()) {
            timeSlotRepository.saveAll(slotsToCreate);
            System.out.println("Successfully saved " + slotsToCreate.size() + " time slots");
        } else {
            System.out.println("No new slots to create. All slots may already exist.");
        }
    }
}
package com.nicco.repository;

import com.nicco.entity.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Integer> {
    List<WorkSchedule> findAllByOrderByDayOfWeekAsc();
    WorkSchedule findByDayOfWeek(int dayOfWeek);
}
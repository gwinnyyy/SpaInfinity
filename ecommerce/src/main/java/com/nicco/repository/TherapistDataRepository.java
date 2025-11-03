package com.nicco.repository;

import com.nicco.entity.TherapistData;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface TherapistDataRepository extends CrudRepository<TherapistData, Integer> {
    List<TherapistData> findByAvailable(boolean available);
    List<TherapistData> findBySpecializationContaining(String specialization);
}
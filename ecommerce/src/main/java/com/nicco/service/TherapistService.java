package com.nicco.service;

import com.nicco.model.Therapist;
import java.util.List;

public interface TherapistService {
    List<Therapist> getAllTherapists();
    List<Therapist> getAvailableTherapists();
    Therapist getById(Integer id);
    Therapist create(Therapist therapist);
    Therapist update(Therapist therapist);
    void delete(Integer id);
}
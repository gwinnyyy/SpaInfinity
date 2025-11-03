package com.nicco.serviceimpl;

import com.nicco.entity.TherapistData;
import com.nicco.model.Therapist;
import com.nicco.repository.TherapistDataRepository;
import com.nicco.service.TherapistService;
import com.nicco.util.Transform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TherapistServiceImpl implements TherapistService {

    @Autowired
    private TherapistDataRepository therapistRepository;

    private final Transform<TherapistData, Therapist> toModel = new Transform<>(Therapist.class);
    private final Transform<Therapist, TherapistData> toEntity = new Transform<>(TherapistData.class);

    @Override
    public List<Therapist> getAllTherapists() {
        List<Therapist> therapists = new ArrayList<>();
        therapistRepository.findAll().forEach(data -> 
            therapists.add(toModel.transform(data))
        );
        return therapists;
    }

    @Override
    public List<Therapist> getAvailableTherapists() {
        List<Therapist> therapists = new ArrayList<>();
        therapistRepository.findByAvailable(true).forEach(data -> 
            therapists.add(toModel.transform(data))
        );
        return therapists;
    }

    @Override
    public Therapist getById(Integer id) {
        Optional<TherapistData> optional = therapistRepository.findById(id);
        return optional.map(data -> toModel.transform(data)).orElse(null);
    }

    @Override
    public Therapist create(Therapist therapist) {
        TherapistData data = toEntity.transform(therapist);
        TherapistData saved = therapistRepository.save(data);
        return toModel.transform(saved);
    }

    @Override
    public Therapist update(Therapist therapist) {
        Optional<TherapistData> optional = therapistRepository.findById(therapist.getId());
        if (optional.isPresent()) {
            TherapistData data = toEntity.transform(therapist);
            TherapistData updated = therapistRepository.save(data);
            return toModel.transform(updated);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        therapistRepository.deleteById(id);
    }
}
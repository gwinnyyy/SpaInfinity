package com.nicco.serviceimpl;

import com.nicco.entity.SpaServiceData;
import com.nicco.repository.SpaServiceDataRepository;
import com.nicco.service.SpaServiceService;
import com.nicco.util.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpaServiceServiceImpl implements SpaServiceService {

    @Autowired
    private SpaServiceDataRepository spaServiceRepository;

    @Override
    public List<SpaServiceData> getAllServices() {
        return spaServiceRepository.findAll();
    }

    @Override
    public SpaServiceData createService(SpaServiceData serviceData) {
        return spaServiceRepository.save(serviceData);
    }

    @Override
    public SpaServiceData updateService(Long serviceId, SpaServiceData serviceDetails) {
        SpaServiceData service = spaServiceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id: " + serviceId));

        service.setName(serviceDetails.getName());
        service.setDescription(serviceDetails.getDescription());
        service.setPrice(serviceDetails.getPrice());
        service.setDurationMinutes(serviceDetails.getDurationMinutes());
        
        return spaServiceRepository.save(service);
    }

    @Override
    public void deleteService(Long serviceId) {
        SpaServiceData service = spaServiceRepository.findById(serviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Service not found with id:" + serviceId));
        
        spaServiceRepository.delete(service);
    }
}
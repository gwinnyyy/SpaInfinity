package com.nicco.service;

import com.nicco.entity.SpaServiceData;
import java.util.List;

public interface SpaServiceService {
    List<SpaServiceData> getAllServices();
    SpaServiceData createService(SpaServiceData serviceData);
    SpaServiceData updateService(Long serviceId, SpaServiceData serviceDetails);
    void deleteService(Long serviceId);
}
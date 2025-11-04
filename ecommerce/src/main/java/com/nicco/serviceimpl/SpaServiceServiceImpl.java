package com.nicco.serviceimpl;

import com.nicco.entity.SpaServiceData;
import com.nicco.repository.SpaServiceDataRepository;
import com.nicco.service.SpaServiceService;
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
}
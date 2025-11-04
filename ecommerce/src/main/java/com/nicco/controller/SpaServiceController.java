package com.nicco.controller;

import com.nicco.entity.SpaServiceData;
import com.nicco.service.SpaServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "${cors.allowed-origins}") 
public class SpaServiceController {

    @Autowired
    private SpaServiceService spaServiceService;

    @GetMapping
    public List<SpaServiceData> getAllServices() {
        return spaServiceService.getAllServices();
    }
}
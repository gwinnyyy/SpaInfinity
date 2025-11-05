package com.nicco.controller;

import com.nicco.entity.SpaServiceData;
import com.nicco.service.SpaServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public SpaServiceData createService(@RequestBody SpaServiceData service) {
        return spaServiceService.createService(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpaServiceData> updateService(@PathVariable Long id, @RequestBody SpaServiceData serviceDetails) {
        SpaServiceData updatedService = spaServiceService.updateService(id, serviceDetails);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        spaServiceService.deleteService(id);
        return ResponseEntity.noContent().build();
    }
}
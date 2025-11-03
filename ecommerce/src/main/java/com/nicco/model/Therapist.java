package com.nicco.model;

import lombok.Data;
import java.util.Date;

@Data
public class Therapist {
    private int id;
    private String firstName;
    private String lastName;
    private String specialization;
    private String phone;
    private String email;
    private boolean available;
    private Date created;
    private Date lastUpdated;
    
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
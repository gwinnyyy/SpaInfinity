package com.nicco.model;

import lombok.Data;

@Data
public class SpaService {
    int id;
    String name;
    String description;
    String categoryName;
    String imageFile;
    long durationInMinutes;
    String price;
}

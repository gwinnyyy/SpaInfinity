package com.gabriel.model;

import lombok.Data;

@Data
@Deprecated
class Product {
    int id;
    String name;
    String description;
    String categoryName;
    String imageFile;
    String unitOfMeasure;
    String price;
}

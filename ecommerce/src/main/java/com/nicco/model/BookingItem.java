package com.nicco.model;
import com.nicco.enums.OrderItemStatus;
import lombok.Data;

import java.util.*;

@Data
public class BookingItem {
    int id;
    int bookingId;
    int customerId;
    String customerName;
    int productId;
    String productName;
    String productDescription;
    String productCategoryName;
    String productImageFile;
    String productUnitOfMeasure;
    double quantity;
    double price;
    OrderItemStatus status;
    Date created;
    Date lastUpdated;
}

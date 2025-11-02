package com.nicco.repository;

import com.nicco.entity.OrderItemData;
import com.nicco.enums.OrderItemStatus;
import com.nicco.model.OrderItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderItemDataRepository extends CrudRepository<OrderItemData, Integer> {
    List<OrderItemData> findAllByCustomerId(Integer customerId);
//    List<OrderItemData> saveAll(List<OrderItemData> orderItemData);
}

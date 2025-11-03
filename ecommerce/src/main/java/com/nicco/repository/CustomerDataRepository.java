package com.nicco.repository;

import com.nicco.entity.CustomerData;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface CustomerDataRepository extends CrudRepository<CustomerData, Integer> {
    Optional<CustomerData> findByEmail(String email);
    Optional<CustomerData> findByPhone(String phone);
}
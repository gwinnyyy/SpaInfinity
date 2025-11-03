package com.nicco.service;

import com.nicco.model.Customer;
import java.util.List;

public interface CustomerService {
    Customer create(Customer customer);
    Customer update(Customer customer);
    Customer getById(Integer id);
    Customer getByEmail(String email);
    List<Customer> getAll();
    void delete(Integer id);
}
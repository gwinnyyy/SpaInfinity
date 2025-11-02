package com.nicco.repository;

import com.nicco.entity.SpaServiceData;
import org.springframework.data.repository.CrudRepository;

public interface ProductDataRepository extends CrudRepository<SpaServiceData, Integer> {
}

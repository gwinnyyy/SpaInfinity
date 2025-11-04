package com.nicco.repository;

import com.nicco.entity.SpaServiceData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaServiceDataRepository extends JpaRepository<SpaServiceData, Long> {
}
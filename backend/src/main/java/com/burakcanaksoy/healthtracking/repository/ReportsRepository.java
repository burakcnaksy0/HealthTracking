package com.burakcanaksoy.healthtracking.repository;

import com.burakcanaksoy.healthtracking.model.Reports;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReportsRepository extends CrudRepository<Reports, Long> {
    Optional<Reports> findTopByUserIdOrderByGeneratedAtDesc(Long userId);
}

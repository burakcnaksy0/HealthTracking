package com.burakcanaksoy.healthtracking.repository;

import com.burakcanaksoy.healthtracking.model.Recommendations;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecommendationsRepository extends CrudRepository<Recommendations, Long> {
    Optional<Recommendations> findTopByUserIdOrderByGeneratedAtDesc(Long userId);
}

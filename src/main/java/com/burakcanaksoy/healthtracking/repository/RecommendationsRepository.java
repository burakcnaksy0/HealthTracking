package com.burakcanaksoy.healthtracking.repository;

import com.burakcanaksoy.healthtracking.model.Recommendations;
import org.springframework.data.repository.CrudRepository;

public interface RecommendationsRepository extends CrudRepository<Recommendations,Long> {
}

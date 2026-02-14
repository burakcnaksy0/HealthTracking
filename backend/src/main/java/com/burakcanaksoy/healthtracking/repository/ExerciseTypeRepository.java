package com.burakcanaksoy.healthtracking.repository;

import com.burakcanaksoy.healthtracking.model.ExerciseType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ExerciseTypeRepository extends CrudRepository<ExerciseType, Long> {
    Optional<ExerciseType> findByName(String name);
}

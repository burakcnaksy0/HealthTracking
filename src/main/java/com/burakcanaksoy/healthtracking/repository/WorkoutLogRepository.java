package com.burakcanaksoy.healthtracking.repository;

import com.burakcanaksoy.healthtracking.model.WorkoutLog;
import com.burakcanaksoy.healthtracking.repository.custom.CustomWorkoutRepository;
import org.springframework.data.repository.CrudRepository;

public interface WorkoutLogRepository extends CrudRepository<WorkoutLog,Long>, CustomWorkoutRepository {
}

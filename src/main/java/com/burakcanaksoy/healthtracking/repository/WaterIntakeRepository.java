package com.burakcanaksoy.healthtracking.repository;

import com.burakcanaksoy.healthtracking.model.WaterIntake;
import com.burakcanaksoy.healthtracking.repository.custom.CustomWaterIntakeRepository;
import org.springframework.data.repository.CrudRepository;

public interface WaterIntakeRepository extends CrudRepository<WaterIntake,Long>, CustomWaterIntakeRepository {
}

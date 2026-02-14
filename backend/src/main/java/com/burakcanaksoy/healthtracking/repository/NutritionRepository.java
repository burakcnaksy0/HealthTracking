package com.burakcanaksoy.healthtracking.repository;

import com.burakcanaksoy.healthtracking.model.MealLog;
import com.burakcanaksoy.healthtracking.repository.custom.CustomNutritionRepository;
import org.springframework.data.repository.CrudRepository;

public interface NutritionRepository extends CrudRepository<MealLog, Long>, CustomNutritionRepository {
}

package com.burakcanaksoy.healthtracking.repository;

import com.burakcanaksoy.healthtracking.model.Reminder;
import org.springframework.data.repository.CrudRepository;

public interface ReminderRepository extends CrudRepository<Reminder, Long> {
}

package com.burakcanaksoy.healthtracking.service;

import com.burakcanaksoy.healthtracking.mapper.WorkoutLogMapper;
import com.burakcanaksoy.healthtracking.model.ExerciseType;
import com.burakcanaksoy.healthtracking.model.User;
import com.burakcanaksoy.healthtracking.model.WorkoutLog;
import com.burakcanaksoy.healthtracking.model.enums.Gender;
import com.burakcanaksoy.healthtracking.repository.ExerciseTypeRepository;
import com.burakcanaksoy.healthtracking.repository.WorkoutLogRepository;
import com.burakcanaksoy.healthtracking.request.WorkoutLogRequest;
import com.burakcanaksoy.healthtracking.response.WorkoutLogResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class WorkoutLogService {
    private final WorkoutLogRepository workoutLogRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;
    private final WorkoutLogMapper workoutLogMapper;

    public WorkoutLogService(WorkoutLogRepository workoutLogRepository,
            ExerciseTypeRepository exerciseTypeRepository,
            WorkoutLogMapper workoutLogMapper) {
        this.workoutLogRepository = workoutLogRepository;
        this.exerciseTypeRepository = exerciseTypeRepository;
        this.workoutLogMapper = workoutLogMapper;
    }

    public WorkoutLogResponse logWorkout(WorkoutLogRequest request) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ExerciseType exerciseType = exerciseTypeRepository.findByName(request.getExerciseName())
                .orElseThrow(() -> new RuntimeException("Exercise type not found: " + request.getExerciseName()));

        if (user.getWeight() == null) {
            throw new RuntimeException("User weight information is missing. Please update your profile.");
        }

        BigDecimal caloriesBurned = calculateCalories(exerciseType.getMet(), user.getWeight(), request.getDuration());

        WorkoutLog workoutLog = workoutLogMapper.toEntity(request);
        workoutLog.setUserId(user.getId());
        workoutLog.setCaloriesBurned(caloriesBurned);
        workoutLog.setCreatedAt(LocalDateTime.now());

        WorkoutLog savedLog = workoutLogRepository.save(workoutLog);

        return workoutLogMapper.toResponse(savedLog);
    }
    public BigDecimal calculateBMH(User user){
        if (user.getHeight() == null || user.getWeight() == null){
            return BigDecimal.ZERO;
        }
        double weight = user.getWeight().doubleValue();
        double height = user.getHeight().doubleValue();
        int age = user.getAge();

        double bmr = 0;

        if (Gender.MALE.equals(user.getGender())){
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else if (Gender.FEMALE.equals(user.getGender())) {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }else{
            return BigDecimal.ZERO;
        }
        log.info("User BMH : {}", bmr);
        return BigDecimal.valueOf(bmr).setScale(2,RoundingMode.HALF_UP);
    }

    private BigDecimal calculateCalories(BigDecimal met, BigDecimal weight, int durationMinutes) {
        return met.multiply(weight)
                .multiply(BigDecimal.valueOf(durationMinutes))
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
    }

    public List<WorkoutLogResponse> historyWorkout() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);

        List<WorkoutLog> workoutLogList = workoutLogRepository.findByUserIdAndCreatedAtBetween(
                user.getId(),
                startOfDay,
                endOfDay
        );
        return workoutLogMapper.toResponseList(workoutLogList);

    }
}

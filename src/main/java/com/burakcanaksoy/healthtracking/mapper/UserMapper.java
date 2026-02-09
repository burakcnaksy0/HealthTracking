package com.burakcanaksoy.healthtracking.mapper;

import com.burakcanaksoy.healthtracking.model.User;
import com.burakcanaksoy.healthtracking.model.enums.ActivityLevel;
import com.burakcanaksoy.healthtracking.model.enums.Goal;
import com.burakcanaksoy.healthtracking.model.enums.Role;
import com.burakcanaksoy.healthtracking.request.RegisterRequest;
import com.burakcanaksoy.healthtracking.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User mapToUser(RegisterRequest request) {
        Goal goalEnum;
        ActivityLevel activityLevelEnum;
        try {
            goalEnum = Goal.valueOf(request.getGoal());
            activityLevelEnum = ActivityLevel.valueOf(request.getActivityLevel());
        } catch (IllegalArgumentException e) {
            log.error("Invalid enum value provided for goal or activity level");
            throw new RuntimeException("Invalid Goal or Activity Level");
        }

        return User.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .weight(request.getWeight())
                .height(request.getHeight())
                .goal(goalEnum)
                .activityLevel(activityLevelEnum)
                .age(request.getAge())
                .role(Role.ROLE_USER)
                .build();
    }

    public UserResponse toResponse(User user){
        UserResponse response = new UserResponse();
        response.setFullName(user.getFullName());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setAge(user.getAge());
        response.setGoal(user.getGoal().toString());
        response.setActivityLevel(user.getActivityLevel().toString());
        response.setHeight(user.getHeight());
        response.setWeight(user.getWeight());

        return response;
    }

}

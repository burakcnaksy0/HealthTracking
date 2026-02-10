package com.burakcanaksoy.healthtracking.service;

import com.burakcanaksoy.healthtracking.mapper.UserMapper;
import com.burakcanaksoy.healthtracking.model.User;
import com.burakcanaksoy.healthtracking.repository.UserRepository;
import com.burakcanaksoy.healthtracking.response.UserResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserResponse getUserProfile() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null ){
            throw new NullPointerException("Unauthorized access");
        }
        return userMapper.toResponse(user);
    }
}

package com.burakcanaksoy.healthtracking.service;

import com.burakcanaksoy.healthtracking.mapper.UserMapper;
import com.burakcanaksoy.healthtracking.model.User;
import com.burakcanaksoy.healthtracking.repository.UserRepository;
import com.burakcanaksoy.healthtracking.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository,UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserResponse> getUsersDetails() {
        List<User> userList = (List<User>) userRepository.findAll();
        if (userList.isEmpty()){
            return List.of();
        }
        return userMapper.toResponseList(userList);
    }
}

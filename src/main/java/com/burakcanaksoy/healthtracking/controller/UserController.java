package com.burakcanaksoy.healthtracking.controller;

import com.burakcanaksoy.healthtracking.response.ApiResponse;
import com.burakcanaksoy.healthtracking.response.UserResponse;
import com.burakcanaksoy.healthtracking.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getUsersDetails(){
        List<UserResponse> userList = userService.getUsersDetails();
        return ResponseEntity.ok(ApiResponse.success("User list",userList));
    }
}

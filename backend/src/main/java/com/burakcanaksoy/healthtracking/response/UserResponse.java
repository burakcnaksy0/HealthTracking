package com.burakcanaksoy.healthtracking.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String fullName;
    private String username;
    private String email;
    private int age;
    private BigDecimal height;
    private BigDecimal weight;
    private String gender;
    private String goal;
    private String activityLevel;
}

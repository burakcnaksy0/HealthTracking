package com.burakcanaksoy.healthtracking.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Full Name cannot be blank")
    private String fullName;

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @Min(value = 18, message = "Age must be at least 18")
    private int age;

    @NotBlank(message = "Gender cannot be blank")
    private String gender;

    @NotNull(message = "Weight is required")
    @DecimalMin(value = "30.0", message = "Weight must be at least 30kg")
    private BigDecimal weight;

    @NotNull(message = "Height is required")
    @DecimalMin(value = "100.0", message = "Height must be at least 100cm")
    private BigDecimal height;

    @NotBlank(message = "Goal cannot be blank")
    private String goal;

    @NotBlank(message = "Activity level cannot be blank")
    private String activityLevel;
}

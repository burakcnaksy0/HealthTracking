package com.burakcanaksoy.healthtracking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("exercise_types")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseType {
    @Id
    private Long id;
    private String name;
    private BigDecimal met;
}

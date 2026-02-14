package com.burakcanaksoy.healthtracking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("reports")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Reports {
    @Id
    private Long id;
    private Long userId;
    private String reportText;
    private BigDecimal totalCaloriesIn;
    private BigDecimal totalCaloriesOut;
    private BigDecimal averageProtein;
    private BigDecimal averageCarb;
    private BigDecimal averageFat;
    private BigDecimal averageWater;
    @CreatedDate
    private LocalDateTime generatedAt;
}

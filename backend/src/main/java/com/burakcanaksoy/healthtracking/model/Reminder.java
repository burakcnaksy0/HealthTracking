package com.burakcanaksoy.healthtracking.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("remainders")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reminder {
    @Id
    private Long id;
    private Long userId;
    private String remainderText;
    @CreatedDate
    private LocalDateTime generatedAt;
}

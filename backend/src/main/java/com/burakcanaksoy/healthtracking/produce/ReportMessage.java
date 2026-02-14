package com.burakcanaksoy.healthtracking.produce;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReportMessage implements Serializable {
    private Long reportId;
    private String email;
    private String reportText;

}

package com.burakcanaksoy.healthtracking.controller;

import com.burakcanaksoy.healthtracking.model.Reminder;
import com.burakcanaksoy.healthtracking.response.ApiResponse;
import com.burakcanaksoy.healthtracking.service.RemainderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reminder")
public class ReminderController {
    private final RemainderService remainderService;

    public ReminderController(RemainderService remainderService){
        this.remainderService = remainderService;
    }

    @GetMapping("/daily")
    public ResponseEntity<ApiResponse<Reminder>> createMidDayReminder(){
        Reminder reminder = remainderService.createMidDayReminder();
        return ResponseEntity.ok(ApiResponse.success("Mid-Day Remainder completed.",reminder));
    }
}

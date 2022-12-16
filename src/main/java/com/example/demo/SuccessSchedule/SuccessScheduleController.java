package com.example.demo.SuccessSchedule;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = "/success_schedule")
@AllArgsConstructor
public class SuccessScheduleController {

    private SuccessScheduleService successScheduleService;

    @GetMapping
    public JSONObject getSuccessSchedules() {
        return successScheduleService.getSuccessSchedules();
    }
}

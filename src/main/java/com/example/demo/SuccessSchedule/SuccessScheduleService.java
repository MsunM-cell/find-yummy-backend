package com.example.demo.SuccessSchedule;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

@Service
@AllArgsConstructor
public class SuccessScheduleService {

    private SuccessScheduleRepository successScheduleRepository;

    public JSONObject getSuccessSchedules() {
        JSONObject response = new JSONObject();

        response.put("code", 200);
        response.put("msg", "success");
        response.put("success_schedules", successScheduleRepository.findAll());

        return response;
    }
}

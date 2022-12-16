package com.example.demo.Registration;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/users/register")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping
    public JSONObject register(@RequestBody JSONObject requestBody) {
        JSONObject response = registrationService.register(requestBody);
        return response;
    }
}

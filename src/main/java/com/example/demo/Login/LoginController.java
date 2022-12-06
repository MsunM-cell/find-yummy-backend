package com.example.demo.Login;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/users/login")
@AllArgsConstructor
public class LoginController {

    private LoginService loginService;

    @PostMapping
    public JSONObject login(@RequestBody JSONObject requestBody) {
        JSONObject response = loginService.login(requestBody);
        return response;
    }
}

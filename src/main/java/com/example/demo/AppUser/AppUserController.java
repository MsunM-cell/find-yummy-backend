package com.example.demo.AppUser;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/users")
@AllArgsConstructor
public class AppUserController {

    private AppUserService appUserService;

    @GetMapping(path = "{userId}")
    public JSONObject getUserById(@PathVariable("userId") Long userId) {
        AppUser appUser = appUserService.getUserById(userId);

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("info", appUser);
        JSONObject temp = response.getJSONObject("info");
        temp.remove("password");
        response.put("info", temp);

        return response;
    }

    @GetMapping
    public JSONObject getAllUsers() {
        List<AppUser> appUsers = appUserService.getAllUsers();

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("users", appUsers);

        return response;
    }
}

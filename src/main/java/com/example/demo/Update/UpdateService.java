package com.example.demo.Update;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.AppUser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UpdateService {

    private AppUserService appUserService;

    public JSONObject updateAppUser(Long id, JSONObject requestBody) {
        appUserService.updateAppUser(
                id,
                requestBody.getString("password"),
                requestBody.getString("phone"),
                requestBody.getString("detail")
        );

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "Success");

        return response;
    }
}

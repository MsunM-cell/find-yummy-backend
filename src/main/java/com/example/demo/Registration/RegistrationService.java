package com.example.demo.Registration;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.AppUser.AppUser;
import com.example.demo.AppUser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

    private AppUserService appUserService;

    public JSONObject register(JSONObject requestBody) {
        Long id = appUserService.addAppUser(
                new AppUser(
                        requestBody.get("username").toString(),
                        requestBody.get("password").toString(),
                        Integer.parseInt(requestBody.get("type").toString()),
                        requestBody.get("name").toString(),
                        Integer.parseInt(requestBody.get("id_card_type").toString()),
                        requestBody.get("id_card_num").toString(),
                        requestBody.get("city").toString()
                )
        );

        JSONObject response = new JSONObject();
        if (id == -1) {
            response.put("code", -1);
            response.put("msg", "用户名已经被使用！");
        } else {
            response.put("code", 200);
            response.put("msg", "success");
            response.put("id", id);
        }

        return response;
    }
}

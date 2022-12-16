package com.example.demo.Login;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.AppUser.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginService {

    private AppUserService appUserService;

    public JSONObject login(JSONObject requestBody) {
        JSONObject info = appUserService.findAppUser(
                requestBody.get("username").toString(),
                requestBody.get("password").toString()
        );

        JSONObject response = new JSONObject();
        Long id = Long.parseLong(info.getString("id"));
        if (id == -1) {
            response.put("code", -1);
            response.put("msg", "用户名不存在！");
        } else if (id == -2) {
            response.put("code", -1);
            response.put("msg", "密码不正确！");
        } else {
            response.put("code", 200);
            response.put("msg", "success");
            response.put("id", id);
            response.put("type", Integer.parseInt(info.getString("type")));
            response.put("city", info.getString("city"));
        }

        return response;
    }
}

package com.example.demo.Update;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users/")
@AllArgsConstructor
public class UpdateController {

    private UpdateService updateService;

    @PutMapping(path = "{id}")
    public JSONObject updateAppUser(@PathVariable("id") Long id,
                                    @RequestBody JSONObject requestBody) {
        return updateService.updateAppUser(id, requestBody);
    }
}

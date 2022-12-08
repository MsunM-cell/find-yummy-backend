package com.example.demo.TasteResponse;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/responses")
@AllArgsConstructor
public class TasteResponseController {

    private TasteResponseService tasteResponseService;

    @PostMapping
    public JSONObject addTasteResponse(@RequestBody JSONObject requestBody) {
        Long id = tasteResponseService.addTasteResponse(requestBody);

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("id", id);

        return response;
    }

    @PutMapping(path = "{responseId}")
    public JSONObject updateTasteResponse(@PathVariable("responseId") Long responseId,
                                          @RequestBody JSONObject requestBody) {
        tasteResponseService.updateTasteResponse(responseId, requestBody);

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");

        return response;
    }

    @DeleteMapping(path = "{responseId}")
    public JSONObject deleteTasteResponse(@PathVariable("responseId") Long responseId) {
        tasteResponseService.deleteTasteResponse(responseId);

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");

        return response;
    }
}

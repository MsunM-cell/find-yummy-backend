package com.example.demo.TasteResponse;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.RequestsToResponses;
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
        Boolean is_updatable = tasteResponseService.updateTasteResponse(responseId, requestBody);

        JSONObject response = new JSONObject();
        if (is_updatable) {
            response.put("code", 200);
            response.put("msg", "success");
        } else {
            response.put("code", -1);
            response.put("msg", "已有接受者，无法更改");
        }

        return response;
    }

    @DeleteMapping(path = "{responseId}")
    public JSONObject deleteTasteResponse(@PathVariable("responseId") Long responseId) {
        Boolean is_deletable = tasteResponseService.deleteTasteResponse(responseId);

        JSONObject response = new JSONObject();
        if (is_deletable) {
            response.put("code", 200);
            response.put("msg", "success");
        } else {
            response.put("code", -1);
            response.put("msg", "已有响应者，无法删除");
        }

        return response;
    }

    @GetMapping(path = "{userId}")
    public JSONObject getTasteResponsesByUserId(@PathVariable("userId") Long userId,
                                                @RequestParam(name = "page") Integer page) {
        return tasteResponseService.getTasteResponsesByUserId(userId, page);
    }
}

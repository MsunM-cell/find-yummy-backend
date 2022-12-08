package com.example.demo.TasteRequest;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
public class TasteRequestController {

    private TasteRequestService tasteRequestService;

    @PostMapping
    public JSONObject addTasteRequest(@RequestBody JSONObject requestBody) {
        Long id = tasteRequestService.addTasteRequest(requestBody);

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("id", id);
        return response;
    }

    @PutMapping(path = "{requestId}")
    public JSONObject updateTasteRequest(@PathVariable("requestId") Long requestId,
                                         @RequestBody JSONObject requestBody) {
        tasteRequestService.updateTasteRequest(requestId, requestBody);

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");

        return response;
    }

    @DeleteMapping(path = "{requestId}")
    public JSONObject deleteTasteRequest(@PathVariable("requestId") Long requestId) {
        tasteRequestService.deleteTasteRequest(requestId);

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");

        return response;
    }

    @GetMapping
    public JSONObject getTasteRequests(@RequestParam(name = "user", required = false) Long userId,
                                       @RequestParam(name = "city", required = false) String city) {
        if (userId == null) {
            return tasteRequestService.getTasteRequestsByCity(city);
        }

        return tasteRequestService.getTasteRequestsByUserId(userId);
    }
}

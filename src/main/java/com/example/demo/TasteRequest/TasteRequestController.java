package com.example.demo.TasteRequest;

import com.alibaba.fastjson.JSONObject;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
public class TasteRequestController {

//    @Value("${file-save-path}")
//    private String file_save_path;

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

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
        Boolean is_updatable = tasteRequestService.updateTasteRequest(requestId, requestBody);

        JSONObject response = new JSONObject();
        if (is_updatable) {
            response.put("code", 200);
            response.put("msg", "success");
        } else {
            response.put("code", -1);
            response.put("msg", "已有响应者，无法更改");
        }

        return response;
    }

    @DeleteMapping(path = "{requestId}")
    public JSONObject deleteTasteRequest(@PathVariable("requestId") Long requestId) {
        Boolean is_deletable = tasteRequestService.deleteTasteRequest(requestId);

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

    @GetMapping
    public JSONObject getTasteRequests(@RequestParam(name = "user", required = false) Long userId,
                                       @RequestParam(name = "city", required = false) String city,
                                       @RequestParam(name = "name", required = false) String name,
                                       @RequestParam(name = "type", required = false) String type,
                                       @RequestParam(name = "page", required = false) Integer page) {
        tasteRequestService.checkExpiredTasteRequests();

        if (userId != null) {
            return tasteRequestService.getTasteRequestsByUserId(userId, page - 1);
        } else if (city != null) {
            return tasteRequestService.getTasteRequestsByCity(city, page - 1);
        } else if (name != null) {
            return tasteRequestService.getTasteRequestsByFuzzyName(name, page - 1);
        } else if (type != null) {
            return tasteRequestService.getTasteRequestsByType(type, page - 1);
        }

        // 没有过滤器，返回所有请求信息
        return tasteRequestService.getAllTasteRequests(page - 1);
    }

    @GetMapping(path = "responses/{requestId}")
    public JSONObject getTasteRequestsResponses(@PathVariable("requestId") Long requestId) {
        return tasteRequestService.getTasteRequestsResponses(requestId);
    }

    @PostMapping(path = "responses")
    public JSONObject acceptTasteRequestResponse(@RequestBody JSONObject requestBody) {
        tasteRequestService.acceptTasteRequestResponse(requestBody);

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");

        return response;
    }

    @PostMapping("images")
    public JSONObject uploadImages(MultipartFile file, HttpServletRequest req) {
        String format = sdf.format(new Date());
        File folder = new File("/Users/gutianyang/大四/上课/Web/find-yummy-backend/src/main/resources/static/uploadFile/" + format);
        String filePath = "";
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }

        String oldName = file.getOriginalFilename();
        assert oldName != null;
        String newName = UUID.randomUUID().toString() +
                oldName.substring(oldName.lastIndexOf("."), oldName.length());
        try {
            file.transferTo(new File(folder, newName));
            filePath = req.getScheme() + "://" + req.getServerName() + ":" +
                    req.getServerPort() + "/uploadFile/" + format + newName;
        } catch (IOException e) {
            JSONObject response = new JSONObject();
            response.put("code", -1);
            response.put("msg", "上传失败");

            return response;
        }

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("url", filePath);

        return response;
    }

    @GetMapping(path = "{request_id}")
    public JSONObject getTasteRequestById(@PathVariable("request_id") Long request_id) {
        return tasteRequestService.getTasteRequestById(request_id);
    }

    @GetMapping(path = "test")
    public JSONObject getTest() {
        return tasteRequestService.getTest();
    }

    @GetMapping(path = "type")
    public JSONObject getTasteTypes() {
        return tasteRequestService.getTasteTypes();
    }
}

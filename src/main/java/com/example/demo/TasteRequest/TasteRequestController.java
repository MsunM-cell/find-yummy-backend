package com.example.demo.TasteRequest;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.RequestsToResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping(path = "/requests")
@AllArgsConstructor
public class TasteRequestController {

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
                                       @RequestParam(name = "page", required = false) Integer page) {
        if (userId != null) {
            return tasteRequestService.getTasteRequestsByUserId(userId, page);
        } else if (city != null) {
            return tasteRequestService.getTasteRequestsByCity(city, page);
        } else if (name != null) {
            return tasteRequestService.getTasteRequestsByFuzzyName(name, page);
        }

        // 没有过滤器，返回所有请求信息
        return tasteRequestService.getAllTasteRequests(page);
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
    public JSONObject uploadImages(MultipartFile image, HttpServletRequest req) {
        String realPath =
                req.getSession().getServletContext().getRealPath("/uploadFile/");
        String format = sdf.format(new Date());
        File folder = new File(realPath + format);
        String filePath = "";
        if (!folder.isDirectory()) {
            folder.mkdirs();
            String oldName = image.getOriginalFilename();
            assert oldName != null;
            String newName = UUID.randomUUID().toString() +
                    oldName.substring(oldName.lastIndexOf("."), oldName.length());
            try {
                image.transferTo(new File(folder, newName));
                filePath = req.getScheme() + "://" + req.getServerName() + ":" +
                        req.getServerPort() + "/uploadFile/" + format + newName;

            } catch (IOException e) {
                JSONObject response = new JSONObject();
                response.put("code", -1);
                response.put("msg", "上传失败");

                return response;
            }
        }

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("url", filePath);

        return response;
    }

    @GetMapping(path = "test")
    public JSONObject getTest() {
        return tasteRequestService.getTest();
    }
}

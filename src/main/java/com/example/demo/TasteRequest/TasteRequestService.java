package com.example.demo.TasteRequest;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.AppUser.AppUser;
import com.example.demo.AppUser.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TasteRequestService {

    private TasteRequestRepository tasteRequestRepository;
    private AppUserRepository appUserRepository;

    public Long addTasteRequest(JSONObject requestBody) {
        TasteRequest tasteRequest = new TasteRequest(
                Long.parseLong(requestBody.get("user").toString()),
                requestBody.get("type").toString(),
                requestBody.get("name").toString(),
                requestBody.get("detail").toString(),
                Double.parseDouble(requestBody.get("max_price").toString()),
                LocalDate.parse(requestBody.get("end_date").toString(), DateTimeFormatter.ISO_DATE),
                requestBody.getString("image")
        );
        tasteRequest.setCreateTime(LocalDateTime.now());
        tasteRequest.setModifyTime(LocalDateTime.now());
        tasteRequest.setState(0);
        tasteRequestRepository.save(tasteRequest);
        return tasteRequest.getId();
    }

    @Transactional
    public void updateTasteRequest(Long requestId, JSONObject requestBody) {
        TasteRequest tasteRequest = tasteRequestRepository.findTasteRequestById(requestId).get();
        if (requestBody.containsKey("type")) {
            tasteRequest.setTasteType(requestBody.getString("type"));
        }
        if (requestBody.containsKey("name")) {
            tasteRequest.setName(requestBody.getString("name"));
        }
        if (requestBody.containsKey("detail")) {
            tasteRequest.setDetail(requestBody.getString("detail"));
        }
        if (requestBody.containsKey("max_price")) {
            tasteRequest.setMaxPrice(Double.parseDouble(requestBody.getString("max_price")));
        }
        if (requestBody.containsKey("end_date")) {
            tasteRequest.setEndTime(LocalDate.parse(requestBody.getString("end_date"), DateTimeFormatter.ISO_DATE));
        }
        if (requestBody.containsKey("image")) {
            tasteRequest.setImageUrl(requestBody.getString("image"));
        }
        tasteRequest.setModifyTime(LocalDateTime.now());
    }

    public void deleteTasteRequest(Long requestId) {
        tasteRequestRepository.deleteById(requestId);
    }

    public JSONObject getTasteRequestsByUserId(Long userId) {
        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");

        List<TasteRequest> tasteRequestsByUserId = tasteRequestRepository.findTasteRequestsByUserId(userId);
        response.put("taste_requests", tasteRequestsByUserId);

        return response;
    }

    public JSONObject getTasteRequestsByCity(String city) {
        List<AppUser> appUsers = appUserRepository.findAppUsersByCity(city);
        List<Long> userIds = appUsers.stream().map(AppUser::getId).toList();

        List<TasteRequest> tasteRequests = tasteRequestRepository.findTasteRequestsByUserIdIn(userIds);

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("taste_requests", tasteRequests);

        return response;
    }
}

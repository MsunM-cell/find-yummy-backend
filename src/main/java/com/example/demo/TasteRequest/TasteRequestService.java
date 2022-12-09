package com.example.demo.TasteRequest;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.AppUser.AppUser;
import com.example.demo.AppUser.AppUserRepository;
import com.example.demo.RequestsToResponses;
import com.example.demo.SuccessSchedule.SuccessSchedule;
import com.example.demo.SuccessSchedule.SuccessScheduleRepository;
import com.example.demo.TasteResponse.TasteResponse;
import com.example.demo.TasteResponse.TasteResponseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TasteRequestService {

    private TasteRequestRepository tasteRequestRepository;
    private TasteResponseRepository tasteResponseRepository;
    private SuccessScheduleRepository successScheduleRepository;
    private AppUserRepository appUserRepository;
    private RequestsToResponses requestsToResponses;

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
    public Boolean updateTasteRequest(Long requestId, JSONObject requestBody) {
        TasteRequest tasteRequest = tasteRequestRepository.findTasteRequestById(requestId).get();
        if (tasteRequest.getState() != 0) {
            // 已经有响应者，无法更改请求信息
            return false;
        }
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

        return true;
    }

    @Transactional
    public Boolean deleteTasteRequest(Long requestId) {
        TasteRequest tasteRequest = tasteRequestRepository.findTasteRequestById(requestId).get();
        if(tasteRequest.getState() != 0) {
            return false;
        }
        tasteRequest.setState(2);

        return true;
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

    public JSONObject getTasteRequestsResponses(Long requestId) {
        Collection<Long> responseIds = requestsToResponses.getMap().get(requestId);
        Collection<TasteResponse> tasteResponses = tasteResponseRepository.findAllById(responseIds);

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("taste_responses", tasteResponses);

        return response;
    }

    @Transactional
    public void acceptTasteRequestResponse(JSONObject requestBody) {
        Long request_id = Long.parseLong(requestBody.getString("request_id"));
        Long response_id = Long.parseLong(requestBody.getString("response_id"));
        Long request_user_id = Long.parseLong(requestBody.getString("request_user_id"));
        Long response_user_id = Long.parseLong(requestBody.getString("response_user_id"));
        Integer is_accept = Integer.parseInt(requestBody.getString("is_accept"));

        if (is_accept == 0) {
            // 拒绝该响应
            // 将"请品鉴"响应状态设置为2拒绝
            TasteResponse tasteResponse = tasteResponseRepository.findById(response_id).get();
            tasteResponse.setState(2);
        } else {
            // 接受该响应
            // 将"寻味道"请求状态设置为4已完成
            TasteRequest tasteRequest = tasteRequestRepository.findTasteRequestById(request_id).get();
            tasteRequest.setState(4);
            // 将"请品鉴"响应状态设置为1同意
            TasteResponse tasteResponse = tasteResponseRepository.findById(response_id).get();
            tasteResponse.setState(1);
            // 交易成功，加入"寻味道"成功明细表
            SuccessSchedule successSchedule = new SuccessSchedule(
                    request_id,
                    response_id,
                    request_user_id,
                    response_user_id,
                    LocalDateTime.now(),
                    3.0,
                    1.0
            );
            successScheduleRepository.save(successSchedule);
        }
    }
}

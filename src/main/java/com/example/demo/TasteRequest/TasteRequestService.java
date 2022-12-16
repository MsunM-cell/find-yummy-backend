package com.example.demo.TasteRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.AppUser.AppUser;
import com.example.demo.AppUser.AppUserRepository;
import com.example.demo.InterRevenue.InterRevenue;
import com.example.demo.InterRevenue.InterRevenueRepository;
import com.example.demo.RequestsToResponses;
import com.example.demo.SuccessSchedule.SuccessSchedule;
import com.example.demo.SuccessSchedule.SuccessScheduleRepository;
import com.example.demo.TasteResponse.TasteResponse;
import com.example.demo.TasteResponse.TasteResponseRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TasteRequestService {

    private AppUserRepository appUserRepository;
    private TasteRequestRepository tasteRequestRepository;
    private TasteResponseRepository tasteResponseRepository;
    private SuccessScheduleRepository successScheduleRepository;
    private InterRevenueRepository interRevenueRepository;
    private RequestsToResponses requestsToResponses;

    public Long addTasteRequest(JSONObject requestBody) {
        System.out.println(requestBody.get("end_date"));
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
        tasteRequest.setCity(appUserRepository.findAppUserById(Long.parseLong(requestBody.get("user").toString())).get().getCity());
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
        if (tasteRequest.getState() != 0) {
            return false;
        }
        tasteRequest.setState(2);

        return true;
    }

    public JSONObject getTasteRequestsByUserId(Long userId, Integer page) {
        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");

        PageRequest pageRequest = PageRequest.of(page, 8);
        List<TasteRequest> tasteRequestsByUserId = tasteRequestRepository.findTasteRequestsByUserId(userId, pageRequest);
        response.put("total", tasteRequestRepository.findTasteRequestsByUserId(userId).size());
        response.put("taste_requests", tasteRequestsByUserId);

//        JSONArray taste_requests = response.getJSONArray("taste_requests");
//        for (int i = 0; i < taste_requests.size(); i++) {
//            JSONObject taste_request = taste_requests.getJSONObject(i);
//            Long user_id = taste_request.getLong("userId");
//            String city = appUserRepository.findAppUserById(user_id).get().getCity();
//            taste_request.put("city", city);
//        }
//        System.out.println(taste_requests);

        return response;
    }

    public JSONObject getTasteRequestsByCity(String city, Integer page) {
        List<AppUser> appUsers = appUserRepository.findAppUsersByCity(city);
        List<Long> userIds = appUsers.stream().map(AppUser::getId).toList();

        PageRequest pageRequest = PageRequest.of(page, 8);
        List<TasteRequest> tasteRequests = tasteRequestRepository.findTasteRequestsByUserIdIn(userIds, pageRequest);

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("total", tasteRequestRepository.findTasteRequestsByUserIdIn(userIds).size());
        response.put("taste_requests", tasteRequests);

        return response;
    }

    public JSONObject getTasteRequestsResponses(Long requestId) {
        JSONObject response = new JSONObject();

        Collection<Long> responseIds = requestsToResponses.getMap().get(requestId);
        if (responseIds == null) {
            response.put("code", -1);
            response.put("msg", "没有响应");
        } else {
            Collection<TasteResponse> tasteResponses = tasteResponseRepository.findAllById(responseIds);

            response.put("code", 200);
            response.put("msg", "success");
            response.put("taste_responses", tasteResponses);
        }

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

            // 持久化存储中介收益汇总表
            // 获取月份（YYYY-MM）
            String month = String.valueOf(LocalDate.now().getYear())
                    + "-" + String.valueOf(LocalDate.now().getMonthValue());
            // 获取地域（省-市）
            String city = appUserRepository.findAppUserById(request_user_id).get().getCity();
            // 获取请求类型
            String requestType = tasteRequest.getTasteType();
            Optional<InterRevenue> optionalInterRevenue = interRevenueRepository.findInterRevenueByMonthAndAndCityAndAndRequestType(month, city, requestType);
            if (optionalInterRevenue.isPresent()) {
                InterRevenue interRevenue = optionalInterRevenue.get();
                interRevenue.setSuccessCnt(interRevenue.getSuccessCnt() + 1);
                interRevenue.setRevenue(interRevenue.getRevenue() + 4.0);
            } else {
                InterRevenue interRevenue = new InterRevenue(
                        month,
                        city,
                        requestType,
                        1,
                        3.0 + 1.0
                );
                interRevenueRepository.save(interRevenue);
            }

            successScheduleRepository.save(successSchedule);
        }
    }

    public JSONObject getAllTasteRequests(Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 8);
        List<TasteRequest> tasteRequests = tasteRequestRepository.findAll(pageRequest).getContent();

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("total", tasteRequestRepository.findAll().size());
        response.put("taste_requests", tasteRequests);

        return response;
    }

    public JSONObject getTasteRequestsByFuzzyName(String name, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 8);
        List<TasteRequest> tasteRequests = tasteRequestRepository.findTasteRequestsByFuzzyName("%" + name + "%", pageRequest);

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("total", tasteRequestRepository.findTasteRequestsByFuzzyName("%" + name + "%").size());
        response.put("taste_requests", tasteRequests);

        return response;
    }

    public JSONObject getTest() {
        PageRequest pageRequest = PageRequest.of(2, 2);
        Page<TasteRequest> page = tasteRequestRepository.findAll(pageRequest);

        JSONObject response = new JSONObject();
        response.put("page", page.getContent());

        return response;
    }

    public JSONObject getTasteRequestById(Long request_id) {
        TasteRequest tasteRequest = tasteRequestRepository.findTasteRequestById(request_id).get();

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("info", tasteRequest);

        return response;
    }

    public JSONObject getTasteRequestsByType(String type, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 8);
        List<TasteRequest> tasteRequests = tasteRequestRepository.findTasteRequestsByTasteType(type, pageRequest);

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("taste_requests", tasteRequests);
        response.put("total", tasteRequestRepository.findTasteRequestsByTasteType(type).size());

        return response;
    }

    @Transactional
    public void checkExpiredTasteRequests() {
        List<TasteRequest> tasteRequests = tasteRequestRepository.findAll();
        for (int i = 0; i < tasteRequests.size(); i++) {
            Integer state = tasteRequests.get(i).getState();
            if (state == 0 || state == 1) {
                LocalDate now = LocalDate.now();
                LocalDate end = tasteRequests.get(i).getEndTime();
                if (now.isAfter(end)) {
                    tasteRequests.get(i).setState(3);
                }
            }
        }
    }

    public JSONObject getTasteTypes() {
        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("taste_types", tasteRequestRepository.findDistinctTasteType());

        return response;
    }
}

package com.example.demo.TasteResponse;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.RequestsToResponses;
import com.example.demo.TasteRequest.TasteRequest;
import com.example.demo.TasteRequest.TasteRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class TasteResponseService {

    private TasteResponseRepository tasteResponseRepository;
    private TasteRequestRepository tasteRequestRepository;
    private RequestsToResponses requestsToResponses;

    @Transactional
    public Long addTasteResponse(JSONObject requestBody) {
        TasteResponse tasteResponse = new TasteResponse(
                Long.parseLong(requestBody.getString("request_id")),
                Long.parseLong(requestBody.getString("response_user_id")),
                requestBody.getString("detail"),
                LocalDateTime.now(),
                LocalDateTime.now(),
                0
        );
        tasteResponseRepository.save(tasteResponse);

        // 设置"寻味道"方状态为"已响应"
        TasteRequest tasteRequest = tasteRequestRepository.findTasteRequestById(Long.parseLong(requestBody.getString("request_id"))).get();
        tasteRequest.setState(1);

        // 同时加入映射关系
        Map<Long, Collection<Long>> map = requestsToResponses.getMap();
        if (map.get(Long.parseLong(requestBody.getString("request_id"))) == null) {
            Collection<Long> collection = new ArrayList<Long>();
            collection.add(tasteResponse.getId());
            requestsToResponses.addNewElementToMap(Long.parseLong(requestBody.getString("request_id")), collection);
        } else {
            Collection<Long> collection = map.get(Long.parseLong(requestBody.getString("request_id")));
            collection.add(tasteResponse.getId());
        }

        return tasteResponse.getId();
    }

    @Transactional
    public Boolean updateTasteResponse(Long responseId, JSONObject requestBody) {
        TasteResponse tasteResponse = tasteResponseRepository.findById(responseId).get();
        if (tasteResponse.getState() != 0) {
            return false;
        }

        if (requestBody.containsKey("detail")) {
            tasteResponse.setDetail(requestBody.getString("detail"));
        }
        tasteResponse.setModifyTime(LocalDateTime.now());

        return true;
    }

    @Transactional
    public Boolean deleteTasteResponse(Long responseId) {
        TasteResponse tasteResponse = tasteResponseRepository.findById(responseId).get();
        if (tasteResponse.getState() != 0) {
            return false;
        }

        tasteResponse.setState(3);

        return true;
    }

    public JSONObject getTasteResponsesByUserId(Long userId, Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 5);
        List<TasteResponse> tasteResponses = tasteResponseRepository.findTasteResponsesByResponseUserId(userId, pageRequest);

        JSONObject response = new JSONObject();
        response.put("code", 200);
        response.put("msg", "success");
        response.put("total", tasteResponseRepository.findTasteResponsesByResponseUserId(userId).size());
        response.put("taste_responses", tasteResponses);

        return response;
    }
}

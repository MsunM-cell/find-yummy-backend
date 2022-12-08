package com.example.demo.TasteResponse;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.TasteRequest.TasteRequestRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TasteResponseService {

    private TasteResponseRepository tasteResponseRepository;

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

        return tasteResponse.getId();
    }

    @Transactional
    public void updateTasteResponse(Long responseId, JSONObject requestBody) {
        TasteResponse tasteResponse = tasteResponseRepository.findById(responseId).get();

        if (requestBody.containsKey("detail")) {
            tasteResponse.setDetail(requestBody.getString("detail"));
        }

        tasteResponse.setModifyTime(LocalDateTime.now());
    }

    public void deleteTasteResponse(Long responseId) {
        tasteResponseRepository.deleteById(responseId);
    }
}

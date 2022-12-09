package com.example.demo.SuccessSchedule;

import java.io.Serializable;

public class SuccessSchedulePrimaryKey implements Serializable {

    private Long requestId; // 请求标识
    private Long responseId; // 响应标识
    private Long requestUserId; // 发布用户标识
    private Long responseUserId; // 响应用户标识
}
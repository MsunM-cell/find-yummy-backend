package com.example.demo.SuccessSchedule;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table
@IdClass(SuccessSchedulePrimaryKey.class)
public class SuccessSchedule {

    @Id
    @Column(
            name = "request_id",
            updatable = false
    )
    private Long requestId; // 请求标识
    @Id
    @Column(
            name = "request_user_id",
            updatable = false
    )
    private Long requestUserId; // 发布用户标识
    @Id
    @Column(
            name = "response_user_id",
            updatable = false
    )
    private Long responseUserId; // 响应用户标识
    @Column(
            name = "success_time",
            nullable = false
    )
    private LocalDateTime successTime; // 达成日期
    @Column(
            name = "request_price",
            nullable = false
    )
    private Double requestPrice; // 发布者支付中介费（人数 * 3元）
    @Column(
            name = "response_price",
            nullable = false
    )
    private Double responsePrice; // 响应者支付中介费（1元）
}
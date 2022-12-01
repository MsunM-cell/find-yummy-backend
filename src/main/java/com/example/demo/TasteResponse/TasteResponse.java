package com.example.demo.TasteResponse;

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
public class TasteResponse {

    @Id
    @SequenceGenerator(
            name = "taste_response_sequence",
            sequenceName = "taste_response_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "taste_response_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id; // 品鉴响应标识
    @Column(
            name = "request_id",
            nullable = false
    )
    private Long requestId; // 味道请求标识；
    @Column(
            name = "response_user_id",
            nullable = false
    )
    private Long responseUserId; // 响应用户标识
    @Column(
            name = "detail"
    )
    private String detail; // 响应描述
    @Column(
            name = "create_time",
            nullable = false
    )
    private LocalDateTime createTime; // 创建时间
    @Column(
            name = "modify_time",
            nullable = false
    )
    private LocalDateTime modifyTime; // 修改时间
    @Column(
            name = "state",
            nullable = false
    )
    private Integer state; // 状态（0待接受，1同意，2拒绝，3取消）
}
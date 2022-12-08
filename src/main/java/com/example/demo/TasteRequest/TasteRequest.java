package com.example.demo.TasteRequest;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table
public class TasteRequest {

    @Id
    @SequenceGenerator(
            name = "taste_request_sequence",
            sequenceName = "taste_request_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "taste_request_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id; // 请求标识
    @Column(
            name = "user_id",
            nullable = false
    )
    private Long userId; // 发布用户标识
    @Column(
            name = "taste_type",
            nullable = false
    )
    private String tasteType; // 味道类型（家乡小吃、地方特色小馆、香辣味、甜酸味、绝一味菜等）
    @Column(
            name = "name",
            nullable = false
    )
    private String name; // 请求主题名称
    @Column(
            name = "detail",
            nullable = false
    )
    private String detail; // 请求描述
    @Column(
            name = "max_price",
            nullable = false
    )
    private Double maxPrice; // 最高单价
    @Column(
            name = "end_time",
            nullable = false
    )
    private LocalDate endTime; // 请求结束日期
    @Column(
            name = "image_url"
    )
    private String imageUrl; // 请求介绍照片（可空）
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
    private Integer state; // 状态（3已完成、0待响应、1已取消、2到期未达成）

    public TasteRequest(Long userId,
                        String tasteType,
                        String name,
                        String detail,
                        Double maxPrice,
                        LocalDate endTime,
                        String imageUrl) {
        this.userId = userId;
        this.tasteType = tasteType;
        this.name = name;
        this.detail = detail;
        this.maxPrice = maxPrice;
        this.endTime = endTime;
        this.imageUrl = imageUrl;
    }
}

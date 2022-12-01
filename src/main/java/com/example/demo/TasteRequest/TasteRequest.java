package com.example.demo.TasteRequest;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
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
    private Integer state; // 状态（已完成、待响应、已取消、到期未达成）
}


//    CREATE TABLE TasteRequest (
//        id bigint,
//        userId bigint not null,
//        tasteType varchar(50) not null,
//        name varchar(50) not null,
//        detail varchar(255) DEFAULT null,
//        maxPrice double not null,
//        endTime date not null,
//        img blob DEFAULT null,
//        createTime date not null,
//        modifyTime date not null,
//        state int not null,
//        PRIMARY KEY (id)
//        ) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4

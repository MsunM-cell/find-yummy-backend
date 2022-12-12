package com.example.demo.InterRevenue;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@IdClass(InterRevenuePrimaryKey.class)
public class InterRevenue {

    @Id
    @Column(
            name = "month",
            updatable = false
    )
    private String month; // 月份（YYYY-MM）
    @Id
    @Column(
            name = "city",
            nullable = false
    )
    private String city; // 地域（省-市）
    @Id
    @Column(
            name = "request_type",
            nullable = false
    )
    private String requestType; // 请求类型
    @Column(
            name = "success_cnt",
            nullable = false
    )
    private Integer successCnt; // 达成笔数
    @Column(
            name = "revenue",
            nullable = false
    )
    private Double revenue; // 中介费收入金额
}

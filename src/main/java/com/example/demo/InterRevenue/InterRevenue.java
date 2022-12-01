package com.example.demo.InterRevenue;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table
public class InterRevenue {

    @Id
    @Column(
            name = "month",
            updatable = false
    )
    private String month; // 月份（YYYYMM）
    @Column(
            name = "city",
            nullable = false
    )
    private String city; // 地域（省-市）
    @Column(
            name = "request_type",
            nullable = false
    )
    private Integer requestType; // 请求类型
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

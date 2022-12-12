package com.example.demo.InterRevenue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalInterRevenue {
    private String month; // 月份（YYYY-MM）
    private Long total_success_cnt; // 累计成交单数
    private Double total_revenue; // 累计中介费金额
}

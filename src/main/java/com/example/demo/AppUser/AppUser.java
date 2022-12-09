package com.example.demo.AppUser;

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
public class AppUser {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id; // 用户标识
    @Column(
            name = "username",
            nullable = false,
            unique = true
    )
    private String username; // 用户名
    @Column(
            name = "password",
            nullable = false
    )
    private String password; // 登录密码
    @Column(
            name = "user_type",
            nullable = false
    )
    private Integer userType; // 用户类型（0普通用户、1系统管理员）
    @Column(
            name = "real_name",
            nullable = false
    )
    private String realName; // 用户姓名
    @Column(
            name = "id_card_type",
            nullable = false
    )
    private Integer idCardType; // 证件类型
    @Column(
            name = "id_card_num",
            nullable = false
    )
    private String idCardNum; // 证件号码
    @Column(
            name = "phone"
    )
    private String phone; // 手机号码（11位数字）
    @Column(
            name = "user_level",
            nullable = false
    )
    private Integer userLevel; // 用户级别（0一般、1VIP）
    @Column(
            name = "detail"
    )
    private String detail; // 用户简介
    @Column(
            name = "city",
            nullable = false
    )
    private String city; // 注册城市
    @Column(
            name = "sign_time",
            nullable = false
    )
    private LocalDateTime signTime; // 注册时间
    @Column(
            name = "modify_time",
            nullable = false
    )
    private LocalDateTime modifyTime; // 修改时间

    public AppUser(String username, String password, Integer userType, String realName,
                   Integer idCardType, String idCardNum, String city) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.realName = realName;
        this.idCardType = idCardType;
        this.idCardNum = idCardNum;
        this.city = city;
    }

    public AppUser() {

    }
}

package com.cloudlibrary.admin.application.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
@NoArgsConstructor
public class Auth {
    private Long uid;
    private String userId;
    private String password;
    private String userName;
    private String gender;
    private String birth;
    private String address;
    private String email;
    private String tel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Builder
    public Auth(Long uid, String userId, String password, String userName, String gender, String birth, String address, String email, String tel, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.uid = uid;
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.gender = gender;
        this.birth = birth;
        this.address = address;
        this.email = email;
        this.tel = tel;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}

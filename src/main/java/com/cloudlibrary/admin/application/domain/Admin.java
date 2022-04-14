package com.cloudlibrary.admin.application.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class Admin {
    private Long adminId;
    private String adminName;
    private String libraryName;
    private String tel;
    private String email;
    private String address;
    private String id;
    private String pw;
}

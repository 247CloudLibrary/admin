package com.cloudlibrary.admin.application.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class Admin {
    private Long adminId;
    private String adminName;
    private Long libraryId;
    private String libraryName;
    private String tel;
    private String email;
    private String address;
    private String id;
    private String pw;

    @Builder
    public Admin(Long adminId, String adminName, Long libraryId, String libraryName, String tel, String email, String address, String id, String pw) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.libraryId = libraryId;
        this.libraryName = libraryName;
        this.tel = tel;
        this.email = email;
        this.address = address;
        this.id = id;
        this.pw = pw;
    }
}

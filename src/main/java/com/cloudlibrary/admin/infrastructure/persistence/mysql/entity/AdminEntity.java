package com.cloudlibrary.admin.infrastructure.persistence.mysql.entity;

import com.cloudlibrary.admin.application.domain.Admin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class AdminEntity implements Serializable {
    private long adminId;
    private String adminName;
    private String libraryName;
    private String tell;
    private String email;
    private String address;

    public Admin toAdmin() {
        return Admin.builder()
                .build();

    }

    public AdminEntity(Admin admin) {

        this.adminId = getAdminId();
        this.adminName = getAdminName();
        this.libraryName = getLibraryName();
        this.tell = getTell();
        this.email = getEmail();
        this.address = getAddress();
    }
}

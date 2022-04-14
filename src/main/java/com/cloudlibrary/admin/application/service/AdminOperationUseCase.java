package com.cloudlibrary.admin.application.service;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

public interface AdminOperationUseCase {
    AdminReadUseCase.FindAdminResult createAdmin(AdminCreatedCommand command);
    AdminReadUseCase.FindAdminResult updateAdmin(AdminUpdateCommand command);
    void deleteAdmin(AdminDeleteCommand command);

    @EqualsAndHashCode(callSuper = false)
    @Builder
    @Getter
    @ToString
    class AdminCreatedCommand {
        private String adminName;
        private String libraryName;
        private String tel;
        private String email;
        private String address;
        private String id;
        private String pw;

    }

    @EqualsAndHashCode(callSuper = false)
    @Builder
    @Getter
    @ToString
    class AdminUpdateCommand {
        private Long adminId;
        private String adminName;
        private String libraryName;
        private String tel;
        private String email;
        private String address;
        private String id;
        private String pw;
    }

    @EqualsAndHashCode(callSuper = false)
    @Builder
    @Getter
    @ToString
    class AdminDeleteCommand {
        private Long adminId;
    }
}

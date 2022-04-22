package com.cloudlibrary.admin.application.service;

import com.cloudlibrary.admin.application.domain.Admin;
import com.cloudlibrary.admin.ui.requestBody.AdminFindPwRequest;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface AdminReadUseCase extends UserDetailsService {
    List<FindAdminResult> getAdminListAll();
    FindAdminResult getAdmin(AdminFindQuery query);
    FindAdminResult getAdminByEmail(String email);
    FindAdminResult getAdminById(String id);
    Long isValidIdAndEmail(AdminFindPwRequest request);

    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = false)
    @Getter
    @ToString
    class AdminFindQuery {
        private Long adminId;

        public AdminFindQuery(Long adminId) {
            this.adminId = adminId;
        }
    }

    @Setter
    @Getter
    @ToString
    @Builder
    class FindAdminResult {
        private Long adminId;
        private String adminName;
        private String libraryName;
        private String tel;
        private String email;
        private String address;
        private String id;
        private String changePassword;

        public static FindAdminResult findByAdmin(Admin admin) {
            return FindAdminResult.builder()
                    .adminId(admin.getAdminId())
                    .adminName(admin.getAdminName())
                    .libraryName(admin.getLibraryName())
                    .tel(admin.getTel())
                    .email(admin.getEmail())
                    .address(admin.getAddress())
                    .id(admin.getId())
                    .build();

        }
    }
}

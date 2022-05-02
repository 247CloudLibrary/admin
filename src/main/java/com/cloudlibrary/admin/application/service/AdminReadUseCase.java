package com.cloudlibrary.admin.application.service;

import com.cloudlibrary.admin.application.domain.Admin;
import com.cloudlibrary.admin.application.domain.Auth;
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

    List<FindAuthResult> getAuthListAll();

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
        private Long libraryId;
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
                    .libraryId(admin.getLibraryId())
                    .libraryName(admin.getLibraryName())
                    .tel(admin.getTel())
                    .email(admin.getEmail())
                    .address(admin.getAddress())
                    .id(admin.getId())
                    .build();
        }
    }

    @Getter
    @ToString
    @Builder
    class FindAuthResult {
        private final Long uid;
        private final String userId;
        private final String userName;
        private final String gender;
        private final String birth;
        private final String address;
        private final String email;
        private final String tel;

        public static FindAuthResult findByAuth(Auth auth) {
            return FindAuthResult.builder()
                    .uid(auth.getUid())
                    .userId(auth.getUserId())
                    .userName(auth.getUserName())
                    .gender(auth.getGender())
                    .birth(auth.getBirth())
                    .address(auth.getAddress())
                    .email(auth.getEmail())
                    .tel(auth.getTel())
                    .build();
        }
    }
}

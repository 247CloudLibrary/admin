package com.cloudlibrary.admin.application.service;

import com.cloudlibrary.admin.application.domain.Admin;
import lombok.*;

import java.util.List;

public interface AdminReadUseCase {
    List<FindAdminResult> getAdminListAll();
    FindAdminResult getAdmin(AdminFindQuery query);

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
    @Getter
    @ToString
    @Builder
    class FindAdminResult {
        private Long adminId;
        private String adminName;
        private String libraryName;
        private String tell;
        private String email;
        private String address;

        public static FindAdminResult findByAdmin(Admin admin) {
            return FindAdminResult.builder()
                    .adminId(admin.getAdminId())
                    .adminName(admin.getAdminName())
                    .libraryName(admin.getLibraryName())
                    .tell(admin.getTell())
                    .email(admin.getEmail())
                    .address(admin.getAddress())
                    .build();
        }
    }
}

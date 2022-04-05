package com.cloudlibrary.admin.application.service;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

public interface AdminOperationUseCase {
    AdminReadUseCase.FindAdminResult createAdmin(AdminCreatedCommand command);
    void updateAdmin(AdminUpdateCommand command);
    void deleteAdmin(AdminDeleteCommand command);

    @EqualsAndHashCode(callSuper = false)
    @Builder
    @Getter
    @ToString
    class AdminCreatedCommand {
        private final String rid;
        private final Long libraryId;
        private final String isbn;
        private final String title;
        private final String thumbnailImage;
        private final String coverImage;
    }

    @EqualsAndHashCode(callSuper = false)
    @Builder
    @Getter
    @ToString
    class AdminUpdateCommand {
        private final Long id;
        private final String rid;
        private final Long libraryId;
        private final String isbn;
        private final String title;
        private final String thumbnailImage;
        private final String coverImage;
    }

    @EqualsAndHashCode(callSuper = false)
    @Builder
    @Getter
    @ToString
    class AdminDeleteCommand {
        private final Long id;
    }
}

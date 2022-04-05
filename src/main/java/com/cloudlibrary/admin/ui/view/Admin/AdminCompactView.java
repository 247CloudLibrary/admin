package com.cloudlibrary.admin.ui.view.Admin;

import com.cloudlibrary.admin.application.service.AdminReadUseCase;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminCompactView {
    @ApiModelProperty(value = "관리자 PK")
    private final long adminId;
    @ApiModelProperty(value = "관리자 이름")
    private final String adminName;
    @ApiModelProperty(value = "도서관 이름")
    private final String libraryName;

    public AdminCompactView(AdminReadUseCase.FindAdminResult result) {
        this.adminId = getAdminId();
        this.adminName = getAdminName();
        this.libraryName = getLibraryName();
    }
}

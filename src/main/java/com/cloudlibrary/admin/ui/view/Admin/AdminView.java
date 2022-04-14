package com.cloudlibrary.admin.ui.view.Admin;

import com.cloudlibrary.admin.application.service.AdminReadUseCase;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminView {
    @ApiModelProperty(value = "관리자 PK")
    private final Long adminId;
    @ApiModelProperty(value = "관리자 이름")
    private final String adminName;
    @ApiModelProperty(value = "도서관 이름")
    private final String libraryName;
    @ApiModelProperty(value = "전화번호")
    private final String tel;
    @ApiModelProperty(value = "이메일주소")
    private final String email;
    @ApiModelProperty(value = "도서관주소")
    private final String address;
    @ApiModelProperty(value = "관리자 접속 ID")
    private final String id;
    @ApiModelProperty(value = "바꾼 비밀번호")
    private final String changePassword;


    public AdminView(AdminReadUseCase.FindAdminResult result) {
        this.adminId = result.getAdminId();
        this.adminName = result.getAdminName();
        this.libraryName = result.getLibraryName();
        this.tel = result.getTel();
        this.email = result.getEmail();
        this.address = result.getAddress();
        this.id = result.getId();
        this.changePassword = result.getChangePassword();
    }

}

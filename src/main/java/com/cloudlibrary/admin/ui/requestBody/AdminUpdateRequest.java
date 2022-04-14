package com.cloudlibrary.admin.ui.requestBody;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdminUpdateRequest {
    private Long adminId;
    private String adminName;
    private String libraryName;
    private String tel;
    private String email;
    private String address;
    private String id;
    private String pw;
}

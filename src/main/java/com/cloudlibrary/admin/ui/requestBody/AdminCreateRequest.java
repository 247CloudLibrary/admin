package com.cloudlibrary.admin.ui.requestBody;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdminCreateRequest {
    private String adminName;
    private Long libraryId;
    private String libraryName;
    private String tel;
    private String email;
    private String address;
    private String id;
    private String pw;
}

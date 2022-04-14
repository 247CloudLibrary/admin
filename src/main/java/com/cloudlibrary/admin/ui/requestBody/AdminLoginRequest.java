package com.cloudlibrary.admin.ui.requestBody;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdminLoginRequest {
    private String id;
    private String pw;
}

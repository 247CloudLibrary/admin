package com.cloudlibrary.admin.ui.requestBody;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdminFindPwRequest {
    private String id;
    private String email;
}

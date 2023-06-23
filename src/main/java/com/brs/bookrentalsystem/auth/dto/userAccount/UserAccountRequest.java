package com.brs.bookrentalsystem.auth.dto.userAccount;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class UserAccountRequest {

    private Integer id;
    private String userName;
    private String email;
    private String password;
    private String ip;

}

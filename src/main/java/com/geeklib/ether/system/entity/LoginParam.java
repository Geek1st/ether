package com.geeklib.ether.system.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginParam {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String verificationCode;
}

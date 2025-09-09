package com.geeklib.ether.system.security;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

import com.geeklib.ether.system.entity.LoginParam;

public class TokenFactory {
    public static AuthenticationToken create(LoginParam loginParam){
        
        if(!StringUtils.isAllBlank(loginParam.getUsername(), loginParam.getPassword())){
            return new UsernamePasswordToken(loginParam.getUsername(), loginParam.getPassword());
        }else if(!StringUtils.isAllBlank(loginParam.getEmail(), loginParam.getPassword())){
            return new EmailToken(loginParam.getEmail(), loginParam.getPassword());
        }else if(!StringUtils.isAllBlank(loginParam.getPhone(), loginParam.getPassword())){
            return new SmsCodeToken(loginParam.getPhone(), loginParam.getPassword());
        }else{
            throw new IllegalArgumentException("Invalid login parameters");
        }
    }
}

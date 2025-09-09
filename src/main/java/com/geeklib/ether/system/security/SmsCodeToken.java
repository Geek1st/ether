package com.geeklib.ether.system.security;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsCodeToken implements HostAuthenticationToken, RememberMeAuthenticationToken{
    
    private String phone;
    private String smsCode;

    public SmsCodeToken(String phone, String smCode){
        this.phone = phone;
        this.smsCode = smCode;
    }

    @Override
    public Object getPrincipal() {
        return phone;
    }

    @Override
    public Object getCredentials() {
        return smsCode;
    }

    @Override
    public boolean isRememberMe() {
        return false;
    }

    @Override
    public String getHost() {
        return null;
    }
}

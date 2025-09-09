package com.geeklib.ether.system.security;

import org.apache.shiro.authc.AuthenticationToken;

public class EmailToken implements AuthenticationToken{
    
    private String email;
    private String password;

    EmailToken(String email, String password){
        this.email = email;
        this.password = password;
    }

    @Override
    public Object getPrincipal() {
        return email;
    }

    @Override
    public Object getCredentials() {
        return password;
    }
}

package com.geeklib.ether.common.realm;

import org.apache.shiro.authc.AuthenticationToken;

public class JwtToken implements AuthenticationToken{
    private String jwtToken;

    public JwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public Object getPrincipal() {
        return jwtToken;
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }

    
}

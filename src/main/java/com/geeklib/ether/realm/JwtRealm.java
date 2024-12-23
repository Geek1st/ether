package com.geeklib.ether.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.geeklib.ether.utils.JwtUtils;

public class JwtRealm extends AuthorizingRealm{

    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // String jwtToken = token.getCredentials().toString();
        // String username = JwtUtils.getUsername(jwtToken);

        // if(null == username){
        //     throw new AuthenticationException("token无效");
        // }

        // if(!supports(token)){
        //     throw new AuthenticationException("token无效");
        // }

        // return new SimpleAuthenticationInfo(username,jwtToken,getName());
        return null;
    }
    
}

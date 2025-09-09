package com.geeklib.ether.common.realm;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.BearerToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.geeklib.ether.common.utils.JwtUtils;
import com.geeklib.ether.system.entity.User;
import com.geeklib.ether.system.service.UserService;

public class JwtRealm extends AuthorizingRealm {

    @Resource
    UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        if(token instanceof BearerToken){
			return true;
		}
		return super.supports(token);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = principals.getPrimaryPrincipal().toString();
        User user = userService.getUser(username);
        Set<String> permission = new HashSet<>();
        permission.add("permission");
        permission.add("project:get");
        permission.add("project:list");
        permission.add("project:create");
        permission.add("project:update");
        permission.add("project:delete");
        authorizationInfo.setStringPermissions(permission);

        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String jwt = token.getCredentials().toString();

        if (JwtUtils.validateToken(jwt)) {
            String username = JwtUtils.getUsername(jwt);
            return new SimpleAuthenticationInfo(username, jwt, getName());
        } else {
            throw new AuthenticationException("token invalid");
        }
    }

}

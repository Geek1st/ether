package com.geeklib.ether.realm;

import java.util.HashSet;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.geeklib.ether.system.entity.User;
import com.geeklib.ether.system.service.UserService;

public class UsernamePasswordRealm extends AuthorizingRealm{

	@Resource
	UserService userService;

	@Override
	public boolean supports(AuthenticationToken token) {
		if(token instanceof UsernamePasswordToken){
			return true;
		}
		return super.supports(token);
	}
    
    @Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = ((User)principals.getPrimaryPrincipal()).getUsername();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		
		authorizationInfo.addRoles(new HashSet<>());
		
		return authorizationInfo;
	}

	/**
	 * @see org.apache.shiro.realm.AuthorizingRealm
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)  {
		
		UsernamePasswordToken usernamepasswordToken = (UsernamePasswordToken)token;
		String username = usernamepasswordToken.getUsername();
		String password = String.valueOf(usernamepasswordToken.getPassword());

		User user = userService.getUserByUsernameAndPassword(username, password);
        
		if(null == user){
			throw new AuthenticationException("用户名或密码错误");
		}
		
		AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user,password,getName());
		return authenticationInfo;
	}
}

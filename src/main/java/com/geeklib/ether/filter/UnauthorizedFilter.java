package com.geeklib.ether.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

public class UnauthorizedFilter extends AuthenticationFilter {
    

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if(!SecurityUtils.getSubject().isAuthenticated()){
            ((HttpServletResponse)response).setStatus(401);
        }
        return false;
    }
    
}

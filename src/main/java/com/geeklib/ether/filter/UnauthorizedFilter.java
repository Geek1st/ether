package com.geeklib.ether.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.tools.ant.taskdefs.condition.Http;

import com.geeklib.ether.utils.JwtUtils;

public class UnauthorizedFilter extends AccessControlFilter {
 
    private boolean validateSession(){
        return SecurityUtils.getSubject().isAuthenticated();
    }

    private boolean validateToken(HttpServletRequest request){
        /*
         * 只验证token是否合法，不验证是否是当前用户
         * 任何持有token的客户端都认为是合法的，可以用token代理调用
         * 面向客户端完全无状态
         * 合法性包括：token格式是否正确，token是否过期，token是否被篡改
         */
        String token = ((HttpServletRequest) request).getHeader("Authorization");

        if (null == token || !token.startsWith("Bearer ")) {
            return false;
        }

        if (null == JwtUtils.parseToken(token.substring(7)).getSubject()) { // 去掉Bearer前缀
            return false;
        }
        return true;
    }

    // private boolean validateApiKey(){
    //     return true;
    // }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {

        if(validateSession() || validateToken((HttpServletRequest) request)){
            return true;
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        return false;
    }
}

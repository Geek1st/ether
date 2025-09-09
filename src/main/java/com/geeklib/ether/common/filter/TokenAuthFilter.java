package com.geeklib.ether.common.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.AccessControlFilter;

public class TokenAuthFilter extends AccessControlFilter{
 
    protected String getToken(ServletRequest request){

        String token = ((HttpServletRequest) request).getHeader("Authorization");

        if (null != token && token.startsWith("Bearer ")) {
            return token.substring(7).trim();
        }else{
            return null;
        }

    }

    private boolean validateToken(String token){
        return false;
    }

 

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        
        if(getToken(request) != null){
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

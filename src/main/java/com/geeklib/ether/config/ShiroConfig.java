package com.geeklib.ether.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.geeklib.ether.filter.JwtFilter;
import com.geeklib.ether.filter.UnauthorizedFilter;
import com.geeklib.ether.realm.UsernamePasswordRealm;

@Configuration
public class ShiroConfig {
    
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>(){{
            
            put("/login","anon");
            put("/logout","anon");
            //put("/**", "authc");
            //put("/**", "unauthorizedFilter");
            put("/api/**", "jwtFilter, unauthorizedFilter");
        }};

        Map<String, Filter> filters = new HashMap<String, Filter>(){{
            put("unauthorizedFilter", unauthorizedFilter());
            put("jwtFilter", jwtFilter());
        }};
   

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setFilters(filters);
        // shiroFilterFactoryBean.setLoginUrl("/login");
        // shiroFilterFactoryBean.setUnauthorizedUrl("/login");
        // shiroFilterFactoryBean.setSuccessUrl("/hello");
        return shiroFilterFactoryBean;
    }

    @Bean
    public SessionsSecurityManager securityManager(){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        
        securityManager.setRealm(usernamePasswordRealm());
        // securityManager.setRealm(jwtRealm());
        return securityManager;
    }
    
    @Bean
    public UsernamePasswordRealm usernamePasswordRealm(){
        return new UsernamePasswordRealm();
    }

    @Bean
    public UnauthorizedFilter unauthorizedFilter(){
        return new UnauthorizedFilter();
    }

    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter();
    }
}

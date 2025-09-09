package com.geeklib.ether.common.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.geeklib.ether.common.filter.ApiKeyAuthFilter;
import com.geeklib.ether.common.filter.TokenAuthFilter;
import com.geeklib.ether.common.realm.JwtRealm;
import com.geeklib.ether.common.realm.UsernamePasswordRealm;


@Configuration
public class ShiroConfig {
    
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>(){{
            
            put("/login","anon");
            put("/logout","anon");
            put("/project/**", "authcBearer");
        }};

        Map<String, Filter> filters = new HashMap<String, Filter>(){{
            // put("jwtAuthFilter", jwtAuthFilter());
            // put("tokenAuthFilter", tokenAuthFilter());
            // put("apiKeyAuthFilter", apiKeyAuthFilter());
        }};
   

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setFilters(filters);
        return shiroFilterFactoryBean;
    }

    @Bean
    public SessionsSecurityManager securityManager(){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // ModularRealmAuthenticator authenticator = new ModularRealmAuthenticator();
        // authenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        // securityManager.setAuthenticator(authenticator);

        List<Realm> realms = new ArrayList<>(){
            {
                add(jwtRealm()); 
                add(usernamePasswordRealm());
            }
        };
        securityManager.setRealms(realms);

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator);
       
        securityManager.setSubjectDAO(subjectDAO);
        
        // securityManager.setRealm(jwtRealm());
        return securityManager;
    }
    
    @Bean
    public UsernamePasswordRealm usernamePasswordRealm(){
        return new UsernamePasswordRealm();
    }

    @Bean
    public JwtRealm jwtRealm(){
        return new JwtRealm();
    }

    @Bean
    public TokenAuthFilter tokenAuthFilter(){
        return new TokenAuthFilter();
    }

    @Bean
    public ApiKeyAuthFilter apiKeyAuthFilter(){
        return new ApiKeyAuthFilter();
    }

}

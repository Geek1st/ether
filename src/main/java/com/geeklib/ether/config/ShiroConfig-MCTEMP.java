package com.geeklib.ether.config;

import org.apache.shiro.mgt.SessionsSecurityManager;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.geeklib.ether.filter.JwtFilter;
import com.geeklib.ether.filter.UnauthorizedFilter;
import com.geeklib.ether.realm.UsernamePasswordRealm;

@Configuration
public class ShiroConfig {

    
    // @Bean
    // public ShiroFilterFactoryBean shiroFilterFactoryBean(){

    //     ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    //     Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>(){{
            
    //         put("/login","anon");
    //         put("/logout","anon");
    //         //put("/**", "authc");
    //         //put("/**", "unauthorizedFilter");
    //         put("/**", "jwtFilter");
    //     }};

    //     Map<String, Filter> filters = new HashMap<String, Filter>(){{
    //         put("unauthorizedFilter", unauthorizedFilter());
    //         put("jwtFilter", jwtFilter());
    //     }};
   

    //     shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    //     shiroFilterFactoryBean.setSecurityManager(securityManager());
    //     shiroFilterFactoryBean.setFilters(filters);

    //     return shiroFilterFactoryBean;
    // }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/login", "anon");
        chainDefinition.addPathDefinition("/logout", "anon");
        chainDefinition.addPathDefinition("/**", "jwtFilter");
        return chainDefinition;
    }

    @Bean
    public SessionsSecurityManager securityManager(){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        
        securityManager.setRealm(usernamePasswordRealm());
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

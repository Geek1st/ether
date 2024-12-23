package com.geeklib.ether.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties
@Data
public class Properties {
    
    Docker docker;

    @Data
    public class Docker{

        String host;
        String port;
        Registry registry;

        @Data
        public class Registry{
            String api;
            String username;
            String password;
        }
    }
}

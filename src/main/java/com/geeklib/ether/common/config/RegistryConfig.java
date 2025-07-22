package com.geeklib.ether.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "harbor")
@Component
@Data
public class RegistryConfig {
    
    String api;
    String username;
    String password;
}

package com.geeklib.ether.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "harbor")
public class HarborConfig {
    String username;
    String password;
}

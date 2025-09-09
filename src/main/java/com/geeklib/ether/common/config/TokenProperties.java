package com.geeklib.ether.common.config;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "token")
@Getter
@Setter
public class TokenProperties {
    
    private Duration expiration = Duration.ofDays(1);
    private Duration maxIdle = Duration.ofMinutes(30);
}

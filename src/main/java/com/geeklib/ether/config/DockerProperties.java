package com.geeklib.ether.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "docker")
@Component
@Getter
@Setter
public class DockerProperties {
    private String host;
    private int port;

}

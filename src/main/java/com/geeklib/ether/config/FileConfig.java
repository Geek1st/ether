package com.geeklib.ether.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@ConfigurationProperties(prefix = "file")
@Component
@Data
public class FileConfig {
    String dataDir;
}

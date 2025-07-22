package com.geeklib.ether.common.config;


import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "workspace")
@Getter
@Setter
public class WorkspaceProperties {
    String path;
    
    final String BUILD_PATH = "/build";
    final String ARCHIVE_PATH = "/archive";
    final String DATA_PATH = "/data";

    public String getBuildPath() {
        return path + BUILD_PATH;
    }

    public Path getDataPath() {
        return Paths.get(path, DATA_PATH);
    }
}
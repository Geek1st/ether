package com.geeklib.ether.logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.geeklib.ether.common.config.WorkspaceProperties;

public class FileLogSink implements LogSink{

    private final WorkspaceProperties workspaceProperties;

    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    final String LOG_FILE_NAME = "build.log";

    public FileLogSink (WorkspaceProperties workspaceProperties) {
        this.workspaceProperties = workspaceProperties;
    }

    @Override
    public void consume(String logName, String log) {
        Path logPath = Paths.get(workspaceProperties.getBuildPath(), logName.split("-")); 
        try {
            Files.write(logPath.resolve(LOG_FILE_NAME), log.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            logger.error("构建日志写入失败: {}", logPath, e);
        }
    }
    
}

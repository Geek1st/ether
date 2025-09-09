package com.geeklib.ether.common.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.geeklib.ether.logger.FileLogSink;
import com.geeklib.ether.logger.HazelcastLogSink;
import com.geeklib.ether.logger.LogStreamDispatcher;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class LogDispatcherConfig {

    @Bean
    public LogStreamDispatcher logStreamDispatcher(HazelcastInstance hazelcastInstance, WorkspaceProperties workspaceProperties) {
        
        return new LogStreamDispatcher(List.of(
            new FileLogSink(workspaceProperties), new HazelcastLogSink(hazelcastInstance)
        ));
    }
}   
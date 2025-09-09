package com.geeklib.ether.logger;

import java.util.List;

/**
 * 日志流分发器
 * 该类负责将日志流分发到 Hazelcast 主题
 */
public class LogStreamDispatcher {

    private final List<LogSink> logSinks;

    public LogStreamDispatcher(List<LogSink> logSinks) {
        this.logSinks = logSinks;
    }

    public void dispatch(String logName, String log){
        logSinks.forEach(logSink -> logSink.consume(logName, log));
    }
}

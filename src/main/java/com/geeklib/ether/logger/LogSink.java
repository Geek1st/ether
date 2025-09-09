package com.geeklib.ether.logger;

public interface LogSink {

    public void consume(String loggerName, String log);
}
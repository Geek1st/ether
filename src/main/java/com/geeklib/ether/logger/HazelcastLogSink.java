package com.geeklib.ether.logger;

import com.hazelcast.core.HazelcastInstance;

public class HazelcastLogSink implements LogSink{

    private final HazelcastInstance hazelcastInstance;
    
    public HazelcastLogSink(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }
    
    @Override
    public void consume(String logName, String log) {
        hazelcastInstance.getTopic(logName).publish(log);
    }
}

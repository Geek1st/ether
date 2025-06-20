package com.geeklib.ether.handler;

import java.util.Map;

import com.hazelcast.config.Config;

public class HazelcastMapRegistrarHandler {
    


    public Config autoConfig(String basePackage){
        
        Config config = new Config();
        config.setInstanceName("ether-instance");
        config.getMapConfigs().put("ether-map", new com.hazelcast.config.MapConfig()
                .setName("ether-map")
                .setBackupCount(1)
                .setTimeToLiveSeconds(3600));
        
        // Additional configurations can be added here based on the basePackage
        
        return config;
    }
}

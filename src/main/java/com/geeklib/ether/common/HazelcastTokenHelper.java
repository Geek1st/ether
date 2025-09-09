package com.geeklib.ether.common;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.geeklib.ether.common.config.TokenProperties;
import com.geeklib.ether.common.utils.JwtUtils;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

@Component
public class HazelcastTokenHelper {

    
    public static HazelcastInstance hazelcastInstance;

    public static IMap<String, String> tokenIMap;

    private final static String MAP_NAME = "TOKEN:JWT_ACCESS_TOKEN";

    public static TokenProperties tokenProperties;

    @Resource
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        HazelcastTokenHelper.hazelcastInstance = hazelcastInstance;
        tokenIMap = hazelcastInstance.getMap(MAP_NAME);
    }

    @Resource
    public void setTokenProperties(TokenProperties tokenProperties) {
        HazelcastTokenHelper.tokenProperties = tokenProperties;
    }

    public static void addToken(String token){
        String name = JwtUtils.getUsername(token);
        addToken(name, token, tokenProperties.getExpiration().toMillis(), TimeUnit.MILLISECONDS, tokenProperties.getMaxIdle().toMillis(), TimeUnit.SECONDS);
    }

    public static void addToken(String key, String value, long timeToLiveSeconds, TimeUnit timeUnit, long maxIdle, TimeUnit maxIdleUnit){
        tokenIMap.put(key, value, timeToLiveSeconds, timeUnit);
    }

    public static void refreshToken(String token){
        String name = JwtUtils.getUsername(token);
        tokenIMap.put(name, token);
    }

    public static void refreshToken(String key, String value, long timeToLiveSeconds){
        tokenIMap.put(key, value, timeToLiveSeconds, null, timeToLiveSeconds, null);
    }

    public static void removeToken(String token){
        tokenIMap.remove(token);
    }

    public static boolean containsToken(String token){
        return hazelcastInstance.getMap(MAP_NAME).containsKey(token);
    }
}
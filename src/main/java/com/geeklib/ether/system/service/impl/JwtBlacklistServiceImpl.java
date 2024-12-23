package com.geeklib.ether.system.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.geeklib.ether.system.service.JwtBlacklistService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

@Service
public class JwtBlacklistServiceImpl implements JwtBlacklistService{

    private final IMap<String, String> blacklist;

    @Autowired
    public JwtBlacklistServiceImpl(HazelcastInstance HazalcastInstance) {
        this.blacklist = HazalcastInstance.getMap("blacklist");
    }

    @Override
    public void addTokenToBlacklist(String token) {
        blacklist.put(token, "blacklist");
    }

    @Override
    public boolean isTokenInBlacklist(String token) {
        return blacklist.containsKey(token);
    }

    @Override
    public void removeTokenFromBlacklist(String token) {
        blacklist.remove(token);
    }
    
}

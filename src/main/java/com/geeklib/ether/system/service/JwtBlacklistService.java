package com.geeklib.ether.system.service;


public interface JwtBlacklistService {

    void addTokenToBlacklist(String token);

    boolean isTokenInBlacklist(String token);

    void removeTokenFromBlacklist(String token);
    
}

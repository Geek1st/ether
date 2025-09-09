package com.geeklib.ether.system.controller;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.geeklib.ether.common.HazelcastTokenHelper;
import com.geeklib.ether.common.utils.JwtUtils;
import com.geeklib.ether.system.entity.LoginParam;
import com.geeklib.ether.system.security.TokenFactory;
import com.geeklib.ether.system.service.JwtBlacklistService;
import com.hazelcast.core.HazelcastInstance;

@RestController
public class SignController {

    // @Resource
    // UserService userService;

    @Resource
    HazelcastInstance hazelcastInstance;

    // @Resource
    JwtBlacklistService jwtBlacklistService;

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody LoginParam loginParam) {
        AuthenticationToken token = TokenFactory.create(loginParam);
        Subject subject = SecurityUtils.getSubject();

        subject.login(token);
        String jwtToken = JwtUtils.generateToken(subject.getPrincipal().toString());
        HazelcastTokenHelper.addToken(jwtToken);
        return ResponseEntity.ok(jwtToken);

    }

    @PostMapping("logout")
    public ResponseEntity<Object> logout(@RequestHeader("authorization") String authorizationHeader) {
        String token = authorizationHeader.isEmpty() ? "" : authorizationHeader.substring(7);
        SecurityUtils.getSubject().logout();
        String username = JwtUtils.getUsername(token);
        hazelcastInstance.getMap("token").remove(username);
        return ResponseEntity.ok(null);
    }

}

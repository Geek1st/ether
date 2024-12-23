package com.geeklib.ether.system.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.geeklib.ether.system.entity.User;
import com.geeklib.ether.utils.JwtUtils;


@RestController
public class SignController {

    

    @PostMapping("login")
    public ResponseEntity<Object> login(@RequestBody User user){

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return ResponseEntity.ok(JwtUtils.generateToken(user.getUsername()));
        } catch (AuthenticationException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PostMapping("logout")
    public ResponseEntity<Object> logout(@RequestHeader("authorization") String authorizationHeader){ 
        String token = authorizationHeader.substring(7);
        
        //jwtBlacklistService.addTokenToBlacklist(token);
        //TODO 前端删除token后，后端刷新令牌或者加入令牌黑名单
        return ResponseEntity.ok(null);
    }
    
}

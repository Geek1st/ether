package com.geeklib.ether.system.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geeklib.ether.common.QueryParams;
import com.geeklib.ether.system.entity.User;
import com.geeklib.ether.system.service.UserService;



@RestController
@RequestMapping("/system/user")
public class UserController {
    
    @Resource
    UserService userService;

    @GetMapping("{name}")
    public ResponseEntity<User> getUserById(@PathVariable String name) {
        
        return ResponseEntity.ok(userService.getUser(name));
    }



    @GetMapping("")    
    public ResponseEntity<List<User>> listUser(QueryParams queryParams, Pageable pageable){
        return ResponseEntity.ok(userService.listUser(queryParams, pageable));
    }
}

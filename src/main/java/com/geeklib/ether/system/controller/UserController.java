package com.geeklib.ether.system.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RestController;

import com.geeklib.ether.system.entity.User;
import com.geeklib.ether.system.service.UserService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/system/user")
public class UserController extends BaseController {
    
    @Resource
    UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        
        return ResponseEntity.ok(userService.getUserById(id));
    }



    @GetMapping("")    
    public ResponseEntity<List<User>> listUser(User user){
        return ResponseEntity.ok(userService.listUser(user));
    }
}

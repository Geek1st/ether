package com.geeklib.ether.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.geeklib.ether.system.entity.User;
import com.geeklib.ether.system.mapper.UserMapper;
import com.geeklib.ether.system.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    UserMapper userMapper;

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public List<User> listUser(User user) {
        return null;
    }

    @Override
    public boolean validateUser(String username, String password) {
        
        return getUserByUsernameAndPassword(username, password) != null;
    }
    
}

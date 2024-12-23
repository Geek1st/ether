package com.geeklib.ether.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Example;
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
        return userMapper.findByUsername(username).get(0);
    }

    @Override
    public User getUserByUsernameAndPassword(String username, String password) {
        return userMapper.findByUsernameAndPassword(username, password).get(0);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.findById(id).get();
    }

    @Override
    public List<User> listUser(User user) {
        return userMapper.findAll(Example.of(user));
    }

    @Override
    public boolean validateUser(String username, String password) {
        
        return getUserByUsernameAndPassword(username, password) != null;
    }
    
}

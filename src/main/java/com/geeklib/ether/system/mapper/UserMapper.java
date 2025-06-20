package com.geeklib.ether.system.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.geeklib.ether.system.entity.User;

@Component
public class UserMapper{

    List<User> findByUsername(String username){return null;};

    List<User> findByUsernameAndPassword(String username, String password){return null;};
}

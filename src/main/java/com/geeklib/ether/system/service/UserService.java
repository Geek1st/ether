package com.geeklib.ether.system.service;

import java.util.List;

import com.geeklib.ether.system.entity.User;

public interface UserService {
    
    User getUserById(Long id);

    User getUserByUsername(String username);

    User getUserByUsernameAndPassword(String username, String password);

    List<User> listUser(User user);

    boolean validateUser(String username, String password);
}

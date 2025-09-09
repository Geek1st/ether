package com.geeklib.ether.system.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.geeklib.ether.common.QueryParams;
import com.geeklib.ether.system.entity.User;

public interface UserService {

    User getUser(String name);

    User getUserByNameAndPassword(String name, String password);

    List<User> listUser(QueryParams queryParams, Pageable pageable);

    void createUser(User user);

    void updateUser(User user);

    void patchUser(User user);

    void deleteUser(String name);
    
}

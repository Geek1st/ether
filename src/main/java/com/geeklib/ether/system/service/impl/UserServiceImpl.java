package com.geeklib.ether.system.service.impl;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.geeklib.ether.common.HazelcastPersistenceHelper;
import com.geeklib.ether.common.QueryParams;
import com.geeklib.ether.system.entity.User;
import com.geeklib.ether.system.service.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Override
    public User getUser(String name) {
        return HazelcastPersistenceHelper.get(name, User.class);
    }

    @Override
    public User getUserByNameAndPassword(String name, String password) {
        return HazelcastPersistenceHelper.get(name, User.class);
    }

    @Override
    public List<User> listUser(QueryParams queryParams, Pageable pageable) {
        return HazelcastPersistenceHelper.list(User.class, queryParams, pageable);
    }

    @Override
    public void createUser(User user) {
        HazelcastPersistenceHelper.create(user.getName(), user);
    }

    @Override
    public void updateUser(User user) {
        HazelcastPersistenceHelper.update(user.getName(), user);
    }

    @Override
    public void patchUser(User user) {
        HazelcastPersistenceHelper.patch(user.getName(), user);
    }

    @Override
    public void deleteUser(String name) {
        HazelcastPersistenceHelper.delete(name, User.class);
    }

    
}

package com.geeklib.ether.system.mapper;

import org.springframework.data.jpa.repository.JpaRepository;

import com.geeklib.ether.system.entity.User;
import java.util.List;


public interface UserMapper extends JpaRepository<User, Long>{

    List<User> findByUsername(String username);

    List<User> findByUsernameAndPassword(String username, String password);
}

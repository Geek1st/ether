package com.geeklib.ether.system.entity;

import java.util.Set;

import com.geeklib.ether.common.BaseEntity;
import com.geeklib.ether.common.annotation.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends BaseEntity{

    private String password;

    private Set<Role> roles;

}

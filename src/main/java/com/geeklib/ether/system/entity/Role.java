package com.geeklib.ether.system.entity;

import java.util.Set;

import com.geeklib.ether.common.BaseEntity;
import com.geeklib.ether.common.annotation.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Role extends BaseEntity{
    private String description;
    private Set<String> permissions;
}

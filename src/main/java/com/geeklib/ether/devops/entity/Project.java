package com.geeklib.ether.devops.entity;


import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.geeklib.ether.annotation.Entity;
import com.geeklib.ether.common.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Project extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Length(max = 50)
    private String name;

    @Length(max = 200)
    private String description;
}
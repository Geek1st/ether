package com.geeklib.ether.devops.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.geeklib.ether.annotation.Entity;
import com.geeklib.ether.common.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Application extends BaseEntity implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;

    private String name;


    private Long projectId;
}

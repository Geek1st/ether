package com.geeklib.ether.devops.entity;

import com.geeklib.ether.annotation.Entity;
import com.geeklib.ether.common.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Artifact extends BaseEntity {
    private String name;
    private String version;
    private String type;
    private String url;
}

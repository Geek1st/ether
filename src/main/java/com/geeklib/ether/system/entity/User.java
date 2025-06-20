package com.geeklib.ether.system.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    private String username;

    private String password;

}

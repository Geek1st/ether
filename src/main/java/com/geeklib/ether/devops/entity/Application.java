package com.geeklib.ether.devops.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.geeklib.ether.annotation.Entity;

import lombok.Data;

@Data
@Entity
public class Application implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;

    private Long id;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gmtCreate;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")

    private Date gmtModified;


    private String name;


    private Long projectId;
}

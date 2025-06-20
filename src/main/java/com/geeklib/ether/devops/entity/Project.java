package com.geeklib.ether.devops.entity;

import java.sql.Timestamp;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Project{

    private Long id;

    @Length(max = 50)

    private String name;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp gmtCreate;


    private Timestamp gmtModified;

    @Length(max = 200)
    private String description;
}
package com.geeklib.ether.devops.entity;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Entity
@Data
public class Project{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(max = 50)
    @Column
    private String name;

    @CreatedDate
    @Column
    private Timestamp gmtCreate;

    @LastModifiedDate
    @Column
    private Timestamp gmtModified;

    @Length(max = 200)
    @Column
    private String description;
}
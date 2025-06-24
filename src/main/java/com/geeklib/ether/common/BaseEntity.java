package com.geeklib.ether.common;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.geeklib.ether.annotation.Entity;
import com.hazelcast.nonapi.io.github.classgraph.json.Id;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BaseEntity {
    @Id
    private long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;
}

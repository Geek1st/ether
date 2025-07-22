package com.geeklib.ether.common;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BaseEntity implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime gmtModified;

    //RFC 1123命名规范
    @NotBlank
    @Size(max = 63)
    @Pattern(regexp = "^[a-z0-9]([-a-z0-9]*[a-z0-9])?$", 
             message = "Name必须以字母或数字开头和结尾 小写字母（a-z）数字（0-9）连字符（-）")
    @Id
    private String name;
}

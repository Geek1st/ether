package com.geeklib.ether.devops.entity;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.geeklib.ether.common.BaseEntity;
import com.geeklib.ether.common.annotation.Entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Project extends BaseEntity {
    public interface CreateGroup {
    }

    public interface UpdateGroup {
    }

    @Length(max = 2000)
    @ApiModelProperty(value = "项目描述")
    private String description;

    @Length(max = 50)
    @NotBlank
    @ApiModelProperty(value = "项目所有者", required = true)
    private String owner;

    @NotBlank
    @ApiModelProperty(value = "项目状态", required = true)
    private Status status;

    @NotBlank
    @ApiModelProperty(value = "项目类型", required = true)
    private Type type;

    private String url;

    private String repoUrl;

    @Getter
    enum Status {
        ACTIVE,
        INACTIVE;

    }

    enum Type {
        WEB("Web Application"),
        MOBILE("Mobile Application"),
        DESKTOP("Desktop Application"),
        API("API Service"),
        LIBRARY("Library");

        private final String description;

        Type(String description) {
            this.description = description;
        }
    }

    public interface UkName {
    }

}
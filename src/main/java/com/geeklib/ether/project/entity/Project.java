package com.geeklib.ether.project.entity;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.geeklib.ether.common.BaseEntity;
import com.geeklib.ether.common.annotation.Entity;
import com.geeklib.ether.common.annotation.HazelcastIndex;
import com.hazelcast.config.IndexType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Project extends BaseEntity {
    @HazelcastIndex()
    @Length(max = 2000)
    @ApiModelProperty(value = "项目描述")
    private String description;

    @HazelcastIndex(type = IndexType.HASH)
    @Length(max = 50)
    @NotBlank
    @ApiModelProperty(value = "项目所有者", required = true)
    private String owner;

    @HazelcastIndex()
    @NotBlank
    @ApiModelProperty(value = "项目状态", required = true)
    private Status status;

    @HazelcastIndex()
    @NotBlank
    @ApiModelProperty(value = "项目类型", required = true)
    private Type type;

    private boolean deleted;

    private int star;

    @HazelcastIndex()
    private String url;

    @HazelcastIndex()
    private String repoUrl;

    public interface CreateGroup {
    }

    public interface UpdateGroup {
    }

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
package com.geeklib.ether.devops.entity;

import com.geeklib.ether.common.BaseEntity;
import com.geeklib.ether.common.annotation.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Artifact extends BaseEntity {

    private String version;
    private String type;
    private String url;
    private Long size;
    private String fileName;
    private String fileExtension;
    private String projectName;
    private String applicationName;
    private Long buildNumber;
    private String md5;
    private String sha1;
}

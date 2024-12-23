package com.geeklib.ether.devops.entity;

import lombok.Data;

@Data
public class BuildInfoS2i extends BuildInfo{
    // private SCM scm;
    private String dockerfile;
    private String image;
    private String tag;
    // private Registry registry;
    // private Project project;
}

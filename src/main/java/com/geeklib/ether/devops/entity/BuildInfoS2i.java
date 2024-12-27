package com.geeklib.ether.devops.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class BuildInfoS2i extends BuildInfo{
    // private SCM scm;
    @NotNull
    private String dockerfile;
    @NotBlank
    private String image;
    @NotEmpty
    private String tag;
    // private Registry registry;
    // private Project project;
}

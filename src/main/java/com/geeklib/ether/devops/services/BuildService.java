package com.geeklib.ether.devops.services;

import java.io.File;
import java.util.List;

import org.springframework.boot.autoconfigure.info.ProjectInfoProperties.Build;
import org.springframework.stereotype.Service;

import com.geeklib.ether.devops.entity.BuildInfo;
import com.geeklib.ether.devops.entity.BuildInfoS2i;
// import com.geeklib.ether.devops.entity.Build;
import com.geeklib.ether.devops.entity.BuildInfoS2iJava;
import com.github.dockerjava.api.command.BuildImageResultCallback;

public interface BuildService {
    
    // public void build(File dockerfile, Build build);

    public BuildImageResultCallback build(BuildInfo buildInfo, String projectCode, String applicationCode);

    public BuildImageResultCallback build(BuildInfo buildInfo, File file, String projectCode, String applicationCode);

    // public void build(Build build);

    // public List<Build> listBuild();

    // public Build getBuild();
}

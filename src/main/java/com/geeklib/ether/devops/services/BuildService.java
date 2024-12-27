package com.geeklib.ether.devops.services;

import java.io.File;

import com.geeklib.ether.devops.entity.BuildInfo;
import com.github.dockerjava.api.command.BuildImageResultCallback;

public interface BuildService {
    
    // public void build(File dockerfile, Build build);

    public BuildImageResultCallback build(BuildInfo buildInfo, String projectCode, String applicationCode);

    public BuildImageResultCallback build(BuildInfo buildInfo, File file, String projectCode, String applicationCode);

    // public void build(Build build);

    // public List<Build> listBuild();

    // public Build getBuild();
}

package com.geeklib.ether.devops.services;

import java.io.File;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.geeklib.ether.devops.entity.BuildInfo;
import com.github.dockerjava.api.command.BuildImageResultCallback;

public interface BuildService {
    
    // public void build(File dockerfile, Build build);

    public BuildImageResultCallback build(BuildInfo buildInfo, String projectCode, String applicationCode);

    public BuildImageResultCallback build(BuildInfo buildInfo, File file, String projectCode, String applicationCode);

    public File exportImage(String imageName, String tag);

    public void streamBuildLogs(SseEmitter emitter);

    // public void build(Build build);

    // public List<Build> listBuild();

    // public Build getBuild();
}

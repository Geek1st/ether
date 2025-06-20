package com.geeklib.ether.devops.services;

import java.io.File;
import java.io.IOException;

import org.apache.groovy.json.internal.IO;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.geeklib.ether.devops.entity.BuildInfo;
import com.geeklib.ether.devops.entity.BuildInfoS2i;
import com.github.dockerjava.api.command.BuildImageResultCallback;

public interface BuildService {
    
    // public void build(File dockerfile, Build build);

    public BuildImageResultCallback build(BuildInfoS2i buildInfoS2i, String projectName, String applicationName) throws IOException;

    public BuildImageResultCallback build(BuildInfo buildInfo, File file, String projectCode, String applicationCode) throws IOException;

    public File exportImage(String imageName, String tag);

    public SseEmitter streamBuildLogs(String projectName, String applicationName, Long buildNumber);

    public void archive(MultipartFile file, String projectName, String applicationName, Long buildNumber) throws IOException, IllegalStateException;

    // public void build(Build build);

    // public List<Build> listBuild();

    // public Build getBuild();
}

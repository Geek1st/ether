package com.geeklib.ether.devops.services.impl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Service;

import com.geeklib.ether.config.Properties.Docker;
import com.geeklib.ether.devops.entity.BuildInfo;
import com.geeklib.ether.devops.entity.BuildInfoS2i;
// import com.geeklib.ether.devops.entity.Build;
import com.geeklib.ether.devops.entity.BuildInfoS2iJava;
import com.geeklib.ether.devops.services.BuildService;
import com.geeklib.ether.utils.DockerUtils;
import com.geeklib.ether.utils.FileUtils;
import com.github.dockerjava.api.command.BuildImageResultCallback;

@Service
public class BuildServiceImpl implements BuildService{

    // @Override
    // public void build(File dockerfile, Build build) {
    //     // TODO Auto-generated method stub
        
    // }

    // @Override
    // public void build(Build build) {
        
        
    //     switch (build.getEnvironment()) {
    //         case "java":
    //             String text = FileUtils.getTemplate("docker/s2i/tomcat/8.5.57");
    //             //DockerUtils.build(, build)
    //             break;
        
    //         default:
    //             break;
    //     }
    // }

    @Override
    public BuildImageResultCallback build(BuildInfo buildInfo, String projectCode, String applicationCode) {

        Path path = Paths.get(FileUtils.getDataDir(), projectCode, applicationCode, "Dockerfile");
        File dockerfile = FileUtils.toFile(buildInfo.getDockerfile(), path);
        
        DockerUtils.build(buildInfo, dockerfile);
        DockerUtils.push(buildInfo.getTag());
        return null;
    }

    @Override
    public BuildImageResultCallback build(BuildInfo buildInfo, File file, String projectCode, String applicationCode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'build'");
    }
    
}

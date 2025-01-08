package com.geeklib.ether.devops.services.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

// import com.geeklib.ether.devops.entity.Build;
import com.geeklib.ether.devops.services.DockerService;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;

@Service
public class DockerServiceImpl implements DockerService{

    @Resource
    DockerHttpClient dockerHttpClient;

    @Resource
    DockerClientConfig dockerClientConfig;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    // @Override
    // public BuildImageResultCallback build(Path dockerfile, Build build) {
    //     DockerClient dockerClient = DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);
    //     BuildImageResultCallback buildImageResultCallback = dockerClient.buildImageCmd()
    //         .withTags(new HashSet<String>(){{ add(build.getTag());}})
    //         .withDockerfile(dockerfile.toFile())
    //         .start();
    //     return buildImageResultCallback;
    // }

    @Override
    public void listImages() {
        DockerClient dockerClient = DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);
        
    }

    @Override
    public void info() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void push(String name) {
        DockerClient dockerClient = DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);
        dockerClient.pushImageCmd(name).start();
        
    }
    
    
}

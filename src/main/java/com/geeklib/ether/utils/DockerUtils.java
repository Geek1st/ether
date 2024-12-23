package com.geeklib.ether.utils;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.geeklib.ether.devops.entity.BuildInfo;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;


public class DockerUtils {

    static DockerHttpClient dockerHttpClient;
    
    static DockerClientConfig dockerClientConfig;

    static DockerClient dockerClient;

    @Resource
    public void setDockerHttpClient(DockerHttpClient dockerHttpClient){
        DockerUtils.dockerHttpClient = dockerHttpClient;
    }

    @Resource
    public void setDockerClientConfig(DockerClientConfig dockerClientConfig){
        DockerUtils.dockerClientConfig = dockerClientConfig;
    }

    @PostConstruct
    public void dockerUtils(){
        DockerUtils.dockerClient = DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);
    }

    public static BuildImageResultCallback build(BuildInfo buildInfo, File dockerfile){

        BuildImageResultCallback buildImageResultCallback = dockerClient.buildImageCmd()
            .withTags(new HashSet<String>(){{ add(buildInfo.getTag());}})
            .withDockerfile(dockerfile)
            .start();

        return buildImageResultCallback;
    }

    public static List<Image> listImages(){
        List<Image> images = dockerClient.listImagesCmd().withLabelFilter(new HashMap()).exec();
        return images;
    }

    public static InspectImageResponse inspectImage(String imageId){
        return dockerClient.inspectImageCmd(imageId).exec();
    }

    public static InspectContainerResponse inspectContainer(String containerId){
        return dockerClient.inspectContainerCmd(containerId).exec();
    }

    public static void removeImage(String imageId){
        dockerClient.removeImageCmd(imageId).exec();
    }

    public static Info info(){

        return dockerClient.infoCmd().exec();
    }

    public static void ping(){
        dockerClient.pingCmd().exec();
    }

    public static void push(String imageName){
        dockerClient.pushImageCmd(imageName).start();
    }
    
}

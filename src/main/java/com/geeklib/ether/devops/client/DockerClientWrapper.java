package com.geeklib.ether.devops.client;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.geeklib.ether.devops.entity.BuildInfo;
import com.geeklib.ether.devops.entity.BuildInfoS2i;
import com.geeklib.ether.devops.handler.BuildLogHandler;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageCmd;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;

/**
 * dockerclient封装类，用于调用docker api
 */
@Component
public class DockerClientWrapper {
    @Resource
    DockerHttpClient dockerHttpClient;

    @Resource
    DockerClientConfig dockerClientConfig;

    DockerClient dockerClient;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    BuildLogHandler buildLogHandler;

    @PostConstruct
    public void init() {
        dockerClient = DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);
        // dockerClient.pingCmd().exec();
    }

    public BuildImageResultCallback build(BuildInfoS2i buildInfoS2i) {
        BuildImageCmd buildImageCmd = dockerClient.buildImageCmd();

        buildImageCmd.withTags(buildInfoS2i.getTags())
                .withLabels(buildInfoS2i.getLabels())
                .withNoCache(buildInfoS2i.isNoCache())
                .withCacheFrom(null);

        if (buildInfoS2i.getTarStream() != null) {
            buildImageCmd.withTarInputStream(buildInfoS2i.getTarStream());
        } else {
            buildImageCmd.withDockerfile(buildInfoS2i.getDockerfile());
        }

        buildInfoS2i.getArgs().forEach((key, value) -> buildImageCmd.withBuildArg(key, value));

        BuildImageResultCallback buildImageResultCallback = new BuildImageResultCallback() {

            public void onNext(BuildResponseItem item) {

                buildLogHandler.writeLog(buildInfoS2i, item.getStream());

            };

            public void onError(Throwable throwable) {
                buildLogHandler.writeLog(buildInfoS2i, "Error: " + throwable.getMessage());
            };

            public void onComplete() {
                buildLogHandler.writeLog(buildInfoS2i, "Build completed successfully.");
            };

        };

        return buildImageCmd.exec(buildImageResultCallback);
    }

    public Info info() {
        Info info = dockerClient.infoCmd().exec();
        return info;
    }

    public void push(String name) {
        dockerClient.pushImageCmd(name).start();
    }

    public void deleteImage(String imageId) {
        dockerClient.removeImageCmd(imageId).exec();
    }


    public BuildImageResultCallback build(BuildInfo buildInfo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'build'");
    }
}

package com.geeklib.ether.ci.client;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.geeklib.ether.ci.entity.BuildInfo;
import com.geeklib.ether.ci.entity.BuildInfoS2i;
import com.geeklib.ether.ci.handler.BuildLogHandler;
import com.geeklib.ether.common.config.WorkspaceProperties;
import com.geeklib.ether.logger.LogStreamDispatcher;
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
    WorkspaceProperties workspaceProperties;

    @Resource
    BuildLogHandler buildLogHandler;

    @Resource
    LogStreamDispatcher logStreamDispatcher;

    @PostConstruct
    public void init() {
        dockerClient = DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);
        // dockerClient.pingCmd().exec();
    }

    public BuildImageResultCallback build(BuildInfoS2i buildInfoS2i) {
        BuildImageCmd buildImageCmd = dockerClient.buildImageCmd();

        buildImageCmd.withLabels(buildInfoS2i.getLabels())
                .withNoCache(buildInfoS2i.isNoCache())
                .withCacheFrom(null)
                .withTags(buildInfoS2i.getTags());
        if (buildInfoS2i.isNoCache()) {
            buildImageCmd.withTarInputStream(new ByteArrayInputStream(buildInfoS2i.getDockerfile().getBytes()));
        } else {
            String dockerfilePath = Paths.get(workspaceProperties.getBuildPath(), buildInfoS2i.getProjectName(), buildInfoS2i.getApplicationName(), buildInfoS2i.getBuildNumber().toString(), "dockerfile").toString();
            
            buildImageCmd.withDockerfile(new File(dockerfilePath));
        }

        Optional.ofNullable(buildInfoS2i.getArgs())
                .ifPresent(args -> args.forEach(buildImageCmd::withBuildArg));

        BuildImageResultCallback buildImageResultCallback = new BuildImageResultCallback() {

            public void onNext(BuildResponseItem item) {
                System.out.println(item.getStream());
                //logStreamDispatcher.dispatch(buildInfoS2i.getProjectName() + "-" + buildInfoS2i.getApplicationName() + "-" + buildInfoS2i.getBuildNumber(), item.getStream());

            };

            public void onError(Throwable throwable) {
                System.out.println(throwable.getMessage());
                //logStreamDispatcher.dispatch(buildInfoS2i.getProjectName() + "-" + buildInfoS2i.getApplicationName() + "-" + buildInfoS2i.getBuildNumber(), "Error: " + throwable.getMessage());
            };

            public void onComplete() {
                System.out.println("Build completed successfully.");
                //logStreamDispatcher.dispatch(buildInfoS2i.getProjectName() + "-" + buildInfoS2i.getApplicationName() + "-" + buildInfoS2i.getBuildNumber(), "Build completed successfully.");
            };

        };

        return buildImageCmd.exec(buildImageResultCallback);
        // return buildImageCmd.start();
    }

    public Info info() {
        Info info = dockerClient.infoCmd().exec();
        return info;
    }

    public void healthz() {
        dockerClient.pingCmd().exec();
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

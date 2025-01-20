package com.geeklib.ether.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.geeklib.ether.devops.entity.BuildInfo;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.ResponseItem.ProgressDetail;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.transport.DockerHttpClient;

@Component
public class DockerUtils {

    static Logger logger = LoggerFactory.getLogger(DockerUtils.class);

    @Resource
    DockerHttpClient dockerHttpClient;

    @Resource
    DockerClientConfig dockerClientConfig;

    static DockerClient dockerClient;

    @PostConstruct
    public void initDockerClient() {
        DockerUtils.dockerClient = DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);
    }

    public static void build(BuildInfo buildInfo, File dockerfile, SseEmitter emitter) {
       
        dockerClient.buildImageCmd()
                .withTags(new HashSet<String>() {
                    {
                        add(buildInfo.getTag());
                    }
                })
                .withDockerfile(dockerfile)
                .exec(streamBuildLogs(emitter));
    }

    private static String generateProgressBar(long current, long total) {
        int progressBarLength = 50; // 进度条的长度
        int progress = (int) ((double) current / total * progressBarLength);
        StringBuilder progressBar = new StringBuilder("[");
        for (int i = 0; i < progressBarLength; i++) {
            if (i < progress) {
                progressBar.append("=");
            } else {
                progressBar.append(" ");
            }
        }
        progressBar.append("]");
        return progressBar.toString();
    }

    public static List<Image> listImages() {
        List<Image> images = dockerClient.listImagesCmd().withLabelFilter(new HashMap()).exec();
        return images;
    }

    public static InspectImageResponse inspectImage(String imageId) {
        return dockerClient.inspectImageCmd(imageId).exec();
    }

    public static InspectContainerResponse inspectContainer(String containerId) {
        return dockerClient.inspectContainerCmd(containerId).exec();
    }

    public static void removeImage(String imageId) {
        dockerClient.removeImageCmd(imageId).exec();
    }

    public static Info info() {

        return dockerClient.infoCmd().exec();
    }

    public static void ping() {
        dockerClient.pingCmd().exec();
    }

    public static void push(String imageName) {
        dockerClient.pushImageCmd(imageName).start();
    }

    public static void pull(String imageName) {
        dockerClient.pullImageCmd(imageName).start();
    }

    /**
     * 导出镜像文件tar
     * 
     * @param imageName
     */
    public static File export(String imageName, String tag) {
        Path path = Paths.get("/");
        try (InputStream is = dockerClient.saveImageCmd(imageName).withTag(tag).exec()) {
            Files.copy(is, path, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return path.toFile();
    }

    public static BuildImageResultCallback streamBuildLogs(SseEmitter emitter) {
        BuildImageResultCallback callback = new BuildImageResultCallback(){
            @Override
            public void onNext(BuildResponseItem item) {
                if (item != null) {
                    if (item.getStream() != null) {
                        try {
                            emitter.send(SseEmitter.event().data(item.getStream()));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (item.getErrorDetail() != null) {
                        try {
                            emitter.send(SseEmitter.event().data("Error: " + item.getErrorDetail().getMessage()));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (item.getProgressDetail() != null) {
                        ProgressDetail progressDetail = item.getProgressDetail();
                        long current = progressDetail.getCurrent() != null ? progressDetail.getCurrent() : 0;
                        long total = progressDetail.getTotal() != null ? progressDetail.getTotal() : 0;
                        String progressBar = generateProgressBar(current, total);
                        try {
                            emitter.send(SseEmitter.event().data("Progress: " + progressBar + " (" + current + "/" + total + ")"));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                    if (item.getStatus() != null) {
                        try {
                            emitter.send(SseEmitter.event().data("Status: " + item.getStatus()));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }                    }
                    if (item.getId() != null) {
                        try {
                            emitter.send(SseEmitter.event().data("ID: " + item.getId()));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                super.onNext(item);
            }

            @Override
            public void onError(Throwable throwable) {
                try {
                    emitter.send(SseEmitter.event().data("Error during build: " + throwable.getMessage()));
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
                super.onError(throwable);
            }

            @Override
            public void onComplete() {
                try {
                    emitter.send(SseEmitter.event().data("Build complete"));
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
                emitter.complete();
                super.onComplete();
            }
        };

        return callback;
    }
}

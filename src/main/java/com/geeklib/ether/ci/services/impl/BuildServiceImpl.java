package com.geeklib.ether.ci.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.geeklib.ether.ci.client.DockerClientWrapper;
import com.geeklib.ether.ci.entity.BuildInfo;
import com.geeklib.ether.ci.entity.BuildInfoS2i;
import com.geeklib.ether.ci.handler.BuildLogHandler;
import com.geeklib.ether.ci.services.BuildService;
import com.geeklib.ether.common.HazelcastPersistenceHelper;
import com.geeklib.ether.common.QueryParams;
import com.geeklib.ether.common.config.WorkspaceProperties;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.model.Info;
import com.hazelcast.core.HazelcastInstance;

@Service
public class BuildServiceImpl implements BuildService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    DockerClientWrapper dockerClientWrapper;

    @Resource
    WorkspaceProperties workspaceProperties;

    @Resource
    BuildLogHandler buildLogHandler;

    @Resource
    HazelcastInstance hazelcastInstance;

    // @Override
    // public BuildImageResultCallback build(BuildInfo buildInfo, String
    // projectCode, String applicationCode) {

    // Path applicationBuildPath = Paths.get(workspaceProperties.getBuildPathFull(),
    // projectCode, applicationCode);
    // applicationBuildPath.toFile().mkdirs();

    // dockerService.build(buildInfo);

    // Path path = Paths.get(FileUtils.getDataDir(), projectCode, applicationCode,
    // "Dockerfile");
    // //File dockerfile = FileUtils.toFile(buildInfo.getDockerfile(), path);

    // DockerUtils.build(buildInfo, buildInfo.getDockerfile(), new SseEmitter());
    // // DockerUtils.push(buildInfo.getTag());
    // return null;
    // }

    @Override
    public BuildImageResultCallback build(BuildInfoS2i buildInfoS2i, String projectName, String applicationName)
            throws IOException {

        Long maxBuildNumber =getMaxBuildNumber(projectName, applicationName);
        buildInfoS2i.setBuildNumber(maxBuildNumber + 1);
        buildInfoS2i.setNextBuildNumber(maxBuildNumber + 2);
        buildInfoS2i.setLastBuildNumber(maxBuildNumber);
        Path dockerfilePath = Paths.get(workspaceProperties.getBuildPath(), projectName, applicationName,
                String.valueOf(maxBuildNumber + 1), "dockerfile");
        Files.createDirectories(dockerfilePath.getParent());
        Files.write(dockerfilePath, buildInfoS2i.getDockerfile().getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        
        
        buildInfoS2i.setNextBuildNumber(maxBuildNumber + 1);
        buildInfoS2i.setName(projectName + "-" + applicationName + "-" + buildInfoS2i.getNextBuildNumber());
        BuildImageResultCallback buildImageResultCallback = dockerClientWrapper.build(buildInfoS2i);
        HazelcastPersistenceHelper.create(buildInfoS2i.getName(), buildInfoS2i);

        return buildImageResultCallback;
    }

    @Override
    public SseEmitter streamBuildLogs(String projectName, String applicationName, Long buildNumber) {

        SseEmitter sseEmitter = buildLogHandler.subscribeLog(projectName, applicationName, buildNumber);

        return sseEmitter;
    }

    public File exportImage(String imageName, String tag) {
        return null;
    }

    @Override
    public BuildImageResultCallback build(BuildInfo buildInfo, File file, String projectCode, String applicationCode) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'build'");
    }

    public BuildInfo createBuildInfo(BuildInfo buildInfo) {
        String key = buildInfo.getProjectName() + "-" + buildInfo.getApplicationName() + "-" + buildInfo.getBuildNumber();
        HazelcastPersistenceHelper.create(key, buildInfo);
        return buildInfo;
    }

    private void createBuildRecords(BuildInfo buildInfo, String projectCode, String applicationCode) {
        // buildMapper.saveObject(null);
        // buildMapper.findLatestBuildNumber(projectCode, applicationCode);
    }

    private void createBuildDirectory(BuildInfo buildInfo, String projectCode, String applicationCode) {
    }

    @Override
    public void archive(MultipartFile file, String projectName, String applicationName, Long buildNumber)
            throws IOException, IllegalStateException {

        // File archiveDir = new
        // File(workspaceProperties.getFullArchivePath(projectName, applicationName,
        // buildNumber));
        // file.transferTo(archiveDir);
    }

    @Override
    public BuildInfo getBuildInfo(String projectName, String applicationName, long buildNumber) {

        String key = projectName + "-" + applicationName + "-" + buildNumber;
        return HazelcastPersistenceHelper.get(key, BuildInfo.class);
    }

    @Override
    public List<BuildInfo> listBuildInfos(String projectName, String applicationName) {
        return HazelcastPersistenceHelper.list(BuildInfo.class);
    }

    @Override
    public BuildInfo updateBuildInfo(String projectName, String applicationName, long buildNumber,
            BuildInfo buildInfo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateBuildInfo'");
    }

    @Override
    public BuildInfo patchBuildInfo(String projectName, String applicationName, long buildNumber, BuildInfo buildInfo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'patchBuildInfo'");
    }

    @Override
    public boolean deleteBuildInfo(String projectName, String applicationName, long buildNumber) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteBuildInfo'");
    }

    @Override
    public Info healthz() {
        return dockerClientWrapper.info();
    }

    @Override
    public Long getMaxBuildNumber(String projectName, String applicationName){
        QueryParams queryParams = new QueryParams();
        queryParams.add(queryParams.new QueryParam("projectName", QueryParams.Operator.EQ, projectName));
        queryParams.add(queryParams.new QueryParam("applicationName", QueryParams.Operator.EQ, applicationName));
        return HazelcastPersistenceHelper.getMaxValue(queryParams, BuildInfoS2i.class, "buildNumber");
    }
}

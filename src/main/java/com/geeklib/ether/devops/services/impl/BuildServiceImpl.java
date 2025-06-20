package com.geeklib.ether.devops.services.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.geeklib.ether.config.WorkspaceProperties;
import com.geeklib.ether.devops.client.DockerClientWrapper;
import com.geeklib.ether.devops.entity.BuildInfo;
import com.geeklib.ether.devops.entity.BuildInfoS2i;
import com.geeklib.ether.devops.handler.BuildLogHandler;
import com.geeklib.ether.devops.services.BuildService;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.flakeidgen.FlakeIdGenerator;
import com.hazelcast.map.IMap;

@Service
public class BuildServiceImpl implements BuildService{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    DockerClientWrapper dockerClientWrapper;

    @Resource
    WorkspaceProperties workspaceProperties;

    @Resource
    BuildLogHandler buildLogHandler;

    @Resource
    HazelcastInstance hazelcastInstance;

    IMap<Long, BuildInfo> buildInfoMap;

    @PostConstruct
    public void init(){
        buildInfoMap = hazelcastInstance.getMap(BuildInfo.class.getSimpleName());
    }

    // @Override
    // public BuildImageResultCallback build(BuildInfo buildInfo, String projectCode, String applicationCode) {

    //     Path applicationBuildPath = Paths.get(workspaceProperties.getBuildPathFull(), projectCode, applicationCode);
    //     applicationBuildPath.toFile().mkdirs();
    
    //     dockerService.build(buildInfo);

    //     Path path = Paths.get(FileUtils.getDataDir(), projectCode, applicationCode, "Dockerfile");
    //     //File dockerfile = FileUtils.toFile(buildInfo.getDockerfile(), path);
        

    //     DockerUtils.build(buildInfo, buildInfo.getDockerfile(), new SseEmitter());
    //     // DockerUtils.push(buildInfo.getTag());
    //     return null;
    // }

    @Override
    public BuildImageResultCallback build(BuildInfoS2i buildInfoS2i, String projectName, String applicationName) throws IOException {
        

        Files.createDirectories(Paths.get(workspaceProperties.getPath()),null,null) ;
  
        BuildImageResultCallback buildImageResultCallback = dockerClientWrapper.build(buildInfoS2i);

        FlakeIdGenerator idGenerator = hazelcastInstance.getFlakeIdGenerator(BuildInfo.class.getSimpleName());

        long id = idGenerator.newId();
        buildInfoMap.put(id, buildInfoS2i);

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

    private void createBuildRecords(BuildInfo buildInfo, String projectCode, String applicationCode) {
        // buildMapper.saveObject(null);
        // buildMapper.findLatestBuildNumber(projectCode, applicationCode);
    }

    private void createBuildDirectory(BuildInfo buildInfo, String projectCode, String applicationCode) {
    }


    @Override
    public void archive(MultipartFile file, String projectName, String applicationName, Long buildNumber) throws IOException, IllegalStateException {
        
        // File archiveDir = new File(workspaceProperties.getFullArchivePath(projectName, applicationName, buildNumber));
        // file.transferTo(archiveDir);
    }
}

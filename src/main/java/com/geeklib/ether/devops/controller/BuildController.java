package com.geeklib.ether.devops.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.geeklib.ether.devops.entity.BuildInfo;
import com.geeklib.ether.devops.entity.BuildInfoS2i;
import com.geeklib.ether.devops.services.BuildService;
import com.github.dockerjava.api.command.BuildImageResultCallback;




@RestController
@RequestMapping("/devops/build")
public class BuildController {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    BuildService buildService;
    
    @PostMapping("/s2i/archive")
    public ResponseEntity<String> archive(MultipartFile file, String projectName, String applicationName, Long buildNumber) throws IOException, IllegalStateException {

        buildService.archive(file, projectName, applicationName, buildNumber);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/s2i/{projectName}/{applicationName}/{buildNumber}/log")
    public SseEmitter streamBuildLogs(@PathVariable String projectName, 
                                      @PathVariable String applicationName, 
                                      @PathVariable Long buildNumber) {
        SseEmitter sseEmitter = buildService.streamBuildLogs(projectName, applicationName, buildNumber);
        return sseEmitter;
    }

    @GetMapping("/s2i/{imageName}/{tag}")
    public ResponseEntity<File> exportImage(String imageName, String tag) {
        File file = buildService.exportImage(imageName, tag);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + file.getName() + "\"").body(file);
    }

    @GetMapping("/{imageName}")
    public ResponseEntity<Object> getImageByName(String imageName){
        //DockerClient dockerClient = DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);
       // List<Image> images = dockerClient.listImagesCmd().withImageNameFilter(imageName).exec();

        return ResponseEntity.ok("hello");
    }

    @PostMapping("/s2i/{projectCode}/{applicationCode}")
    public ResponseEntity<BuildImageResultCallback> createImage(@Valid BuildInfoS2i buildinfoS2i, @PathVariable String projectName, @PathVariable String applicationName) throws IOException{
        buildinfoS2i.setProjectName(projectName);
        buildinfoS2i.setApplicationName(applicationName);
        BuildImageResultCallback buildImageResultCallback = buildService.build(buildinfoS2i, projectName, applicationName);
        return ResponseEntity.ok(buildImageResultCallback);
    }

    @GetMapping("/{projectName}/{applicationName}/{buildNumber}")
    public ResponseEntity<BuildInfo> getBuildInfo(@PathVariable String projectName, @PathVariable String applicationName, @PathVariable long buildNumber) {
        //TODO
        return null;
    }

    @GetMapping("/{projectName}/{applicationName}")
    public ResponseEntity<List<BuildInfo>> listBuildInfo(@PathVariable String projectName, @PathVariable String applicationName){
        //TODO
        return null;
    }

    @PutMapping("/{projectName}/{applicationName}/{buildNumber}")
    public ResponseEntity<BuildInfo> updateBuildInfo(@PathVariable String projectName, @PathVariable String applicationName, @PathVariable long buildNumber, @RequestBody BuildInfo buildInfo) {
        //TODO
        return null;
    }

    @PatchMapping("/{projectName}/{applicationName}/{buildNumber}")
    public ResponseEntity<BuildInfo> patchBuildInfo(@PathVariable String projectName, @PathVariable String applicationName, @PathVariable long buildNumber, @RequestBody BuildInfo buildInfo) {
        //TODO
        return null;
    }

    @DeleteMapping("/{projectName}/{applicationName}/{buildNumber}")
    public ResponseEntity<Object> deleteBuildInfo(@PathVariable String projectName, @PathVariable String applicationName, @PathVariable long buildNumber) {
        //TODO
        return null;
    }

    @PostMapping("/b2i/{projectCode}/{applicationCode}")
    public ResponseEntity<Object> createImage(){

        return ResponseEntity.ok(null);
    }
    
}

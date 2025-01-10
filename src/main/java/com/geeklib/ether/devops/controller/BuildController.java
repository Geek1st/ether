package com.geeklib.ether.devops.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.geeklib.ether.devops.entity.BuildInfoS2i;
import com.geeklib.ether.devops.services.BuildService;
import com.geeklib.ether.devops.services.DockerService;
import com.geeklib.ether.utils.FileUtils;

import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/devops/build")
public class BuildController {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    DockerService dockerService;

    @Resource
    BuildService buildService;
    
    @GetMapping("/s2i/template")
    public ResponseEntity<String> getDockerfileTemplate(){
        String template = FileUtils.getTemplate("resources/template/docker/s2i/tomcat/8.5.57/dockerfile");
        return ResponseEntity.ok(template);
    }

    @PostMapping("/s2i/archive")
    public ResponseEntity<String> archive(MultipartFile file){
        try {
            file.transferTo(Paths.get("/"));
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ResponseEntity.ok("ok");
    }

    /**
     * Export a Docker image as a tar file
     * @param imageName the name of the image
     * @param tag the tag of the image
     * @return a ResponseEntity of the image file with a Content-Disposition header set to "attachment; filename=\"<filename>\""
     */
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
    public ResponseEntity<Object> createImage(@Valid BuildInfoS2i buildinfo, @PathVariable String projectCode, @PathVariable String applicationCode){
        
        return ResponseEntity.ok(buildService.build(buildinfo, projectCode, applicationCode));
    }

    @PostMapping("/b2i/{projectCode}/{applicationCode}")
    public ResponseEntity<Object> createImage(){

        return ResponseEntity.ok(null);
    }
    
}

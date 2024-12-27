package com.geeklib.ether.devops.controller;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geeklib.ether.devops.entity.BuildInfoS2i;
import com.geeklib.ether.devops.services.BuildService;
import com.geeklib.ether.devops.services.DockerService;


@RestController
@RequestMapping("/api/devops/build")
public class BuildController {
    
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource
    DockerService dockerService;

    @Resource
    BuildService buildService;

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

package com.geeklib.ether.devops.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geeklib.ether.devops.entity.Artifact;
import com.geeklib.ether.devops.services.ArtifactService;

@RestController
@RequestMapping("/devops/artifact")
public class ArtifactController {
    
    @Resource
    ArtifactService artifactService;

    @PostMapping("")
    public Artifact createArtifact(Artifact artifact) {
        
        return artifactService.createArtifact(artifact);
}
}

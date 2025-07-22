package com.geeklib.ether.devops.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geeklib.ether.devops.entity.Artifact;
import com.geeklib.ether.devops.services.ArtifactService;

@RestController
@RequestMapping("/devops/artifact")
public class ArtifactController {

    @Resource
    ArtifactService artifactService;


    @GetMapping("/{name}")
    public Artifact getArtifact(@PathVariable String name) {
        return artifactService.getArtifact(name);
    }

    @GetMapping("/list")
    public List<Artifact> listArtifact() {
        return artifactService.listArtifact();
    }
    
    @PostMapping("")

    public Artifact createArtifact(    @Validated @RequestBody Artifact artifact) {

        return artifactService.createArtifact(artifact);
    }

    @DeleteMapping("/{name}")
    public void deleteArtifact(@PathVariable String name) {
        artifactService.deleteArtifact(name);
    }

    @PutMapping("/{name}")
    public void updateArtifact(@PathVariable String name, Artifact artifact) {
        artifactService.updateArtifact(name, artifact);
    }

    @PatchMapping("/{name}")
    public void patchArtifact(@PathVariable String name, Artifact artifact) {
        artifactService.patchArtifact(name, artifact);
    }
    
}

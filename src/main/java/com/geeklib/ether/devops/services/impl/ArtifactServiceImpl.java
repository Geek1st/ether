package com.geeklib.ether.devops.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.geeklib.ether.common.HazelcastHelper;
import com.geeklib.ether.devops.entity.Artifact;
import com.geeklib.ether.devops.repository.ArtifactRepository;
import com.geeklib.ether.devops.services.ArtifactService;

@Service
public class ArtifactServiceImpl implements ArtifactService {


    @Resource
    ArtifactRepository artifactRepository;
    
    @Override
    public Artifact createArtifact(Artifact artifact) {
        artifactRepository.save(artifact);
        return artifact;
    }

    @Override
    public Artifact getArtifact(String name) {
        return artifactRepository.findById(name).orElse(null);
    }

    @Override
    public List<Artifact> listArtifact() {
        return HazelcastHelper.list(Artifact.class);
    }

    @Override
    public void deleteArtifact(String name) {
        HazelcastHelper.delete(name, Artifact.class);
    }

    @Override
    public void updateArtifact(String name, Artifact artifact) {
        HazelcastHelper.update(name, artifact);
    }

    @Override
    public void patchArtifact(String name, Artifact artifact) {
        HazelcastHelper.patch(name, artifact);
    }

}

package com.geeklib.ether.ci.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.geeklib.ether.ci.entity.Artifact;
import com.geeklib.ether.ci.repository.ArtifactRepository;
import com.geeklib.ether.ci.services.ArtifactService;
import com.geeklib.ether.common.HazelcastPersistenceHelper;

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
        return HazelcastPersistenceHelper.list(Artifact.class);
    }

    @Override
    public void deleteArtifact(String name) {
        HazelcastPersistenceHelper.delete(name, Artifact.class);
    }

    @Override
    public void updateArtifact(String name, Artifact artifact) {
        HazelcastPersistenceHelper.update(name, artifact);
    }

    @Override
    public void patchArtifact(String name, Artifact artifact) {
        HazelcastPersistenceHelper.patch(name, artifact);
    }

}

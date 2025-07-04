package com.geeklib.ether.devops.services.impl;

import org.springframework.stereotype.Service;

import com.geeklib.ether.common.HazelcastHelper;
import com.geeklib.ether.devops.entity.Artifact;
import com.geeklib.ether.devops.services.ArtifactService;

@Service
public class ArtifactServiceImpl implements ArtifactService {

    @Override
    public Artifact createArtifact(Artifact artifact) {
        Artifact result = HazelcastHelper.create(artifact);
        return result;
    }

}

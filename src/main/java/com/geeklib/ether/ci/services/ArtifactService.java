package com.geeklib.ether.ci.services;

import java.util.List;

import com.geeklib.ether.ci.entity.Artifact;


public interface ArtifactService {

    public Artifact getArtifact(String name);
    public List<Artifact> listArtifact();
    public Artifact createArtifact(Artifact artifact);
    public void deleteArtifact(String name);
    public void updateArtifact(String name, Artifact artifact);
    public void patchArtifact(String name, Artifact artifact);
}

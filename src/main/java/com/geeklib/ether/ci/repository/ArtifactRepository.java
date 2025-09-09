package com.geeklib.ether.ci.repository;

import org.springframework.data.hazelcast.repository.HazelcastRepository;
import org.springframework.stereotype.Repository;

import com.geeklib.ether.ci.entity.Artifact;

@Repository
public interface ArtifactRepository extends HazelcastRepository<Artifact, String> {

    default void create(Artifact artifact) {
        findById(artifact.getName()).ifPresent(a -> {
            throw new RuntimeException("Artifact already exists");
        });
        save(artifact);
    }
}

package com.geeklib.ether.ci.repository;

import org.springframework.data.hazelcast.repository.HazelcastRepository;

import com.geeklib.ether.project.entity.Project;

public interface ProjectRepository extends HazelcastRepository<Project, String> {
    
}

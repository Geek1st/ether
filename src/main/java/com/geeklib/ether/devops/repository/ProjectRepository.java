package com.geeklib.ether.devops.repository;

import org.springframework.data.hazelcast.repository.HazelcastRepository;

import com.geeklib.ether.devops.entity.Project;

public interface ProjectRepository extends HazelcastRepository<Project, String> {
    
}

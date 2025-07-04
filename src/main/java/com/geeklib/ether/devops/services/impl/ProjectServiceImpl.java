package com.geeklib.ether.devops.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.geeklib.ether.common.HazelcastHelper;
import com.geeklib.ether.devops.entity.Project;
import com.geeklib.ether.devops.services.ProjectService;
import com.geeklib.ether.devops.services.RegistryService;
import com.hazelcast.core.HazelcastInstance;

@Service
public class ProjectServiceImpl implements ProjectService {


    @Resource
    RegistryService registryservice;

    @Resource
    HazelcastInstance hazelcastInstance;

    
    public Project getProject(String name){
        return HazelcastHelper.get(name, Project.class);
    }

    @Override
    public List<Project> listProject() {

        return HazelcastHelper.list(Project.class);
    }

    @Override
    public Project createProject(Project project) {
        // IMap<String, Project> iMap = hazelcastInstance.getMap(project.getClass().getSimpleName());
        // return iMap.putIfAbsent(project.getName(), project);
        return HazelcastHelper.create(project);
    }

    @Override
    public void removeProject(String name) {
        HazelcastHelper.delete(name, Project.class);
    }

    public void updateProject(Project project) {
        HazelcastHelper.update(project.getName(), project);
    }
    
}

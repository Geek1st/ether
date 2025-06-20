package com.geeklib.ether.devops.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.geeklib.ether.devops.entity.Project;
import com.geeklib.ether.devops.services.ProjectService;
import com.geeklib.ether.devops.services.RegistryService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

@Service
public class ProjectServiceImpl implements ProjectService {


    @Resource
    RegistryService registryservice;

    @Resource
    HazelcastInstance hazelcastInstance;

    private IMap<Long, Project> iMap;


    @PostConstruct
    public void init() {
        iMap = hazelcastInstance.getMap(Project.class.getSimpleName());
    }

    
    public Project getProject(long id){
        return iMap.get(id);
    }

    @Override
    public List<Project> listProject() {
        
        Collection<Project> collection = iMap.values();
        List<Project> projects = new ArrayList<Project>(collection);
        return projects;
    }

    @Override
    public void createProject(Project project) {

        iMap.put(project.getId(), project);
    }

    @Override
    public void removeProject(long id) {
        iMap.remove(id);
    }

    public void updateProject(Project project) {
        iMap.put(project.getId(), project);
    }
    
}

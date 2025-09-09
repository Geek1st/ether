package com.geeklib.ether.project.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.geeklib.ether.ci.repository.ProjectRepository;
import com.geeklib.ether.ci.services.RegistryService;
import com.geeklib.ether.common.HazelcastPersistenceHelper;
import com.geeklib.ether.common.QueryParams;
import com.geeklib.ether.project.entity.Project;
import com.geeklib.ether.project.service.ProjectService;

@Service
public class ProjectServiceImpl implements ProjectService {


    @Resource
    RegistryService registryservice;

    @Resource
    ProjectRepository projectRepository;

    @Override
    public Project getProject(String name) {
        return HazelcastPersistenceHelper.get(name, Project.class);
    }

    @Override
    public List<Project> listProject() {
        return HazelcastPersistenceHelper.list(Project.class);
    }

    @Override
    public List<Project> listProject(QueryParams queryParams, Pageable pageable) {
        return HazelcastPersistenceHelper.list(Project.class, queryParams, pageable);
    }

    @Override
    public Project createProject(Project project) {
        return HazelcastPersistenceHelper.create(project.getName(), project); 
    }

    @Override
    public void removeProject(String name) {
        HazelcastPersistenceHelper.delete(name, Project.class);
    }

    public void updateProject(Project project) {
        HazelcastPersistenceHelper.update(project.getName(), project);
    }

    public void patchProject(Project project) {
        HazelcastPersistenceHelper.patch(project.getName(), project);
    }
    
}

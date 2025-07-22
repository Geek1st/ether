package com.geeklib.ether.devops.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.geeklib.ether.common.HazelcastHelper;
import com.geeklib.ether.common.QueryParams;
import com.geeklib.ether.devops.entity.Project;
import com.geeklib.ether.devops.repository.ProjectRepository;
import com.geeklib.ether.devops.services.ProjectService;
import com.geeklib.ether.devops.services.RegistryService;

@Service
public class ProjectServiceImpl implements ProjectService {


    @Resource
    RegistryService registryservice;

    @Resource
    ProjectRepository projectRepository;

    @Override
    public Project getProject(String name) {
        return HazelcastHelper.get(name, Project.class);
    }

    @Override
    public List<Project> listProject() {
        return HazelcastHelper.list(Project.class);
    }

    @Override
    public List<Project> listProject(QueryParams queryParams, Pageable pageable) {
        return HazelcastHelper.list(Project.class, queryParams, pageable);
    }

    @Override
    public Project createProject(Project project) {
        return HazelcastHelper.create(project.getName(), project); 
    }

    @Override
    public void removeProject(String name) {
        HazelcastHelper.delete(name, Project.class);
    }

    public void updateProject(Project project) {
        HazelcastHelper.update(project.getName(), project);
    }

    public void patchProject(Project project) {
        HazelcastHelper.patch(project.getName(), project);
    }
    
}

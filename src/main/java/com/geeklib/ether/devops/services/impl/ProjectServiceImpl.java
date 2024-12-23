package com.geeklib.ether.devops.services.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

// import com.geeklib.ether.devops.dao.ProjectDao;
import com.geeklib.ether.devops.entity.Project;
import com.geeklib.ether.devops.mapper.ProjectMapper;
import com.geeklib.ether.devops.services.ProjectService;
import com.geeklib.ether.devops.services.RegistryService;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Resource
    ProjectMapper projectMapper;

    @Resource
    RegistryService registryservice;

    
    public Project getProject(long id){
        return projectMapper.getById(id);
    }

    @Override
    public List<Project> listProject() {
        
        return projectMapper.listObject();
        //return null;
    }

    @Override
    public void createProject(Project project) {

        //projectMapper.createObject(project);
    }

    @Override
    public void removeProject(long id) {
        //projectMapper.removeObject(id);
    }

    public void updateProject(Project project) {
        //projectMapper.updateObject(project);
    }
    
}

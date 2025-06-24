package com.geeklib.ether.devops.services;

import java.util.List;

import com.geeklib.ether.devops.entity.Project;

public interface ProjectService extends BaseService{

    List<Project> listProject();

    Project createProject(Project project);

    Project getProject(long id);

    void removeProject(long id);

    void updateProject(Project project);

}
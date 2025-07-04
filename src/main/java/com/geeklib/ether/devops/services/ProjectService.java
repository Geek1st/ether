package com.geeklib.ether.devops.services;

import java.util.List;

import com.geeklib.ether.devops.entity.Project;

public interface ProjectService extends BaseService{

    List<Project> listProject();

    Project createProject(Project project);

    Project getProject(String name);

    void removeProject(String name);

    void updateProject(Project project);

}
package com.geeklib.ether.devops.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.geeklib.ether.devops.entity.Project;
import com.geeklib.ether.common.QueryParams;

public interface ProjectService extends BaseService{

    List<Project> listProject();
    List<Project> listProject(QueryParams queryParams, Pageable pageable);

    Project createProject(Project project);

    Project getProject(String name);

    void removeProject(String name);

    void updateProject(Project project);

    void patchProject(Project project);

}
package com.geeklib.ether.project.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.geeklib.ether.common.QueryParams;
import com.geeklib.ether.project.entity.Project;

public interface ProjectService {

    List<Project> listProject();
    List<Project> listProject(QueryParams queryParams, Pageable pageable);

    Project createProject(Project project);

    Project getProject(String name);

    void removeProject(String name);

    void updateProject(Project project);

    void patchProject(Project project);

}
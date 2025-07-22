package com.geeklib.ether.devops.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.geeklib.ether.common.QueryParams;
import com.geeklib.ether.devops.entity.Project;
import com.geeklib.ether.devops.services.ProjectService;
import com.geeklib.ether.devops.services.RegistryService;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Resource
    ProjectService projectService;

    @Resource
    RegistryService registryService;

    @GetMapping("/{name}")
    public Project getProject(@PathVariable String name){
        return projectService.getProject(name);
    }

    @GetMapping("")
    public List<Project> listProject(QueryParams querParams, Pageable pageable){

        return projectService.listProject(querParams, pageable);
    }

    @PostMapping("")
    public Project createProject(@RequestBody @Validated(Project.CreateGroup.class) Project project){

        return projectService.createProject(project);
    }

    @DeleteMapping("/{name}")
    public void deleteProject(@PathVariable String name){

        projectService.removeProject(name);
    }

    @PutMapping("/{name}")
    public void updateProject(@PathVariable String name, Project project){
        projectService.updateProject(project);
    }

    @PatchMapping("/{name}")
    public void patchProject(@PathVariable String name, Project project){
        projectService.patchProject(project);
    }
}

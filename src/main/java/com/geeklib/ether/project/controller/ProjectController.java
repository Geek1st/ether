package com.geeklib.ether.project.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geeklib.ether.ci.services.RegistryService;
import com.geeklib.ether.common.QueryParams;
import com.geeklib.ether.project.entity.Project;
import com.geeklib.ether.project.service.ProjectService;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Resource
    ProjectService projectService;

    @Resource
    RegistryService registryService;

    @RequiresPermissions("project:get")
    @GetMapping("/{name}")
    public Project getProject(@PathVariable String name){
        return projectService.getProject(name);
    }

    @RequiresPermissions("project:list")
    @GetMapping("")
    public List<Project> listProject(QueryParams querParams, Pageable pageable){

        return projectService.listProject(querParams, pageable);
    }

    @RequiresPermissions("project:create")
    @PostMapping("")
    public Project createProject(@RequestBody @Validated(Project.CreateGroup.class) Project project){

        return projectService.createProject(project);
    }

    @RequiresPermissions("project:delete")
    @DeleteMapping("/{name}")
    public void deleteProject(@PathVariable String name){

        projectService.removeProject(name);
    }

    @RequiresPermissions("project:update")
    @PutMapping("/{name}")
    public void updateProject(@PathVariable String name, Project project){
        projectService.updateProject(project);
    }

    @RequiresPermissions("project:update")
    @PatchMapping("/{name}")
    public void patchProject(@PathVariable String name, Project project){
        projectService.patchProject(project);
    }
}

package com.geeklib.ether.devops.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geeklib.ether.devops.entity.Project;
import com.geeklib.ether.devops.services.ProjectService;
import com.geeklib.ether.devops.services.RegistryService;

/**
 * 该控制器与gitlab、harbor、kubernetes交互
 */
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Resource
    ProjectService projectService;

    @Resource
    RegistryService registryService;
    
    @GetMapping("")
    public List<Project> listProject(){

        return projectService.listProject();
    }

    @PostMapping("")
    public Project createProject(@RequestBody @Validated(Project.CreateGroup.class) Project project){

        return projectService.createProject(project);
    }


    @GetMapping("/{name}")
    public Project getProject(@PathVariable String name){
        return projectService.getProject(name);
    }

    @DeleteMapping("/{name}")
    public void deleteProject(@PathVariable String name){

        projectService.removeProject(name);
    }

    @PutMapping("/{name}")
    public void updateProject(@PathVariable String name, Project project){
        projectService.updateProject(project);
    } 
}

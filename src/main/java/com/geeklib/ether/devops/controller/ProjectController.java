package com.geeklib.ether.devops.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geeklib.ether.devops.entity.Project;
import com.geeklib.ether.devops.services.ProjectService;
import com.geeklib.ether.devops.services.RegistryService;

/**
 * 该控制器与gitlab、harbor、kubernetes交互
 */
@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Resource
    ProjectService projectService;

    @Resource
    RegistryService registryService;
    
    @GetMapping("")
    public List<Project> listProject(){

        return projectService.listProject();
    }

    @PostMapping("/")
    public ResponseEntity createProject(Project project){

        projectService.createProject(project);
        return ResponseEntity.ok("");
    }
}

package com.geeklib.ether.devops.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Project> createProject(@RequestBody Project project){

        ;
        return ResponseEntity.ok().body(projectService.createProject(project));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable long id){
        return ResponseEntity.ok(projectService.getProject(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable long id){
        projectService.removeProject(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProject(@PathVariable long id, Project project){
        projectService.updateProject(project);

        return ResponseEntity.ok("");
    } 
}

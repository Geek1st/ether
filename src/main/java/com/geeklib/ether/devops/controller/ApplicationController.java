package com.geeklib.ether.devops.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geeklib.ether.devops.entity.Application;
import com.geeklib.ether.devops.services.ApplicationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/application")
public class ApplicationController {
    
    @Resource
    ApplicationService applicationService;

    Application application;

    @GetMapping("")
    public ResponseEntity<List<Application>> listApplications() {
        return ResponseEntity.ok(applicationService.listApplications());
    }

    @GetMapping("{id}")
    public ResponseEntity<Application> getApplication(@PathVariable long id) {
        return ResponseEntity.ok(applicationService.getApplication(id));
    }

    @PostMapping("")
    public ResponseEntity<Application> createApplication(@RequestBody Application application) {
        return ResponseEntity.ok(applicationService.createApplication(application));
    }
    
}

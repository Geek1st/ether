package com.geeklib.ether.devops.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.geeklib.ether.devops.entity.Application;
import com.geeklib.ether.devops.services.ApplicationService;



@RestController
@RequestMapping("/application")
public class ApplicationController {
    
    @Resource
    ApplicationService applicationService;

    Application application;

    @GetMapping("{name}")
    public Application getApplication(@PathVariable String name) {
        return applicationService.getApplication(name);
    }

    @GetMapping("")
    public List<Application> listApplications() {
        return applicationService.listApplications();
    }

    @PostMapping("")
    public Application createApplication(@RequestBody Application application) {
        return applicationService.createApplication(application);
    }

    @DeleteMapping("{name}")
    public void deleteApplication(@PathVariable String name) {
        applicationService.deleteApplication(name);
    }

    @PutMapping("{name}")
    public void updateApplication(@PathVariable String name, @RequestBody Application application) {
        applicationService.updateApplication(name, application);
    }
    
}

package com.geeklib.ether.controller;

import java.io.IOException;
import java.util.Collection;

import com.geeklib.ether.entity.Application;
import com.geeklib.ether.service.ApplicationService;
import com.offbytwo.jenkins.model.Job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @GetMapping("/{name}")
    public Application getApplication(String name) {
        Application application = new Application();
        application.setName("123");
        application.setBranch("master");
        return application;
    }

    @GetMapping()
    public Collection<Job> listApplication() {
        return applicationService.listApplication(null);
    }

    @PostMapping("")
    public ResponseEntity<Boolean> createApplication(Application application) throws IOException {

        
        return ResponseEntity.ok(applicationService.createApplication(application));
    }
}
package com.geeklib.ether.devops.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("")
    public ResponseEntity<List<Application>> listApplications() {
        return ResponseEntity.ok(applicationService.listApplications());
    }
}

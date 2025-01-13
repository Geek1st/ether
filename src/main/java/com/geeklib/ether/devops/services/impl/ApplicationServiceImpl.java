package com.geeklib.ether.devops.services.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.geeklib.ether.devops.entity.Application;
import com.geeklib.ether.devops.mapper.ApplicationMapper;
import com.geeklib.ether.devops.services.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    
    @Resource
    ApplicationMapper applicationMapper;

    @Override
    public List<Application> listApplications() {
        return applicationMapper.listObject();
    }
    
}

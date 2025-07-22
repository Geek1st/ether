package com.geeklib.ether.devops.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.geeklib.ether.devops.entity.Application;
import com.geeklib.ether.devops.services.ApplicationService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Resource
    HazelcastInstance hazelcastInstance;


    @Override
    public List<Application> listApplications() {
        IMap<Long, Application> iMap = hazelcastInstance.getMap(Application.class.getSimpleName());
        Collection<Application> collection = iMap.values();
        List<Application> applications = new ArrayList<Application>(collection);
        return applications;
    }

    @Override
    public Application getApplication(String name) {
        IMap<String, Application> imap = hazelcastInstance.getMap(Application.class.getSimpleName());
        Application application = imap.get(name);
        return application;
    }

    @Override
    public Application createApplication(Application application) {
        hazelcastInstance.getMap(Application.class.getSimpleName())
                .put(application.getName(), application);
        return application;
    }

    @Override
    public void updateApplication(String name, Application application) {
        hazelcastInstance.getMap(Application.class.getSimpleName())
                .put(application.getName(), application);
    }

    @Override
    public void patchApplication(String name, Application application) {
        hazelcastInstance.getMap(Application.class.getSimpleName())
                .put(application.getName(), application);
    }

    @Override
    public void deleteApplication(String name) {
        hazelcastInstance.getMap(Application.class.getSimpleName())
                .remove(name);
    }
}

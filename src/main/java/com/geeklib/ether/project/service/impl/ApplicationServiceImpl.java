package com.geeklib.ether.project.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.geeklib.ether.common.HazelcastPersistenceHelper;
import com.geeklib.ether.common.QueryParams;
import com.geeklib.ether.project.entity.Application;
import com.geeklib.ether.project.service.ApplicationService;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Resource
    HazelcastInstance hazelcastInstance;


    @Override
    public List<Application> listApplications(QueryParams queryParams, Pageable pageable) {
        return HazelcastPersistenceHelper.list(Application.class, queryParams, pageable);
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

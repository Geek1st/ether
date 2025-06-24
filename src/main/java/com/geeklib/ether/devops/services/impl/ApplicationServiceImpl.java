package com.geeklib.ether.devops.services.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
    public Application getApplication(long id) {
        IMap<Long, Application> imap = hazelcastInstance.getMap(Application.class.getSimpleName());
        Application application = imap.get(id);
        return application;
    }

    @Override
    public Application createApplication(Application application) {
        long id = hazelcastInstance.getFlakeIdGenerator(Application.class.getSimpleName()).newId();
        application.setId(id);
        hazelcastInstance.getMap(Application.class.getSimpleName())
                .put(id, application);
        return application;
    }

    @Override
    public boolean updateApplication(long id, Application application) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateApplication'");
    }

    @Override
    public boolean patchApplication(long id, Application application) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'patchApplication'");
    }

    @Override
    public boolean deleteApplication(long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteApplication'");
    }

    @Override
    public boolean deleteApplication(Application application) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteApplication'");
    }

    @Override
    public boolean deleteApplications(List<Long> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteApplications'");
    }

    
    
}

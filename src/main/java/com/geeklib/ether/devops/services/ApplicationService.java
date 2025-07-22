package com.geeklib.ether.devops.services;

import java.util.List;

import com.geeklib.ether.devops.entity.Application;

public interface ApplicationService {

    Application getApplication(String name);
    
    List<Application> listApplications();

    Application createApplication(Application application);

    void updateApplication(String name, Application application);

    void patchApplication(String name, Application application);

    void deleteApplication(String name);
}

package com.geeklib.ether.devops.services;

import java.util.List;

import com.geeklib.ether.devops.entity.Application;

public interface ApplicationService {
    
    List<Application> listApplications();

    Application getApplication(long id);

    Application createApplication(Application application);
}

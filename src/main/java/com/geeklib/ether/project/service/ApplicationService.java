package com.geeklib.ether.project.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.geeklib.ether.common.QueryParams;
import com.geeklib.ether.project.entity.Application;

public interface ApplicationService {

    Application getApplication(String name);
    
    List<Application> listApplications(QueryParams queryParams, Pageable pageable);

    Application createApplication(Application application);

    void updateApplication(String name, Application application);

    void patchApplication(String name, Application application);

    void deleteApplication(String name);
}

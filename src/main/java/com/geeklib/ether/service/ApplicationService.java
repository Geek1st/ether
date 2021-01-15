package com.geeklib.ether.service;

import java.io.IOException;
import java.util.Collection;

import com.geeklib.ether.entity.Application;
import com.offbytwo.jenkins.model.Job;

public interface ApplicationService {
    
    public Job getApplication(String name);

    public Collection<Job> listApplication(String group);

    public Boolean createApplication(Application application) throws IOException;
}

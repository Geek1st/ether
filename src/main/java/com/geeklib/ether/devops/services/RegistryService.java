package com.geeklib.ether.devops.services;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.geeklib.ether.devops.entity.Project;

public interface RegistryService {
    

    public List<Project> listProject();

    public void createProject();

    public void deleteProject();

    public void viewProject();
}

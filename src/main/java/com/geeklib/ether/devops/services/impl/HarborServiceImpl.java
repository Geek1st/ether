package com.geeklib.ether.devops.services.impl;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import com.geeklib.ether.config.RegistryConfig;
import com.geeklib.ether.devops.entity.Project;
import com.geeklib.ether.devops.services.RegistryService;

@Service
public class HarborServiceImpl implements RegistryService{
    
    HttpClient client = HttpClientBuilder.create().build();

    @Resource
    RegistryConfig registryConfig;

    @Override
    public List<Project> listProject(){
        
        HttpGet get = new HttpGet(registryConfig.getApi() + "/projects");
        try {
            HttpResponse httpResponse = client.execute(get);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void createProject(){

        HttpPost post = new HttpPost(registryConfig.getApi() + "/project");
        try {
            HttpResponse httpResponse = client.execute(post);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    @Override
    public void deleteProject() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void viewProject() {
        // TODO Auto-generated method stub
        
    }
}

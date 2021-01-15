package com.geeklib.ether.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.geeklib.ether.config.JenkinsConfig;
import com.geeklib.ether.entity.Application;
import com.geeklib.ether.service.ApplicationService;
import com.geeklib.ether.util.TemplateEngineUtil;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private JenkinsServer jenkinsServer = null;

    @PostConstruct
    public void initConfig() {
        try {
            jenkinsServer = new JenkinsServer(new URI(jenkinsConfig.getUrl()), jenkinsConfig.getUsername(),
                    jenkinsConfig.getPassword());
        } catch (URISyntaxException e) {
            logger.error("jenkins:url 错误", e);
        }
    }

    @Autowired
    JenkinsConfig jenkinsConfig;

    @Override
    public Job getApplication(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<Job> listApplication(String group) {
        Map<String, Job> jobs = null;

        try {
            jobs = jenkinsServer.getJobs();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error("", e);
        }

        return jobs.values();

    }

    @Override
    public Boolean createApplication(Application application) throws IOException {
        
        String xml = TemplateEngineUtil.bind(new ClassPathResource("templates/jenkins/workflow-job.xml"), application);
        jenkinsServer.createJob(application.getName(), xml, true);
        return true;
    }

}

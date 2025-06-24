package com.geeklib.ether.devops.services.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.geeklib.ether.config.RegistryConfig;
import com.geeklib.ether.devops.services.RegistryService;
// import com.geeklib.ether.exception.HttpServiceException;
// import com.geeklib.registry.api.ArtifactApi;
// import com.geeklib.registry.api.ProjectApi;
// import com.geeklib.registry.entity.Project;
// import com.geeklib.registry.entity.ProjectReq;
// import com.geeklib.registry.invoker.ApiClient;

@Service
public class HarborServiceImpl implements RegistryService{

    // Logger logger = LoggerFactory.getLogger(HarborServiceImpl.class);

    // @Resource
    // RegistryConfig registryConfig;

    // ApiClient apiClient = new ApiClient();
    // ProjectApi projectApi = new ProjectApi(apiClient);
    // ArtifactApi artifactApi = new ArtifactApi(apiClient);

    // String xRequestId = null;

    // @PostConstruct
    // public void init() {
    //     apiClient.setBasePath(registryConfig.getApi());
    //     apiClient.setUsername(registryConfig.getUsername());
    //     apiClient.setPassword(registryConfig.getPassword());
    // }

    // public List<Project> listProject(String query, Long page, Long pageSize, String sort, String name, Boolean _public, String own, Boolean withDetail) {
        
    //     List<Project> projects = null;
        
    //     try {
    //         projects = projectApi.listProjects(xRequestId, query, page, pageSize, sort, name, _public, own, withDetail).collectList().block();
    //     } catch (WebClientResponseException e) {

    //         throw new HttpServiceException(e.getRawStatusCode(), e.getMessage(), e.getResponseBodyAsString());
    //     }
    //     return projects;
    // }

    // public void createProject(ProjectReq projectReq) {
    //     projectApi.createProject(projectReq, xRequestId, false);
    // }

    // public void deleteProject(String projectNameOrId) {
        
    //     projectApi.deleteProject(projectNameOrId, xRequestId, null).block();
    // }

    // public void viewProject() {
    //     // TODO Auto-generated method stub
        
    // }

    // public void deleteArtifact(String projectName, String repositoryName, String reference) {
    //     artifactApi.deleteArtifact(projectName, repositoryName, reference, xRequestId).block();
    // }

    // public void listArtifact(String projectName, String repositoryName, String q, Long page, Long pageSize, String sort) {
    //     artifactApi.listArtifacts(projectName, repositoryName, xRequestId, q, sort, page, pageSize, sort, null, null, null, null, null, null, null);
        
    // }

    // public void getArtifact(String projectName, String repositoryName, String reference) {
    //     artifactApi.getArtifact(projectName, repositoryName, reference, repositoryName, null, null, reference, null, null, null, null, null, null, null);
        
    // }
}

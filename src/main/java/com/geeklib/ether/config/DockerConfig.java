package com.geeklib.ether.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

@Configuration
public class DockerConfig {

    @Autowired
    DockerProperties dockerProperties;
    @Bean
    public DockerClientConfig dockerClientConfig() {
        DockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://192.168.241.128:2375")
                .withDockerTlsVerify(false)
                .withDockerCertPath("")
                .withRegistryUrl("http://hub.centit.com")
                .withRegistryUsername("operator")
                .withRegistryPassword("0pC3ntit.1")
                .build();

        return dockerClientConfig;
    }

    @Bean
    public DockerHttpClient dockerHttpClient(){
        
        DockerHttpClient dockerHttpClient = new ApacheDockerHttpClient.Builder()
        .dockerHost(dockerClientConfig().getDockerHost())
        .maxConnections(200)
        .connectionTimeout(Duration.ofSeconds(30))
        .responseTimeout(Duration.ofSeconds(45))
        .build();

        return dockerHttpClient;
    }
}

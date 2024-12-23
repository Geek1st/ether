package com.geeklib.ether.devops.services;

import java.nio.file.Path;

import com.github.dockerjava.api.command.BuildImageResultCallback;

public interface DockerService {
    
    // public BuildImageResultCallback build(Path dockerfile, Build build);

    public void push(String name);

    public void listImages();

    public void info();
}

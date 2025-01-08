package com.geeklib.ether.devops.services;

public interface DockerService {
    
    // public BuildImageResultCallback build(Path dockerfile, Build build);

    public void push(String name);

    public void listImages();

    public void info();
}

package com.geeklib.ether.devops.entity;

import lombok.Data;

@Data
public class Registry {
    
    private String name;
    private Project project;
    private String username;
    private String password;

    @Data
    class Project {
        private String name;
        private String image;
        private String tag;
    }
}

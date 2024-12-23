package com.geeklib.ether.devops.entity;


import org.apache.commons.lang3.StringUtils;

import lombok.Data;
@Data
public class BuildInfo {

    Project project;

    String environment;
    String filename;
    String temporaryDir;
    String dockerfile;
    String tag;

    public String getTemporaryDir() {
        return StringUtils.substringBeforeLast(filename, "\\");
    }

}

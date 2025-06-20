package com.geeklib.ether.devops.entity;

import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildInfoS2i extends BuildInfo{
    File dockerfile;
    String dockerfileContent;
    InputStream tarStream;
    Set<String> tags;
    Map<String, String> labels;
    boolean noCache;
    Map<String, String> args;
    String platform;
}

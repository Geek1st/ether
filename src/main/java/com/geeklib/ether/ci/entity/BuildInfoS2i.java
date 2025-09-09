package com.geeklib.ether.ci.entity;

import java.util.Map;
import java.util.Set;

import com.geeklib.ether.common.annotation.Entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class BuildInfoS2i extends BuildInfo{
    String dockerfile;
    Set<String> tags;
    Map<String, String> labels;
    boolean noCache;
    Map<String, String> args;
    String platform;
}

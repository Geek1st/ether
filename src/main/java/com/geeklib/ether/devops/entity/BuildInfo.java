package com.geeklib.ether.devops.entity;


import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BuildInfo{

    Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date gmtCreate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date gmtModified;
    Long buildNumber;
    Long projectId;
    String projectName;
    String applicationName;
    Long applicationId;
    Long nextBuildNumber;
    Long lastBuildNumber;
    String status; // 当前状态，例如：PENDING, RUNNING, SUCCESS, FAILURE
    String stage; // 当前构建阶段，例如：编译、测试、打包、部署
    String errorMessage; // 如果失败，记录错误信息
    Date startTime; // 状态开始时间
    Date endTime; // 状态结束时间
    Long duration; // 状态持续时间（毫秒）
    String triggeredBy; // 状态触发者，例如：用户、系统、Webhook
    String environment; // 构建环境，例如：开发、测试、生产
    String logsPath; // 状态相关日志路径
    Boolean isFinalState; // 是否为最终状态（如成功或失败）
    String details; // 状态的详细描述
    String node; // 执行状态的节点信息（如构建服务器）
}

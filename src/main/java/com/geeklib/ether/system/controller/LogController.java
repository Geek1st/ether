package com.geeklib.ether.system.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO 实现日志热加载，根据环境区分日志等级，按模块区分日志
@RestController("/system/log")
public class LogController {
    @GetMapping("")
	public ResponseEntity<Object> getLoggers(){

        return null;
    }

}
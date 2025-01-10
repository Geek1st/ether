package com.geeklib.ether.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2Config {

    // @Bean(initMethod = "start", destroyMethod = "stop")
    // public Server h2Server() throws Exception {
    //     return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    // }
}
package com.geeklib.ether.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.geeklib.ether.devops.mapper.BaseMapper;

// @Configurable
// @EnableJpaRepositories( basePackages = "com.geekib.ether.devops.mapper",
//     excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = BaseMapper.class))
public class JpaConfig {
    
}

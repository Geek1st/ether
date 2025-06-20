package com.geeklib.ether.config;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import javax.annotation.Resource;

import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.geeklib.ether.common.JsonMapStore;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class HazelcastConfiguration {

    @Resource
    WorkspaceProperties workspaceProperties;

    @Bean
    public HazelcastInstance hazelcastInstance() {
        return Hazelcast.newHazelcastInstance(hazelcastConfig());
    }

    @Bean
    public Config hazelcastConfig() {
        Config config = new Config();

        config.setInstanceName("ether-hazelcast-instance");

        config.getNetworkConfig().setPort(5701).setPortAutoIncrement(true);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(false);

        // 启用控制台
        config.setProperty("hazelcast.management.center.enabled", "true");
        config.setProperty("hazelcast.management.center.url", "http://localhost:8080/hazelcast-mancenter");

        // 持久化设置
        // config.getPersistenceConfig().setEnabled(true)
        //         .setBaseDir(Paths.get(workspaceProperties.getPath(), workspaceProperties.getDATA_PATH()).toFile());
        config.getPersistenceConfig().setEnabled(false);
        

        listMapEntity().forEach(entityClass -> {
            MapConfig mapConfig = new MapConfig(entityClass.getSimpleName());
            MapStoreConfig mapStoreConfig = new MapStoreConfig();
            mapStoreConfig.setEnabled(true)
                    .setImplementation(new JsonMapStore<>(entityClass, workspaceProperties))
                    .setWriteDelaySeconds(0);
            mapConfig.setMapStoreConfig(mapStoreConfig);
            config.addMapConfig(mapConfig);
        });
        System.out.println(config);
        return config;
    }

    /**
     * 列出所有标记为 Entity 的类
     * 
     * @return
     */
    private Set<Class<?>> listMapEntity() {

        Reflections reflections = new Reflections("com.geeklib.ether");
        Set<Class<?>> entities = reflections.getTypesAnnotatedWith(com.geeklib.ether.annotation.Entity.class);

        return entities;
    }

    
}

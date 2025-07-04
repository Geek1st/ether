package com.geeklib.ether.config;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.reflections.Reflections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geeklib.ether.annotation.HazelcastIndex;
import com.geeklib.ether.common.JsonMapStore;
import com.geeklib.ether.utils.StringUtils;
import com.hazelcast.config.Config;
import com.hazelcast.config.IndexConfig;
import com.hazelcast.config.IndexType;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MapStoreConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Configuration
public class HazelcastConfiguration {

    @Resource
    WorkspaceProperties workspaceProperties;

    @Resource
    ObjectMapper objectMapper;

    @Bean
    public HazelcastInstance hazelcastInstance() {
        return Hazelcast.newHazelcastInstance(hazelcastConfig());
    }
    
    public Config hazelcastConfig() {
        Config config = new Config();

        config.setInstanceName("ether-hazelcast-instance");

        //显示关闭多播
        config.getNetworkConfig().setPort(5701).setPortAutoIncrement(true);
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getAwsConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getAzureConfig().setEnabled(false);
        config.getNetworkConfig().getJoin().getGcpConfig().setEnabled(false);
        //TODO 如果不能自动识别到SERVICE_NAME,需要从环境变量中获取
        if(System.getenv("KUBERNETES_SERVICE_HOST") != null) {
            config.getNetworkConfig().getJoin().getKubernetesConfig().setEnabled(true);
        }else {
            config.getNetworkConfig().getJoin().getKubernetesConfig().setEnabled(false);
        }
        

        // 启用控制台
        config.setProperty("hazelcast.management.center.enabled", "true");
        config.setProperty("hazelcast.management.center.url", "http://localhost:8080/hazelcast-mancenter");
        //显示禁用组播
        config.setProperty("hazelcast.discovery.enabled", "false");
        
        // 持久化设置
        // config.getPersistenceConfig().setEnabled(true)
        //         .setBaseDir(Paths.get(workspaceProperties.getPath(), workspaceProperties.getDATA_PATH()).toFile());
        config.getPersistenceConfig().setEnabled(false);
        

        listMapEntity().forEach(entityClass -> {
            MapConfig mapConfig = new MapConfig(StringUtils.camelToUnderline(entityClass.getSimpleName()));
            MapStoreConfig mapStoreConfig = new MapStoreConfig();
            mapStoreConfig.setEnabled(true)
                    .setImplementation(new JsonMapStore(entityClass, workspaceProperties, objectMapper))
                    .setWriteDelaySeconds(0);
            mapConfig.setMapStoreConfig(mapStoreConfig);
            getIndexMap(entityClass).forEach((indexType, fieldName) -> {
                mapConfig.addIndexConfig(new IndexConfig(indexType, fieldName));
            });
            config.addMapConfig(mapConfig);
        });
        System.out.println(config);
        return config;
    }

    private Map<IndexType, String> getIndexMap(Class<?> entityClass) {
        Map<IndexType, String> indexMap = new HashMap<>();
        for (Field field : entityClass.getDeclaredFields()) {
            HazelcastIndex indexAnnotation = field.getAnnotation(HazelcastIndex.class);
            if (indexAnnotation != null) {
                indexMap.put(indexAnnotation.type(), field.getName());
            }
        }
        return indexMap;
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

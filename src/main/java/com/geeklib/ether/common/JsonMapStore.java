package com.geeklib.ether.common;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.geeklib.ether.config.WorkspaceProperties;
import com.geeklib.ether.utils.StringUtils;
import com.hazelcast.map.MapStore;

public class JsonMapStore<K, V> implements MapStore<K, V> {

    protected Logger logger = LoggerFactory.getLogger(JsonMapStore.class);

    ObjectMapper objectMapper;

    WorkspaceProperties workspaceProperties;

    private final Class<V> valueClass;

    private Class<K> keyClass;

    public JsonMapStore(Class<V> valueClass, WorkspaceProperties workspaceProperties, ObjectMapper objectMapper) {
       
        this.valueClass = valueClass;
        this.workspaceProperties = workspaceProperties;
        this.objectMapper = objectMapper;

        for (Field declaredField : valueClass.getDeclaredFields()) {
            if (declaredField.isAnnotationPresent(JsonKey.class)) {
                keyClass = (Class<K>) declaredField.getType();
            }
        }
    }

    /**
     * 获取存储文件
     * 
     * @return 存储文件
     */
    private File getStoreFile(K key) {
        Path storeDir = getStoreDir();
        String fileName = key + ".json";
        return storeDir.resolve(fileName).toFile();
    };

    /**
     * 获取存储目录
     * 
     * @return 存储目录
     */
    protected Path getStoreDir() {
        return Paths.get(workspaceProperties.getDataPath().toString(), getMapName());
    }

    /**
     * 获取存储的键类型
     * 
     * @return 键类型的字符串表示
     */
    protected Class<V> getValueClass() {
        return valueClass;
    };

    /**
     * 获取存储的键类型
     * 
     * @return 键类型的字符串表示
     */
    protected String getMapName() {
        return StringUtils.camelToUnderline(valueClass.getSimpleName());
    }

    @Override
    public V load(K key) {

        File file = this.getStoreFile(key);

        if(!file.exists()) {
            return null;
        }

        try {
            return objectMapper.readValue(file, getValueClass());
        } catch (IOException e) {
            logger.error("数据加载失败，文件: {}", file.getAbsolutePath(), e);
            return null;
        }
    }

    @Override
    public void store(K key, V value) {

        try {
            Files.createDirectories(getStoreDir());
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(getStoreFile(key), value);
            logger.info("数据持久化成功，文件: {}", getStoreFile(key).getAbsolutePath());
        } catch (IOException e) {
            logger.error("数据持久化失败", e);
        }
    }

    @Override
    public Map<K, V> loadAll(Collection<K> keys) {
        Map<K, V> maps = new HashMap<K, V>();
        for (K key : keys) {
            File file = getStoreFile(key);
            if (file.exists()) {
                V value = null;
                try {
                    value = objectMapper.readValue(file, getValueClass());
                } catch (IOException e) {
                    logger.error("读取持久化文件失败,{}", getStoreFile(key), e);
                }
                maps.put(key, value);
            }
        }
        return maps;
    }

    @Override
    public Iterable<K> loadAllKeys() {

        //TODO 循环性能需要优化
        List<K> keys = null;
        try {
            keys = Files.list(getStoreDir()).map(Path::getFileName)
                    .map(name -> name.toString().substring(0, name.toString().lastIndexOf(".")))
                    .map(name -> keyClass.cast(name) )
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return keys;
    }

    @Override
    public void storeAll(Map<K, V> map) {
        map.forEach((key, value) -> {
            store(key, value);
        });
    }

    @Override
    public void delete(K key) {
        File file = getStoreFile(key);
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            logger.error("持久化文件删除失败", e);
        }
    }

    @Override
    public void deleteAll(Collection<K> keys) {
        keys.forEach(key -> {
            try {
                Files.deleteIfExists(getStoreFile(key).toPath());
            } catch (IOException e) {
                logger.error("持久化文件删除失败", getStoreFile(key), e);
            }
        });;
    }

}

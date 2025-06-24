package com.geeklib.ether.common;

import java.io.File;
import java.io.IOException;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.geeklib.ether.config.WorkspaceProperties;
import com.hazelcast.map.MapStore;

public class JsonMapStore<T> implements MapStore<Long, T> {

    protected Logger logger = LoggerFactory.getLogger(JsonMapStore.class);

    ObjectMapper objectMapper;

    WorkspaceProperties workspaceProperties;

    private final Class<T> type;

    public JsonMapStore(Class<T> type, WorkspaceProperties workspaceProperties, ObjectMapper objectMapper) {
        this.type = type;
        this.workspaceProperties = workspaceProperties;
        this.objectMapper = objectMapper;
    }

    /**
     * 获取存储文件
     * 
     * @return 存储文件
     */
    private File getStoreFile(Long key) {
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
    protected Class<T> getValueClass() {
        return type;
    };

    /**
     * 获取存储的键类型
     * 
     * @return 键类型的字符串表示
     */
    protected String getMapName() {
        return type.getSimpleName();
    }

    @Override
    public T load(Long key) {

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
    public void store(Long key, Object value) {

        try {
            Files.createDirectories(getStoreDir());
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(getStoreFile(key), value);
            logger.info("数据持久化成功，文件: {}", getStoreFile(key).getAbsolutePath());
        } catch (IOException e) {
            logger.error("数据持久化失败", e);
        }
    }

    @Override
    public Map<Long, T> loadAll(Collection<Long> keys) {
        Map<Long, T> maps = new HashMap<Long, T>();
        for (Long key : keys) {
            File file = getStoreFile(key);
            if (file.exists()) {
                T value = null;
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
    public Iterable<Long> loadAllKeys() {

        //TODO 循环性能需要优化
        List<Long> keys = null;
        try {
            keys = Files.list(getStoreDir()).map(Path::getFileName)
                    .map(name -> name.toString().substring(0, name.toString().lastIndexOf(".")))
                    .map(Long::valueOf).collect(Collectors.toList());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return keys;
    }

    @Override
    public void storeAll(Map<Long, T> map) {
        map.forEach((key, value) -> {
            store(key, value);
        });
    }

    @Override
    public void delete(Long key) {
        File file = getStoreFile(key);
        try {
            Files.deleteIfExists(file.toPath());
        } catch (IOException e) {
            logger.error("持久化文件删除失败", e);
        }
    }

    @Override
    public void deleteAll(Collection<Long> keys) {
        keys.forEach(key -> {
            try {
                Files.deleteIfExists(getStoreFile(key).toPath());
            } catch (IOException e) {
                logger.error("持久化文件删除失败", getStoreFile(key), e);
            }
        });;
    }

}

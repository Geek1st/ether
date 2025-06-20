package com.geeklib.ether.common;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.aopalliance.reflect.Class;
import org.springframework.stereotype.Component;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.flakeidgen.FlakeIdGenerator;
import com.hazelcast.map.IMap;


public class HazelcastHelper<V> {

    @Resource
    private HazelcastInstance hazelcastInstance;

    public V get(long id, V clazz){
        IMap<Long, V> iMap = hazelcastInstance.getMap(clazz.getClass().getName());
        V v = iMap.get(id);
        return v;
    }

    public <T> List<T> list(Class clazz){
        IMap<Long, T> iMap = hazelcastInstance.getMap(clazz.getClass().getSimpleName());
        return new ArrayList<T>(iMap.values());
    }

    public <T> Object save(T t){
        FlakeIdGenerator idGenerator = hazelcastInstance.getFlakeIdGenerator(t.getClass().getSimpleName());
        long id = idGenerator.newId();
        Object object = getMap(t).put(id, t);
        
        return object;
    }

    public <T> void save(List<T> t){
        t.forEach( item -> save(item) );
    }

    public <T> boolean update(long id, T t){
        return getMap(t).put(id, t) == null;
    }

    public <T> boolean delete(long id, Class clazz){
        return getMap(clazz.getClass().getSimpleName()).remove(id) != null;
    }


    public <T> IMap getMap(T t){
        return hazelcastInstance.getMap(t.getClass().getSimpleName());
    }


}
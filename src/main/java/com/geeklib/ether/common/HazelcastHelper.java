package com.geeklib.ether.common;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.aopalliance.reflect.Class;
import org.springframework.stereotype.Component;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.flakeidgen.FlakeIdGenerator;
import com.hazelcast.map.IMap;


@Component
public class HazelcastHelper {

    public static HazelcastInstance hazelcastInstance;

    @Resource
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        HazelcastHelper.hazelcastInstance = hazelcastInstance;
    }
    
    /**
     * 获取指定ID的对象
     * 
     * @param id 对象ID
     * @param clazz 对象类型
     * @return 对象实例
     */
    public static <T> T get(long id, T clazz) {
        IMap<Long, T> iMap = hazelcastInstance.getMap(clazz.getClass().getSimpleName());
        T t = iMap.get(id);
        return t;
    }

    public static <T> List<T> list(T clazz){
        IMap<Long, T> iMap = hazelcastInstance.getMap(clazz.getClass().getSimpleName());
        return new ArrayList<T>(iMap.values());
    }

    public <T> Object save(T t){
        FlakeIdGenerator idGenerator = hazelcastInstance.getFlakeIdGenerator(t.getClass().getSimpleName());
        long id = idGenerator.newId();
        try {
            t.getClass().getMethod("setId()", Long.class).invoke(t, id);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
package com.geeklib.ether.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import com.hazelcast.query.PagingPredicate;
import com.hazelcast.query.Predicates;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonKey;
import com.geeklib.ether.common.QueryParams.Operator;
import com.geeklib.ether.common.QueryParams.QueryParam;
import com.geeklib.ether.common.annotation.HazelcastIndex;
import com.geeklib.ether.common.exception.ResourceConflictException;
import com.geeklib.ether.common.exception.ResourceNotFoundException;
import com.geeklib.ether.common.exception.UniqueIndexViolationException;
import com.geeklib.ether.common.utils.BeanUtils;
import com.geeklib.ether.common.utils.StringUtils;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import com.hazelcast.query.PagingPredicate;
import com.hazelcast.query.Predicate;
import com.hazelcast.query.Predicates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class HazelcastHelper {

    public static HazelcastInstance hazelcastInstance;

    @Resource
    public void setHazelcastInstance(HazelcastInstance hazelcastInstance) {
        HazelcastHelper.hazelcastInstance = hazelcastInstance;
    }

    /**
     * 创建一个新的对象实例，如果对象已存在，则不保存并返回已存在的对象实例
     * 
     * @param <K> 自定义Key类型
     * @param <V> 自定义对象类型
     * @param k   自定义Key
     * @param v   自定义对象
     * @return 新创建的对象实例
     */
    public static <K, V> V create(K k, V v) {
        if (getMap(v).putIfAbsent(k, v) != null) {
            throw new ResourceConflictException(k.toString());
        }

        return getMap(v).get(k);
    }

    /**
     * 创建一个新的对象实例，如果对象已存在，则不保存并返回已存在的对象实例
     *
     * @param <V> 自定义对象类型
     * @param v   自定义对象
     * @return 新创建的对象实例
     */
    public static <K, V> V create(V v) {
        return create(getKey(v), v);
    }

    public static <K, V> V update(K k, V v) {
        V result = getMap(v).replace(k, v);
        if (result == null) {
            throw new ResourceNotFoundException(k.toString());
        }
        return result;
    }

    public static <K, V> V patch(K k, V v) {
        // TODO 存在并发问题，需加锁
        IMap<K, V> iMap = getMap(v);
        V oldBean = iMap.get(k);
        if (oldBean == null) {
            throw new ResourceNotFoundException(k.toString());
        } else {
            V newBean = BeanUtils.patchBean(v, oldBean);
            iMap.replace(k, newBean);
        }

        return oldBean;
    }

    /**
     * 获取指定ID的对象
     * 
     * @param id    对象ID
     * @param clazz 对象类型
     * @return 对象实例
     */
    public static <V> V get(Object key, Class<V> clazz) {
        IMap<Object, V> iMap = getMap(clazz);

        V v = iMap.get(key);
        if (v == null) {
            throw new ResourceNotFoundException(key.toString());
        }

        return v;
    }

    public static <T> List<T> list(Class<T> clazz) {
        IMap<Object, T> iMap = getMap(clazz);

        return new ArrayList<T>(iMap.values());
    }

    public static <T> List<T> list(Class<T> clazz, QueryParams queryParams, Pageable pageable) {
        IMap<Object, T> iMap = getMap(clazz);

        // 创建查询条件
        Predicate predicate = createPredicate(queryParams, clazz);

        // 获取分页参数
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        
        // 创建PagingPredicate
        PagingPredicate<Object, T> pagingPredicate = Predicates.pagingPredicate(predicate, size);
        pagingPredicate.setPage(page);

        // 执行查询
        List<T> results = new ArrayList<>(iMap.values(pagingPredicate));

        return results;
    }

    public static void delete(Object key, Class clazz) {
        if (getMap(clazz).remove(key) == null) {
            throw new ResourceNotFoundException("未找到要删除的对象: " + key);
        }
    }

    /**
     * 通过对象实例获取 Hazelcast 的 Map
     * 
     * @param <K> map 的 key 类型
     * @param <V> map 的 value 类型
     * @param v   对象实例
     * @return Hazelcast 的 Map
     */
    private static <K, V> IMap<K, V> getMap(V v) {

        String mapName = StringUtils.camelToUnderline(v.getClass().getSimpleName());
        return hazelcastInstance.getMap(mapName);
    }

    /**
     * 通过类获取 Hazelcast 的 Map
     * 
     * @param <K>   map 的 key 类型
     * @param <V>   map 的 value 类型
     * @param clazz 类对象
     * @return Hazelcast 的 Map
     */
    private static <K, V> IMap<K, V> getMap(Class<V> clazz) {
        String mapName = StringUtils.camelToUnderline(clazz.getSimpleName());
        return hazelcastInstance.getMap(mapName);
    }

    private static <T> boolean uniqueCheck(T t) {
        Map<String, List<Field>> unionUniqueFileds = new HashMap<>();// 用于存储联合唯一字段
        List<Field> uniqueFields = new ArrayList<>();// 用于存储单个唯一字段

        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            HazelcastIndex hazelcastIndex = field.getAnnotation(HazelcastIndex.class);

            if (hazelcastIndex == null) {
                continue;
            }

            if (hazelcastIndex.unique() == Void.class) {
                uniqueFields.add(field);
            } else {
                unionUniqueFileds.computeIfAbsent(hazelcastIndex.unique().getName(), k -> new ArrayList<Field>())
                        .add(field);
            }
        }

        uniqueFields.forEach(field -> {

            field.setAccessible(true);
            Object value;
            try {
                value = field.get(t);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                throw new RuntimeException("获取字段值失败: " + field.getName(), e);
            }
            boolean exists = getMap(t).values(
                    Predicates.equal(field.getName(), (Comparable) value)).size() > 0;
            if (exists) {
                throw new UniqueIndexViolationException(t.getClass(), field, value);
            }

        });

        return true;
    }

    /**
     * 获取对象的唯一键
     * 如果对象中有被 @JsonKey 注解标记的字段，则返回该字段的值作为唯一键
     * 如果没有被 @JsonKey 注解标记的字段，则返回 null
     * 
     * @param obj
     * @return
     */
    private static Object getKey(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(JsonKey.class)) {
                try {
                    field.setAccessible(true);
                    return field.get(obj);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    private static Object parseValue(Class<?> fieldType, Object value) {
        if (value == null) {
            return null;
        }

        if (fieldType == Integer.class || fieldType == int.class) {
            return Integer.parseInt(value.toString());
        } else if (fieldType == Long.class || fieldType == long.class) {
            return Long.parseLong(value.toString());
        } else if (fieldType == Double.class || fieldType == double.class) {
            return Double.parseDouble(value.toString());
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            return Boolean.parseBoolean(value.toString());
        } else if (fieldType == Date.class) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd").parse(value.toString());
            } catch (ParseException e) {
                throw new IllegalArgumentException("日期格式不正确，应为 yyyy-MM-dd");
            }
        }
        return value;
    }

    private static <T> Predicate createPredicate(QueryParams queryParams, Class<T> clazz) {
        if (queryParams.isEmpty()) {
            return Predicates.alwaysTrue(); // 如果没有查询条件，返回一个始终为真的条件
        }

        List<Predicate> predicates = new ArrayList<Predicate>();
        
        for (QueryParam queryParam : queryParams) {
            try {
                // 前台参数都是String类型，直接判断是否为日期格式
                String value = queryParam.getValue().toString();
                
                // 如果是日期类型，需要特殊处理
                if (value.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String[] dates = value.split(",");
                    if (dates.length == 2) {
                        Date startDate = sdf.parse(dates[0]);
                        Date endDate = sdf.parse(dates[1]);
                        predicates.add(Predicates.greaterThan(queryParam.getKey(), startDate));
                        predicates.add(Predicates.lessThan(queryParam.getKey(), endDate));
                    } else {
                        Date date = sdf.parse(value);
                        switch (queryParam.getOperator()) {
                            case EQ:
                                predicates.add(Predicates.equal(queryParam.getKey(), date));
                                break;
                            case NE:
                                predicates.add(Predicates.notEqual(queryParam.getKey(), date));
                                break;
                            case GT:
                                predicates.add(Predicates.greaterThan(queryParam.getKey(), date));
                                break;
                            case LT:
                                predicates.add(Predicates.lessThan(queryParam.getKey(), date));
                                break;
                            default:
                                throw new IllegalArgumentException("不支持的日期操作符: " + queryParam.getOperator());
                        }
                    }
                } else {
                    // 非日期类型，直接使用原始值
                    switch (queryParam.getOperator()) {
                        case EQ:
                            predicates.add(Predicates.equal(queryParam.getKey(), queryParam.getValue()));
                            break;
                        case NE:
                            predicates.add(Predicates.notEqual(queryParam.getKey(), queryParam.getValue()));
                            break;
                        case GT:
                            predicates.add(Predicates.greaterThan(queryParam.getKey(), queryParam.getValue()));
                            break;
                        case LT:
                            predicates.add(Predicates.lessThan(queryParam.getKey(), queryParam.getValue()));
                            break;
                        case IN:
                            predicates.add(Predicates.in(queryParam.getKey(), queryParam.getValue()));
                            break;
                        case LIKE:
                            predicates.add(Predicates.like(queryParam.getKey(), queryParam.getValue()));
                            break;
                        default:
                            break;
                    }
                }
            } catch (ParseException e) {
                throw new IllegalArgumentException("日期格式不正确，应为 yyyy-MM-dd");
            }
        }

        Predicate predicate = Predicates.and(predicates.toArray(new Predicate[0]));

        return predicate;
    }

}
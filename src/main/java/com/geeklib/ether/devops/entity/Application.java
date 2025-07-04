package com.geeklib.ether.devops.entity;

import com.geeklib.ether.annotation.Entity;
import com.geeklib.ether.annotation.HazelcastIndex;
import com.geeklib.ether.common.BaseEntity;
import com.hazelcast.config.IndexType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Application extends BaseEntity implements java.io.Serializable {
    
    private static final long serialVersionUID = 1L;

    @HazelcastIndex(unique = UkNameProjectId.class, type = IndexType.HASH)
    private String name;

    @HazelcastIndex(unique = UkNameProjectId.class, type = IndexType.HASH)
    private long projectId;

    public interface UkNameProjectId {}
}

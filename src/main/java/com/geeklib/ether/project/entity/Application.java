package com.geeklib.ether.project.entity;

import com.geeklib.ether.common.BaseEntity;
import com.geeklib.ether.common.annotation.Entity;
import com.geeklib.ether.common.annotation.HazelcastIndex;
import com.hazelcast.config.IndexType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Application extends BaseEntity {
    
    private static final long serialVersionUID = 1L;

    @HazelcastIndex(unique = UkNameProjectId.class, type = IndexType.HASH)
    private String name;

    @HazelcastIndex(unique = UkNameProjectId.class, type = IndexType.HASH)
    private long projectId;

    private String description;

    private String owner;

    private String status;

    private String type;

    public interface UkNameProjectId {}
}

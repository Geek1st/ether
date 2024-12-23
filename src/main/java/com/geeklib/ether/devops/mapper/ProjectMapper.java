package com.geeklib.ether.devops.mapper;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.Mapping;
import com.geeklib.ether.devops.entity.Project;

@Repository
public interface ProjectMapper extends BaseMapper<Project, Long> {

}

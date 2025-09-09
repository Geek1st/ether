package com.geeklib.ether.system.service;

import java.util.Set;

import org.springframework.data.domain.Pageable;

import com.geeklib.ether.common.QueryParams.QueryParam;
import com.geeklib.ether.system.entity.Role;

public interface RoleService {

    Set<Role> listRole(QueryParam queryParam, Pageable pageable);

    Role getRole(String name);

    void createRole(Role role);

    void updateRole(Role role);

    void patchRole(Role role);

    void deleteRole(String name);
}

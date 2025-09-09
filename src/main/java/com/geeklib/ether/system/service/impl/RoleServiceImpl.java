package com.geeklib.ether.system.service.impl;

import java.util.Set;

import org.springframework.data.domain.Pageable;

import com.geeklib.ether.common.QueryParams.QueryParam;
import com.geeklib.ether.system.entity.Role;
import com.geeklib.ether.system.service.RoleService;

public class RoleServiceImpl implements RoleService {

    @Override
    public Set<Role> listRole(QueryParam queryParam, Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listRole'");
    }

    @Override
    public Role getRole(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRole'");
    }

    @Override
    public void createRole(Role role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createRole'");
    }

    @Override
    public void updateRole(Role role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateRole'");
    }

    @Override
    public void patchRole(Role role) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'patchRole'");
    }

    @Override
    public void deleteRole(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteRole'");
    }

}

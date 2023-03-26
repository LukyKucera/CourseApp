package com.javacorner.service;

import com.javacorner.entity.Role;

public interface RoleService {

    Role loadRoleByName(String roleName);

    Role createRole(String roleName);
}

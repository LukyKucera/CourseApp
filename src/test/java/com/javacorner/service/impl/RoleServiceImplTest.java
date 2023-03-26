package com.javacorner.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.javacorner.dao.RoleDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleDao roleDao;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void testLoadRoleByName() {
        String name = "roleName";
        roleService.loadRoleByName(name);
        verify(roleDao).findByName(any());

    }

    @Test
    void testCreateRole() {
        roleService.createRole("Admin");
        roleService.createRole("Instructor");
        verify(roleDao, times(2)).save(any());
    }
}
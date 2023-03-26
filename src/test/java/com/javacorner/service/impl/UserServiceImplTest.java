package com.javacorner.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.javacorner.dao.RoleDao;
import com.javacorner.dao.UserDao;
import com.javacorner.entity.Role;
import com.javacorner.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserDao userDao;

    @Mock
    RoleDao roleDao;

    @Mock
    User mockedUser;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    void testLoadUserByEmail() {
        userService.loadUserByEmail("user01@gmail.com");
        verify(userDao, times(1)).findByEmail(any());

    }

    @Test
    void testCreateUser() {
        String email = "user@gmail.com";
        String password = "test";
        User user = new User(email, password);

        userService.createUser(email, password);

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userDao).save(argumentCaptor.capture());

        User capturedUser = argumentCaptor.getValue();

        assertEquals(user, capturedUser);
    }

    @Test
    void testAssignRoleToUser() {
        Role role = new Role();
        role.setRoleId(1L);

        when(userDao.findByEmail(any())).thenReturn(mockedUser);
        when(roleDao.findByName(any())).thenReturn(role);

        userService.assignRoleToUser("email@gmail.com", "roleName");

        verify(mockedUser, times(1)).assignRoleToUser(role);

    }
}
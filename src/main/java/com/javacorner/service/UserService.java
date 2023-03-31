package com.javacorner.service;

import com.javacorner.entity.User;

public interface UserService {

    User loadUserByEmail(String email);

    User createUser(String email, String password);

    void assignRoleToUser(String email, String roleName);

    boolean doesCurrentUserHasRole(String roleName);

}
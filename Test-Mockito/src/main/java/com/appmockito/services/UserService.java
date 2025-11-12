package com.appmockito.services;

import com.appmockito.models.UserEntity;

public interface UserService {
    UserEntity createUser(String firstName,
                          String lastName,
                          String email,
                          String password,
                          String repeatPassword);
}

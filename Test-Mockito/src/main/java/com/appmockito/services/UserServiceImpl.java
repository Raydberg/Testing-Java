package com.appmockito.services;

import com.appmockito.models.UserEntity;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public UserEntity createUser(String firstName,
                                 String lastName,
                                 String email,
                                 String password,
                                 String repeatPassword) {


        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("User's first name is empty");
        }

        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("User last name is empty");
        }

        UserEntity user = new UserEntity(firstName, lastName, email, UUID.randomUUID().toString());



        return user;
    }

}

package com.sk.skala.myapp.service;

import com.sk.skala.myapp.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getUsers(String name);

    Optional<User> getUserById(long id);

    User createUser(User user);

    Optional<User> updateUser(long id, User updatedUser);

    void deleteUser(long id);
}

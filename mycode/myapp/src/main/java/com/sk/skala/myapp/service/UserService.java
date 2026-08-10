package com.sk.skala.myapp.service;

import com.sk.skala.myapp.domain.User;
import com.sk.skala.myapp.dto.UserRequest;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getUsers(String name);

    Optional<User> getUserById(long id);

    User createUser(@Valid UserRequest request);

    Optional<User> updateUser(long id, @Valid UserRequest request);

    void deleteUser(long id);
}

package com.sk.skala.myapp.service;

import com.sk.skala.myapp.domain.User;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

// UserServiceImpl(RealSubject)을 감싸서, 모든 메소드 호출 전후에 시간을 출력하는 Proxy
public class UserServiceProxy implements UserService {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    private final UserService target;

    public UserServiceProxy(UserService target) {
        this.target = target;
    }

    @Override
    public List<User> getUsers(String name) {
        return around("getUsers", () -> target.getUsers(name));
    }

    @Override
    public Optional<User> getUserById(long id) {
        return around("getUserById", () -> target.getUserById(id));
    }

    @Override
    public User createUser(User user) {
        return around("createUser", () -> target.createUser(user));
    }

    @Override
    public Optional<User> updateUser(long id, User updatedUser) {
        return around("updateUser", () -> target.updateUser(id, updatedUser));
    }

    @Override
    public void deleteUser(long id) {
        around("deleteUser", () -> {
            target.deleteUser(id);
            return null;
        });
    }

    private <T> T around(String methodName, Supplier<T> call) {
        long start = System.currentTimeMillis();
        System.out.println("[Proxy] " + methodName + " 메소드 시작: " + LocalTime.now().format(TIME_FORMATTER));
        T result = call.get();
        long elapsed = System.currentTimeMillis() - start;
        System.out.println("[Proxy] " + methodName + " 메소드 종료: " + LocalTime.now().format(TIME_FORMATTER)
                + " -> 총 소요 시간: " + elapsed + " ms");
        return result;
    }
}

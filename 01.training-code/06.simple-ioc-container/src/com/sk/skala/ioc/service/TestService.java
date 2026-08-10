package com.sk.skala.ioc.service;

import com.sk.skala.ioc.annotation.Service;

@Service
public class TestService {

    private final UserService userService;

    public TestService(UserService userService) {
        this.userService = userService;
    }

    public String runTest(String name) {
        return userService.getUser(name);
    }
}

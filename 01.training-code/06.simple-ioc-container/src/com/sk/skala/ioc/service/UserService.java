package com.sk.skala.ioc.service;

import com.sk.skala.ioc.annotation.Service;
import com.sk.skala.ioc.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUser(String name) {
        return name + "의 이메일은 " + userRepository.findEmailByName(name) + " 입니다";
    }
}

package com.sk.skala.myapp.config;

import com.sk.skala.myapp.repository.UserRepository;
import com.sk.skala.myapp.service.UserService;
import com.sk.skala.myapp.service.UserServiceImpl;
import com.sk.skala.myapp.service.UserServiceProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceConfig {

    @Bean
    public UserService userService(UserRepository userRepository) {
        // 실제 대상 생성
        UserService target = new UserServiceImpl(userRepository);
        // 프록시로 감싸서 반환
        return new UserServiceProxy(target);
    }
}

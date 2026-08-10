package com.sk.skala.myapp.service;

import com.sk.skala.myapp.domain.User;
import com.sk.skala.myapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 모든 사용자 조회 / name으로 조회
    public List<User> getUsers(String name) {
        log.info("getUsers service called");
        log.debug("querying users with name filter: {}", name);
        if (name == null) {
            return userRepository.findAll();
        }
        return userRepository.findByName(name);
    }

    // 특정 사용자 조회
    public Optional<User> getUserById(long id) {
        log.info("getUserById service called");
        log.debug("querying user id: {}", id);
        return userRepository.findById(id);
    }

    // 사용자 추가
    public User createUser(User user) {
        log.info("createUser service called");
        log.debug("saving user: {}", user);
        return userRepository.save(user);
    }

    // 사용자 정보 수정
    public Optional<User> updateUser(long id, User updatedUser) {
        log.info("updateUser service called");
        log.debug("updating user id: {}, payload: {}", id, updatedUser);
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        });
    }

    // 사용자 삭제
    public void deleteUser(long id) {
        log.info("deleteUser service called");
        log.debug("deleting user id: {}", id);
        userRepository.deleteById(id);
    }
}

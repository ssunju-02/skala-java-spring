package com.sk.skala.myapp.service;

import com.sk.skala.myapp.domain.User;
import com.sk.skala.myapp.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 모든 사용자 조회 / name으로 조회
    public List<User> getUsers(String name) {
        if (name == null) {
            return userRepository.findAll();
        }
        return userRepository.findByName(name);
    }

    // 특정 사용자 조회
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }

    // 사용자 추가
    public User createUser(User user) {
        return userRepository.save(user);
    }

    // 사용자 정보 수정
    public Optional<User> updateUser(long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setName(updatedUser.getName());
            user.setEmail(updatedUser.getEmail());
            return userRepository.save(user);
        });
    }

    // 사용자 삭제
    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}

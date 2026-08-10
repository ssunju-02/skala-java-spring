package com.sk.skala.myapp.service;

import com.sk.skala.myapp.domain.User;
import com.sk.skala.myapp.dto.UserRequest;
import com.sk.skala.myapp.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

// Configuration 기반 수동 Proxy 대신, MetricsAspect(@Aspect/@Around)가 시간 측정을 대신하므로
// 다시 컴포넌트 스캔으로 등록되는 평범한 @Service Bean으로 되돌린다.
// @Validated: createUser/updateUser의 @Valid UserRequest 파라미터를 AOP Proxy로 검증
// 클래스 레벨 readOnly=true: 조회 메소드는 변경 감지/추적을 하지 않아 성능이 좋고, 쓰기가 원천 차단됨
@Slf4j
@Service
@Validated
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 모든 사용자 조회 / name으로 조회
    @Override
    public List<User> getUsers(String name) {
        log.info("getUsers service called");
        log.debug("querying users with name filter: {}", name);
        if (name == null) {
            return userRepository.findAll();
        }
        return userRepository.findByName(name);
    }

    // 특정 사용자 조회
    @Override
    public Optional<User> getUserById(long id) {
        log.info("getUserById service called");
        log.debug("querying user id: {}", id);
        return userRepository.findById(id);
    }

    // 사용자 추가
    @Override
    @Transactional
    public User createUser(@Valid UserRequest request) {
        log.info("createUser service called");
        log.debug("saving user: {}", request);
        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        return userRepository.save(user);
    }

    // 사용자 정보 수정
    @Override
    @Transactional
    public Optional<User> updateUser(long id, @Valid UserRequest request) {
        log.info("updateUser service called");
        log.debug("updating user id: {}, payload: {}", id, request);
        return userRepository.findById(id).map(user -> {
            user.setName(request.name());
            user.setEmail(request.email());
            return userRepository.save(user);
        });
    }

    // 사용자 삭제
    @Override
    @Transactional
    public void deleteUser(long id) {
        log.info("deleteUser service called");
        log.debug("deleting user id: {}", id);
        userRepository.deleteById(id);
    }
}

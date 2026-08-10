package com.sk.skala.myapp.controller;

import com.sk.skala.myapp.aspect.Metrics;
import com.sk.skala.myapp.dto.UserRequest;
import com.sk.skala.myapp.dto.UserResponse;
import com.sk.skala.myapp.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET: 모든 사용자 조회 / name으로 조회 (/api/users, /api/users?name=xxx)
    @GetMapping("/users")
    public List<UserResponse> getUsers(@RequestParam Optional<String> name) {
        log.info("getUsers called");
        log.debug("getUsers called with name filter: {}", name.orElse("none"));
        return userService.getUsers(name.orElse(null)).stream()
                .map(UserResponse::from)
                .toList();
    }

    // GET: 특정 사용자 조회 (/api/users/{id})
    @Metrics
    @GetMapping("/users/{id}")
    public UserResponse getUserById(@PathVariable @Positive(message = "ID는 양수여야 합니다") long id) {
        log.info("getUserById called");
        log.debug("getUserById called with id: {}", id);
        return userService.getUserById(id).map(UserResponse::from).orElse(null);
    }

    // POST: 사용자 추가
    @PostMapping("/users")
    public UserResponse createUser(@Valid @RequestBody UserRequest request) {
        log.info("createUser called");
        log.debug("createUser payload: {}", request);
        return UserResponse.from(userService.createUser(request));
    }

    // PUT: 사용자 정보 수정
    @PutMapping("/users/{id}")
    public UserResponse updateUser(@PathVariable @Positive(message = "ID는 양수여야 합니다") long id,
                                    @Valid @RequestBody UserRequest request) {
        log.info("updateUser called");
        log.debug("updateUser id: {}, payload: {}", id, request);
        return userService.updateUser(id, request).map(UserResponse::from).orElse(null);
    }

    // DELETE: 사용자 삭제
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable @Positive(message = "ID는 양수여야 합니다") long id) {
        log.info("deleteUser called");
        log.debug("deleteUser id: {}", id);
        userService.deleteUser(id);
    }
}

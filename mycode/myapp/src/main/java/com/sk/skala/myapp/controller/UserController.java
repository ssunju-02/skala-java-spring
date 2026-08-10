package com.sk.skala.myapp.controller;

import com.sk.skala.myapp.domain.User;
import com.sk.skala.myapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
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
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET: 모든 사용자 조회 / name으로 조회 (/api/users, /api/users?name=xxx)
    @GetMapping("/users")
    public List<User> getUsers(@RequestParam Optional<String> name) {
        log.info("getUsers called");
        log.debug("getUsers called with name filter: {}", name.orElse("none"));
        return userService.getUsers(name.orElse(null));
    }

    // GET: 특정 사용자 조회 (/api/users/{id})
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable long id) {
        log.info("getUserById called");
        log.debug("getUserById called with id: {}", id);
        return userService.getUserById(id).orElse(null);
    }

    // POST: 사용자 추가
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        log.info("createUser called");
        log.debug("createUser payload: {}", user);
        return userService.createUser(user);
    }

    // PUT: 사용자 정보 수정
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User updatedUser) {
        log.info("updateUser called");
        log.debug("updateUser id: {}, payload: {}", id, updatedUser);
        return userService.updateUser(id, updatedUser).orElse(null);
    }

    // DELETE: 사용자 삭제
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable long id) {
        log.info("deleteUser called");
        log.debug("deleteUser id: {}", id);
        userService.deleteUser(id);
    }
}

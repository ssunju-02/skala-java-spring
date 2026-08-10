package com.sk.skala.myapp.controller;

import com.sk.skala.myapp.domain.User;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    private List<User> users = new ArrayList<>(List.of(
            new User(1, "alice", "alice@example.com"),
            new User(2, "bob", "bob@example.com"),
            new User(3, "charlie", "charlie@example.com")
    ));
    private long userIdCounter = 4;

    // GET: 모든 사용자 조회 / name으로 조회 (/api/users, /api/users?name=xxx)
    @GetMapping("/users")
    public List<User> getUsers(@RequestParam(required = false) String name) {
        if (name == null) {
            return users;
        }
        return users.stream()
                .filter(u -> u.getName().equals(name))
                .collect(Collectors.toList());
    }

    // GET: 특정 사용자 조회 (/api/users/{id})
    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable long id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // POST: 사용자 추가
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        user.setId(userIdCounter++);
        users.add(user);
        return user;
    }

    // PUT: 사용자 정보 수정
    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable long id, @RequestBody User updatedUser) {
        for (User u : users) {
            if (u.getId() == id) {
                u.setName(updatedUser.getName());
                u.setEmail(updatedUser.getEmail());
                return u;
            }
        }
        return null;
    }

    // DELETE: 사용자 삭제
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable long id) {
        users.removeIf(u -> u.getId() == id);
    }
}

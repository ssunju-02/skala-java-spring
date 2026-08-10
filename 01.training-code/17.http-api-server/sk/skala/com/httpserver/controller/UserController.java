package sk.skala.com.httpserver.controller;

import sk.skala.com.httpserver.annotation.Controller;
import sk.skala.com.httpserver.annotation.GetMapping;
import sk.skala.com.httpserver.annotation.PathVariable;
import sk.skala.com.httpserver.annotation.PostMapping;
import sk.skala.com.httpserver.domain.User;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    private final List<User> users = new ArrayList<>(List.of(
            new User(1, "alice", "alice@example.com"),
            new User(2, "bob", "bob@example.com"),
            new User(3, "charlie", "charlie@example.com")
    ));
    private long userIdCounter = 4;

    @GetMapping("/users")
    public List<User> getUsers() {
        return users;
    }

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable long id) {
        return users.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @PostMapping("/users")
    public User createUser(User user) {
        user.setId(userIdCounter++);
        users.add(user);
        return user;
    }
}

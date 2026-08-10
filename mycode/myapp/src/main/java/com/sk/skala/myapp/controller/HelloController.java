package com.sk.skala.myapp.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")

public class HelloController {
    @GetMapping("/hello")
    public Map<String, String> HelloWorld() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "스칼라에 오신 것을 환영합니다");
        return response;
    }
}

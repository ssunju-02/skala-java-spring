package com.sk.skala.ioc.repository;

import java.util.Map;

// @Service가 없어도, UserService의 생성자 파라미터로 요청되면 컨테이너가 대신 만들어 준다.
public class UserRepository {

    private final Map<String, String> users = Map.of(
            "홍길동", "hong@sk.com",
            "김철수", "kim@sk.com"
    );

    public String findEmailByName(String name) {
        return users.getOrDefault(name, "등록되지 않은 사용자");
    }
}

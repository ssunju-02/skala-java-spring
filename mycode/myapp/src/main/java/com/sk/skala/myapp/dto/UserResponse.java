package com.sk.skala.myapp.dto;

import com.sk.skala.myapp.domain.User;

public record UserResponse(Long id, String name, String email) {

    // 정적 팩토리 메소드로 Entity -> DTO 변환
    public static UserResponse from(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}

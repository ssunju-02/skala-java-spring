package com.sk.skala.myapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// record는 불변(immutable)이라 Lombok의 setter 없이도 Jackson 역직렬화가 가능하다.
public record UserRequest(
        @NotBlank(message = "이름은 필수입니다") String name,
        @NotBlank(message = "이메일은 필수입니다")
        @Email(message = "올바른 이메일 형식이 아닙니다") String email
) {
}

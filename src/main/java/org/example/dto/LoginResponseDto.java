package org.example.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String accessToken;   // JWT 토큰
    private long expiresInSec;    // 만료 시간 (초 단위)
    private UserDto user;         // 유저 정보 객체 (명세서 요구사항)

    // 내부 클래스 (User 정보 담는 그릇)
    @Data
    @Builder
    public static class UserDto {
        private Long userId;      // u_123
        private String email;     // user@x.com (username)
        private String name;      // 홍길동 (nickname)
    }
}
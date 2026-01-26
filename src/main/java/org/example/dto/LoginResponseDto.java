package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private String accessToken;  // JWT 토큰
    private long expiresInSec;   // 만료 시간 (예: 1800)

    // 명세서에 있는 중첩된 user 객체
    private UserInfoDto user;

    // 내부에서만 쓸 DTO라 static inner class로 정의
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInfoDto {
        private String userId;   // "u_1" 같은 ID
        private String email;
        private String name;
    }
}
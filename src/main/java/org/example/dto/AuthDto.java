package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthDto {

    // 1. 회원가입 요청 (명세서: email, password, name)
    @Data
    public static class SignupRequest {
        private String email;    // username -> email 변경
        private String password;
        private String name;
    }

    // 2. 회원가입 응답 (명세서: userId, email)
    @Data @Builder
    public static class SignupResponse {
        private String userId; // 예: "1" 또는 "u_1"
        private String email;
    }

    // 3. 로그인 요청 (명세서: email, password)
    @Data
    public static class LoginRequest {
        private String email;
        private String password;
    }

    // 4. 로그인 응답 (명세서: accessToken, expiresInSec, user 정보)
    @Data @Builder
    public static class LoginResponse {
        private String accessToken;
        private long expiresInSec; // 토큰 만료 시간 (예: 1800)
        private UserInfoDto user;  // 아래 UserInfoDto 객체 포함
    }

    // 5. 로그인 응답 안에 들어가는 유저 정보
    @Data @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class UserInfoDto {
        private String userId;
        private String email;
        private String name;
    }
}
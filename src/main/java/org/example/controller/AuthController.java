package org.example.controller;

import org.example.dto.LoginRequestDto;
import org.example.dto.LoginResponseDto;
import org.example.dto.SignupRequestDto;
import org.example.dto.SignupResponseDto; // 💡 방금 만든 파일 import
import org.example.dto.common.ApiResponse;
import org.example.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // 회원가입 API (수정됨)
    @PostMapping("/signup")
    public ApiResponse<SignupResponseDto> signup(@RequestBody SignupRequestDto requestDto) {

        // Service가 이제 void가 아니라 객체(SignupResponseDto)를 리턴합니다.
        SignupResponseDto response = authService.signup(requestDto);

        return ApiResponse.success(response);
    }

    // 로그인 API (기존 유지)
    @PostMapping("/login")
    public ApiResponse<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto response = authService.login(requestDto);
        return ApiResponse.success(response);
    }
}
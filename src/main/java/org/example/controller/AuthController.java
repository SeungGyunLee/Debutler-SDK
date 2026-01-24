package org.example.controller;


import org.example.dto.LoginRequestDto;
import org.example.dto.LoginResponseDto;
import org.example.dto.SignupResquestDto;
import org.example.dto.common.ApiResponse;
import org.example.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    // 회원가입 API
    @PostMapping("/signup")
    public ApiResponse<String> signup(@RequestBody SignupResquestDto resquestDto) {
        authService.signup(resquestDto);
        return ApiResponse.success("회원가입 완료");
    }

    // 로그인 API
    @PostMapping("/login")
    public ApiResponse<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto response = authService.login(requestDto);
        // 로그인 성공시 프론트엔드에 Bearer 토큰값 반환
        return ApiResponse.success(response);
    }
}

package org.example.service;

import org.example.config.JwtTokenProvider;
import org.example.domain.User;
// 💡 따로 만든 DTO 파일들을 각각 임포트
import org.example.dto.LoginRequestDto;
import org.example.dto.LoginResponseDto;
import org.example.dto.SignupRequestDto;
import org.example.dto.SignupResponseDto;
import org.example.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    // 1. 회원가입
    @Transactional
    public SignupResponseDto signup(SignupRequestDto requestDto) { // 반환타입 변경

        // 이메일 중복 체크 (email 사용)
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        // 유저 생성
        User user = User.builder()
                .email(requestDto.getEmail())       // email 저장
                .password(encodedPassword)
                .name(requestDto.getName())
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        // DB 저장
        User savedUser = userRepository.save(user);

        // 결과 반환 (명세서 규격)
        return SignupResponseDto.builder()
                .userId(String.valueOf(savedUser.getId()))
                .email(savedUser.getEmail())
                .build();
    }

    // 2. 로그인
    @Transactional(readOnly = true)
    public LoginResponseDto login(LoginRequestDto requestDto) {

        // 이메일로 찾기
        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        // 비번 확인
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }

        // 토큰 발급
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRoles());
        long expiresIn = 1800; // 30분

        // 응답 객체 생성
        return LoginResponseDto.builder()
                .accessToken(token)
                .expiresInSec(expiresIn)
                .user(LoginResponseDto.UserInfoDto.builder()
                        .userId(String.valueOf(user.getId()))
                        .email(user.getEmail())
                        .name(user.getName())
                        .build())
                .build();
    }
}
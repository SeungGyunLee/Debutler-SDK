package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.Consent;
import org.example.domain.User;
import org.example.dto.ConsentDto;
import org.example.dto.common.ApiResponse;
import org.example.repository.ConsentRepository;
import org.example.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/consents")
@RequiredArgsConstructor
public class ConsentController {

    private final ConsentRepository consentRepository;
    private final UserRepository userRepository;

    // 2.2 약관 동의 기록 (POST) - 수정됨 (List 지원)
    @PostMapping
    @Transactional
    public ApiResponse<String> createConsents( // 메서드명 변경 (복수형)
                                               @RequestBody List<ConsentDto.Request> requests, // 💡 핵심: List로 받기!
                                               @AuthenticationPrincipal UserDetails userDetails
    ) {
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        // 반복문으로 들어온 요청을 모두 처리
        for (ConsentDto.Request request : requests) {

            // 1. 기존 동의 내역 확인
            Consent consent = consentRepository.findByUserAndType(user, request.getType())
                    .orElse(Consent.builder()
                            .user(user)
                            .type(request.getType())
                            .build());

            // 2. 시간 설정
            LocalDateTime agreedTime = request.getAgreedAt() != null ? request.getAgreedAt() : LocalDateTime.now();

            // 3. 업데이트 또는 생성 객체 준비
            Consent newConsent = Consent.builder()
                    .id(consent.getId())
                    .user(user)
                    .type(request.getType())
                    .version(request.getVersion())
                    .agreed(request.isAgreed()) // true or false
                    .agreedAt(agreedTime)
                    .build();

            // 4. 저장
            consentRepository.save(newConsent);
        }

        return ApiResponse.success("모든 동의 내역이 저장되었습니다.");
    }

    // ... 기존 @GetMapping 코드는 그대로 두시면 됩니다 ...
    // 2.3 사용자 동의 현황 조회 (GET)
    @GetMapping
    public ApiResponse<List<ConsentDto.Response>> getConsents(@AuthenticationPrincipal UserDetails userDetails) {
        // (아까 작성한 코드 그대로 유지)
        User user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        List<Consent> consents = consentRepository.findAllByUser(user);
        List<ConsentDto.Response> responseList = new ArrayList<>();

        responseList.add(convertOrEmpty(consents, "MYDATA", "2026-01-01"));
        responseList.add(convertOrEmpty(consents, "CRYPTO_WALLET_LOOKUP", "2026-01-01"));

        return ApiResponse.success(responseList);
    }

    private ConsentDto.Response convertOrEmpty(List<Consent> list, String type, String defaultVersion) {
        // (아까 작성한 코드 그대로 유지)
        Optional<Consent> match = list.stream()
                .filter(c -> c.getType().equals(type))
                .findFirst();

        if (match.isPresent()) {
            Consent c = match.get();
            return ConsentDto.Response.builder()
                    .type(c.getType())
                    .version(c.getVersion())
                    .agreed(c.isAgreed())
                    .agreedAt(c.getAgreedAt())
                    .build();
        } else {
            return ConsentDto.Response.builder()
                    .type(type)
                    .version(defaultVersion)
                    .agreed(false)
                    .agreedAt(null)
                    .build();
        }
    }
}
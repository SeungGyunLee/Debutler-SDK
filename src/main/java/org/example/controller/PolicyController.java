package org.example.controller;

import com.sun.tools.javac.util.DefinedBy;
import lombok.RequiredArgsConstructor;
import org.example.domain.Consent;
import org.example.domain.User;
import org.example.dto.common.ApiResponse;
import org.example.repository.ConsentRepository;
import org.example.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PolicyController {
    private final ConsentRepository consentRepo;
    private final UserRepository userRepo;

    // 약관 목록 조회
    @GetMapping("/policies/terms")
    public ApiResponse<Map<String, List<Map<String, Object>>>>getTerms(@RequestParam(required = false) String type) {
        Map<String, Object> term = new HashMap<>();
        term.put("type", "MYDATA");
        term.put("version", "2026-01-01");
        term.put("title", "마이데이터 이용약관");
        term.put("mandatory", true);
        term.put("contentUrl", "https://terms.site/mydata/2026-01-01");

        return ApiResponse.success(Collections.singletonMap("items", Collections.singletonList(term)));
    }

    // 약관 동의 기록 저장
    @PostMapping("/consents")
    public ApiResponse<String> saveConsent(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, Object> request) {
        User user = userRepo.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        String type = (String) request.get("type");
        String version = (String) request.get("version");
        boolean agreed = (boolean) request.get("agreed");

        Consent consent = Consent.builder()
                .user(user)
                .type(type)
                .version(version)
                .agreed(agreed)
                .agreedAt(LocalDateTime.now())
                .build();

        consentRepo.save(consent);

        return ApiResponse.success("동의 처리 완료");
    }

    // 사용자 동의 현황 조회 로직
    @GetMapping("/consents")
    public ApiResponse<Map<String, List<Consent>>> getConsents(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepo.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        List<Consent> list = consentRepo.findAllByUser(user);
        return ApiResponse.success(Collections.singletonMap("items", list));
    }
}

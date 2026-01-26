package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.PolicyDto;
import org.example.dto.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/policies")
@RequiredArgsConstructor
public class PolicyController {

    // 2.1 약관 목록 조회
    @GetMapping("/terms")
    public ApiResponse<List<PolicyDto>> getTerms(@RequestParam(required = false) String type) {

        // 1. 전체 약관 리스트 만들기 (하드코딩)
        List<PolicyDto> allPolicies = new ArrayList<>();

        // (1) 마이데이터 약관
        allPolicies.add(PolicyDto.builder()
                .type("MYDATA")
                .version("2026-01-01")
                .title("마이데이터 이용약관")
                .mandatory(true)
                .contentUrl("https://debutler.org/terms/mydata/2026-01-01")
                .build());

        // (2) 가상자산 약관 (여기가 빠져있었을 수 있음!)
        allPolicies.add(PolicyDto.builder()
                .type("CRYPTO_WALLET_LOOKUP")
                .version("2026-01-01")
                .title("가상자산 지갑 조회 약관")
                .mandatory(true)
                .contentUrl("https://debutler.org/terms/crypto/2026-01-01")
                .build());

        // 2. 필터링 로직 (type 파라미터가 있으면 그것만 걸러서 줌)
        if (type != null && !type.isEmpty()) {
            List<PolicyDto> filtered = allPolicies.stream()
                    .filter(policy -> policy.getType().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
            return ApiResponse.success(filtered);
        }

        // 파라미터 없으면 전체 반환
        return ApiResponse.success(allPolicies);
    }
}
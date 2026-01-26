package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.MyDataPortfolioDto;
import org.example.service.MyDataReadService;
import org.example.service.MyDataSyncService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/mydata")
@RequiredArgsConstructor
public class MyDataController {

    private final MyDataSyncService myDataSyncService;
    private final MyDataReadService myDataReadService;

    // 1. 데이터 연동 (Sync)
    // 프론트엔드 요청 예시: { "mock_token": "...", "user_search_id": "..." }
    @PostMapping("/connect")
    public ResponseEntity<String> connectMyData(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody Map<String, String> body
    ) {
        String username = userDetails.getUsername();

        // [수정] Request Body에서 토큰과 CI값 추출
        String mockToken = body.get("mock_token");
        String userSearchId = body.get("user_search_id"); // 표준 API 필수 헤더값

        // [수정] 서비스 메서드 호출 (파라미터 3개)
        myDataSyncService.syncAllAssets(username, mockToken, userSearchId);

        return ResponseEntity.ok("연동 및 데이터 저장이 완료되었습니다.");
    }

    // 2. 통합 포트폴리오 조회 (Read)
    @GetMapping("/portfolio")
    public ResponseEntity<MyDataPortfolioDto> getPortfolio(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        String username = userDetails.getUsername();

        MyDataPortfolioDto result = myDataReadService.getPortfolioByUsername(username);

        return ResponseEntity.ok(result);
    }
}
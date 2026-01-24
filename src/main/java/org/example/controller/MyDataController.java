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
    @PostMapping("/connect")
    public ResponseEntity<String> connectMyData(
            @AuthenticationPrincipal UserDetails userDetails, // 👈 로그인된 유저 정보 추출
            @RequestBody Map<String, String> body
    ) {
        // 서비스가 String username을 받기로 했으므로 유저명을 꺼냅니다.
        String username = userDetails.getUsername();
        String mockToken = body.get("mock_token");

        // 수정된 서비스 메서드 호출 (username 전달)
        myDataSyncService.syncAllAssets(username, mockToken);

        return ResponseEntity.ok("연동 및 데이터 저장이 완료되었습니다.");
    }

    // 2. 통합 포트폴리오 조회 (Read)
    @GetMapping("/portfolio")
    public ResponseEntity<MyDataPortfolioDto> getPortfolio(
            @AuthenticationPrincipal UserDetails userDetails // 👈 여기도 동일하게 적용
    ) {
        String username = userDetails.getUsername();

        // ReadService도 username을 받도록 수정했다면 아래와 같이 호출합니다.
        MyDataPortfolioDto result = myDataReadService.getPortfolioByUsername(username);

        return ResponseEntity.ok(result);
    }
}
package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.*;
import org.example.service.AssetService;
import org.example.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assets")
@RequiredArgsConstructor
public class AssetController {
    private final AssetService assetService;

    // 대시보드 (GET /api/assets/dashboard)
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardResponseDto> dashboard(@AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.ok(assetService.getDashboard(user.getUsername()));
    }

    // 은행 추가 (POST /api/assets/bank)
    @PostMapping("/bank")
    public ResponseEntity<String> addBank(@RequestBody AddAssetRequestDto dto, @AuthenticationPrincipal UserDetails user) {
        assetService.addBank(user.getUsername(), dto);
        return ResponseEntity.ok("은행 계좌 연결 완료");
    }

    // 코인 추가 (POST /api/assets/crypto)
    @PostMapping("/crypto")
    public ResponseEntity<String> addCrypto(@RequestBody AddAssetRequestDto dto, @AuthenticationPrincipal UserDetails user) {
        assetService.addCrypto(user.getUsername(), dto);
        return ResponseEntity.ok("가상자산 지갑 연결 완료");
    }



}

package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

// 명세서 2.2, 2.3 대응
public class ConsentDto {

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {
        private String type;     // MYDATA, CRYPTO_WALLET_LOOKUP
        private String version;  // 2026-01-01
        private boolean agreed;  // true/false
        private LocalDateTime agreedAt; // 명세서에 있음 (선택 사항)
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private String type;
        private String version;
        private boolean agreed;
        private LocalDateTime agreedAt;
    }
}
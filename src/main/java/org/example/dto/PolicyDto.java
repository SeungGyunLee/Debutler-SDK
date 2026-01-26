package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// 명세서 2.1 대응
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PolicyDto {
    private String type;       // MYDATA 등
    private String version;    // 2026-01-01
    private String title;      // 마이데이터 이용약관
    private boolean mandatory; // 필수 여부
    private String contentUrl; // 약관 내용 URL
}
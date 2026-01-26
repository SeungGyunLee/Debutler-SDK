package org.example.dto;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class SignupResponseDto {
    private String userId; // 명세서: "u_123" 형태
    private String email;  // 명세서: "user@x.com"
}
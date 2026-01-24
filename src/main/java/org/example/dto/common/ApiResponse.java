package org.example.dto.common;

import com.sun.tools.javac.util.DefinedBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private T data;
    private ApiError error;
    private String traceId;

    // 성공 응답 메서드
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .error(null)
                .traceId(UUID.randomUUID().toString().substring(0, 8))
                .build();
    }

    // 에러 응답 메서드
    public static <T> ApiResponse<T> error(String code, String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .data(null)
                .error(new ApiError(code, message))
                .traceId(UUID.randomUUID().toString().substring(0, 8))
                .build();
    }

    @Data
    @AllArgsConstructor
    public static class ApiError {
        private String code;
        private String message;
    }
}

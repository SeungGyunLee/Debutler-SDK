package org.example.dto.blockchain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class VirtualTokenRequest {

    private String address; // 필수

    @JsonProperty("chains")
    private List<String> chains; // 선택 (null이면 전체 조회)
}
package org.example.dto.blockchain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class VirtualPriceDto {
    // 예: { "usd": 41532.88, "krw": 61543210.74 }

    @JsonProperty("usd")
    private double usd;

    @JsonProperty("krw")
    private double krw;
}
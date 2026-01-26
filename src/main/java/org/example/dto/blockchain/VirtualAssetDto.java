package org.example.dto.blockchain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class VirtualAssetDto {

    private String chain;       // eth, btc 등

    @JsonProperty("asset_type")
    private String assetType;   // native, erc20 등

    @JsonProperty("asset_id")
    private String assetId;

    private String symbol;      // ETH, BTC

    private int decimals;

    @JsonProperty("balance_raw")
    private String balanceRaw;  // 블록체인은 숫자가 커서 String 권장

    private double balance;

    @JsonProperty("price_usd")
    private double priceUsd;

    @JsonProperty("price_krw")
    private double priceKrw;

    @JsonProperty("value_usd")
    private double valueUsd;

    @JsonProperty("value_krw")
    private double valueKrw;

    @JsonProperty("prices_ts")
    private String pricesTs;
}
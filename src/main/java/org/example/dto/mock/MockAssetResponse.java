package org.example.dto.mock; // 패키지명 꼭 확인하세요!

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class MockAssetResponse<T> {

    private String rspCode;
    private String rspMsg;
    private Integer resultCount;
    private List<T> resultList; // 여기가 제네릭 <T> 여야 합니다.

    // ==========================================
    // 👇 아래 클래스들이 꼭! 있어야 "MockAssetResponse.BankAccount"를 쓸 수 있습니다.
    // ==========================================

    // 1. Bank (은행)
    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class BankAccount { // static 필수
        private String accountNum;
        private String prodName;
        private String accountType;
        private String balanceAmt;
        private String currencyCode;
        private String bankCode;
        private String bankName;
        private String issueDate;
        private String maturityDate;
    }

    // 2. Card (카드)
    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Card { // static 필수
        private String cardId;
        private String cardNum;
        private String cardName;
        private String cardType;
        private String paymentAmt;
        private String cardCompanyCode;
        private String cardCompanyName;
        private String paymentDate;
        private String usedAmt;
        private String linkedBankCode;
    }

    // 3. Invest (증권) - 이름: SecurityAccount
    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class SecurityAccount { // static 필수
        private String accountNum;
        private String prodName;
        private String totalEvalAmt;
        private String depositAmt;
        private String companyCode;
        private String companyName;
        private List<Product> products;
    }

    // (증권 내부의 종목 클래스)
    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Product { // static 필수
        private String prodCode;
        private String prodName;
        private String holdQty;
        private String evalAmt;
        private String earningRate;
    }

    // 4. Insurance (보험)
    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Insurance { // static 필수
        private String insuNum;
        private String prodName;
        private String insuType;
        private String insuStatus;
        private String faceAmt;
        private String paidAmt;
        private String expDate;
        private String companyCode;
        private String companyName;
    }
}
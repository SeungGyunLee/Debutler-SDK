package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyDataPortfolioDto {
    private List<BankDto> bankList;
    private List<CardDto> cardList;
    private List<InvestDto> investList;
    private List<InsuDto> insuList;

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class BankDto {
        private String bankName;
        private String accountNum;
        private String balanceAmt;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class CardDto {
        private String cardCompanyName;
        private String cardName;
        private String paymentAmt;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class InvestDto {
        private String companyName;
        private String accountNum;
        private String totalEvalAmt;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class InsuDto {
        private String companyName;
        private String prodName;
        private String paidAmt;
    }
}
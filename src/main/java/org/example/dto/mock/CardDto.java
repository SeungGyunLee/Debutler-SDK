package org.example.dto.mock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor // 👈 [필수 1] 깡통 객체를 만들 수 있게 함
@AllArgsConstructor
public class CardDto {
    @JsonProperty("card_id") private String cardId;
    @JsonProperty("card_num") private String cardNum;
    @JsonProperty("card_name") private String cardName;
    @JsonProperty("is_consent") private boolean isConsent;
    @JsonProperty("card_type") private String cardType;
    @JsonProperty("card_member") private String cardMember;
    @JsonProperty("annual_fee") private String annualFee;
    @JsonProperty("issue_date") private String issueDate;
    @JsonProperty("is_trans_payable") private boolean isTransPayable;
    @JsonProperty("payment_amt") private String paymentAmt;
}
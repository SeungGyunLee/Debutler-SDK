package org.example.dto.mock;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter @Builder
@NoArgsConstructor // 👈 [필수 1] 깡통 객체를 만들 수 있게 함
@AllArgsConstructor
public class InvestAcctResponse {
    @JsonProperty("rsp_code") private String rspCode;
    @JsonProperty("rsp_msg") private String rspMsg;
    @JsonProperty("search_timestamp") private String searchTimestamp;
    @JsonProperty("next_page") private String nextPage;
    @JsonProperty("account_cnt") private int accountCnt;
    @JsonProperty("account_list") private List<InvestAccountDto> accountList;
}
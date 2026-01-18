package org.example.domain;


import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CryptoWallet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol; // 코인명
    private String walletAddress; // 지갑주소
    private Double quantity; // 보유 수량
    private Long balance; // 원화 환산 금액

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @com.fasterxml.jackson.annotation.JsonIgnore
    private User user; // 소유자
}

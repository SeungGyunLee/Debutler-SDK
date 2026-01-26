package org.example.domain;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "mydata_crypto") // DB 테이블 이름 지정
public class CryptoWallet {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 1. 블록체인 네트워크 (API의 'chains' 정보)
    // 예: eth, btc, sol, xrp
    @Column(nullable = false)
    private String chain;

    // 2. 지갑 주소
    @Column(nullable = false)
    private String walletAddress;

    // 3. 코인 심볼 (예: ETH, BTC, USDT)
    @Column(nullable = false)
    private String symbol;

    // 4. 보유 수량 (API의 'balance')
    // 코인은 소수점이 매우 길 수 있어서 BigDecimal 사용 필수!
    // (precision=30, scale=10 -> 전체 30자리 중 소수점 10자리까지 허용)
    @Column(precision = 30, scale = 10)
    private BigDecimal balance;

    // 5. 원화 평가 금액 (API의 'value_krw' 또는 'total_value_krw')
    @Column(precision = 20, scale = 2)
    private BigDecimal valueKrw;

    // 6. 소유자 (User 테이블과 연결)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
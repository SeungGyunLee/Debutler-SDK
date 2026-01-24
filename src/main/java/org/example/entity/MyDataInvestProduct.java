package org.example.entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Builder @NoArgsConstructor @AllArgsConstructor
@Table(name = "mydata_invest_product")
public class MyDataInvestProduct {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prodCode;
    private String prodName;
    private int holdQty; // 보유 수량

    @Column(precision = 19, scale = 2)
    private BigDecimal evalAmt; // 평가 금액

    @Column(precision = 5, scale = 2)
    private BigDecimal earningRate; // 수익률

    // 부모 계좌와 연결
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invest_id")
    private MyDataInvest invest;
}
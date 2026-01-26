package org.example.entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Builder @NoArgsConstructor @AllArgsConstructor
@Table(name = "cards", catalog = "mock_card")
public class MyDataCard {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String cardCompanyName;
    private String cardName;
    private String cardNum;

    @Column(precision = 19, scale = 2)
    private BigDecimal paymentAmt; // 결제 예정 금액
}
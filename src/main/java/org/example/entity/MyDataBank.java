package org.example.entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Builder @NoArgsConstructor @AllArgsConstructor
@Table(name = "mydata_bank")
public class MyDataBank {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String bankName;
    private String accountNum;
    private String prodName;

    @Column(precision = 19, scale = 2) // 금액은 정밀도 명시
    private BigDecimal balanceAmt;
}
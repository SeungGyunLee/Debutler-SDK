package org.example.entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter @Builder @NoArgsConstructor @AllArgsConstructor
@Table(name = "insurances", catalog = "mock_insurance")
public class MyDataInsurance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String companyName;
    private String prodName;
    private String insuType;

    @Column(precision = 19, scale = 2)
    private BigDecimal paidAmt;
}
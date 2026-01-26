package org.example.entity;

import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Builder @NoArgsConstructor @AllArgsConstructor
@Table(name = "invest_accounts", catalog = "mock_invest")
public class MyDataInvest {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String companyName;
    private String accountNum;

    private String prodName;

    @Column(precision = 19, scale = 2)
    private BigDecimal totalEvalAmt; // 총 평가 금액

    // ※ 1:N 관계 설정 (계좌 하나에 여러 종목)
    @Builder.Default
    @OneToMany(mappedBy = "invest", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MyDataInvestProduct> products = new ArrayList<>();

    // 연관관계 편의 메서드
    public void addProduct(MyDataInvestProduct product) {
        this.products.add(product);
        product.setInvest(this);
    }
}
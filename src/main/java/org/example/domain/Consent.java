package org.example.domain;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consents")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String type; // MYDATA, CRYPTO_WALLET ...

    @Column(nullable = false)
    private String version; // "2026-01-01"

    private boolean agreed;

    private LocalDateTime agreedAt;
}

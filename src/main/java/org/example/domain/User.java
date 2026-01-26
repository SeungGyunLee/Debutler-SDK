package org.example.domain;

import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    private String name;

    @Builder.Default
    @Column(nullable = false)
    private boolean isMyDataAgreed = false;

    @Builder.Default
    @Column(nullable = false)
    private boolean isCryptoAgreed = false;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public void agreeMyDataService() {
        this.isMyDataAgreed = true;
    }

    public void agreeCryptoService() {
        this.isCryptoAgreed = true;
    }
}

package org.example.repository;

import org.example.domain.Consent;
import org.example.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ConsentRepository extends JpaRepository<Consent, Long> {
    // 특정 유저의 모든 동의 내역 조회
    List<Consent> findAllByUser(User user);

    Optional<Consent> findByUserAndType(User user, String type);
}
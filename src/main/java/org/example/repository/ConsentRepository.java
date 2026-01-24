package org.example.repository;

import org.example.domain.Consent;
import org.example.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ConsentRepository extends JpaRepository<Consent, Long> {
    List<Consent> findAllByUser(User user);
    Optional<Consent> findByUserAndTypeAndVersion(User user, String type, String version);
}

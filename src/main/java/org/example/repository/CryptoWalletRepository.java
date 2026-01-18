package org.example.repository;

import org.example.domain.CryptoWallet;
import org.example.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CryptoWalletRepository extends JpaRepository<CryptoWallet, Long> {
    List<CryptoWallet> findAllByUser(User user);
}

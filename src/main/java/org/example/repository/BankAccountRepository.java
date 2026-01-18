package org.example.repository;

import org.example.domain.BankAccount;
import org.example.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
    List<BankAccount> findAllByUser(User user); // 내 계좌 전부 가져오기
}

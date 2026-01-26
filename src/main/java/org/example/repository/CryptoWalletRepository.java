package org.example.repository;

import org.example.domain.CryptoWallet;
import org.example.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CryptoWalletRepository extends JpaRepository<CryptoWallet, Long> {

    // 1. 내 가상자산 목록 조회 (기존 유지)
    List<CryptoWallet> findAllByUser(User user);

    // 2. [추가] 내 가상자산 초기화 (새로고침 할 때 기존 데이터 삭제용)
    // 블록체인 API에서 최신 정보를 받아오면, 옛날 정보를 지우고 새로 저장해야 합니다.
    void deleteByUser(User user);
}
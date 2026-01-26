package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.adapter.BlockchainAdapter;
import org.example.domain.CryptoWallet;
import org.example.domain.User;
import org.example.dto.blockchain.VirtualAssetDto;
import org.example.dto.blockchain.VirtualTokenResponse;
import org.example.repository.CryptoWalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BlockchainService {

    private final BlockchainAdapter blockchainAdapter;
    private final CryptoWalletRepository cryptoWalletRepository;

    /**
     * 블록체인 지갑 연동 및 DB 저장 (새로고침)
     * 1. 외부 API 조회
     * 2. 기존 DB 데이터 삭제 (초기화)
     * 3. 새 데이터 DB 저장
     */
    @Transactional // 삭제(delete)와 저장(save)을 한 묶음으로 처리
    public VirtualTokenResponse refreshWallet(User user, String address, List<String> chains) {
        log.info("블록체인 지갑 연동 시작 - 사용자: {}, 주소: {}", user.getId(), address);

        // 1. Adapter로 외부 API 찌르기 (데이터 가져오기)
        VirtualTokenResponse response = blockchainAdapter.getPortfolio(address, chains);

        // 2. 기존 내 데이터 싹 지우기 (중복 방지)
        cryptoWalletRepository.deleteByUser(user);

        // 3. 가져온 데이터(DTO)를 내 DB 모양(Entity)으로 변환하기
        if (response.getAssets() != null) {
            List<CryptoWallet> newWallets = response.getAssets().stream()
                    .map(dto -> mapToEntity(user, address, dto)) // 변환 마법사 호출
                    .collect(Collectors.toList());

            // 4. DB에 저장!
            cryptoWalletRepository.saveAll(newWallets);
            log.info("가상자산 {}건 저장 완료", newWallets.size());
        }

        return response; // 화면에 보여줄 데이터 반환
    }

    // [도우미] DTO(종이) -> Entity(DB) 변환 메서드
    private CryptoWallet mapToEntity(User user, String address, VirtualAssetDto dto) {
        return CryptoWallet.builder()
                .user(user)
                .chain(dto.getChain())             // 예: eth
                .walletAddress(address)            // 내 지갑 주소
                .symbol(dto.getSymbol())           // 예: ETH
                .balance(BigDecimal.valueOf(dto.getBalance()))  // 코인 개수
                .valueKrw(BigDecimal.valueOf(dto.getValueKrw())) // 원화 환산액
                .build();
    }
}
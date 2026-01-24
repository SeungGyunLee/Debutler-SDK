package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.adapter.MyDataMockAdapter;
import org.example.dto.mock.MockAssetResponse;
import org.example.entity.*;
import org.example.repository.*;
import org.example.domain.User; // 도메인 유저 확인 필수!
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;


@Slf4j
@Service
@RequiredArgsConstructor
public class MyDataSyncService {

    private final MyDataMockAdapter adapter;
    private final UserRepository userRepository;

    private final MyDataBankRepository bankRepository;
    private final MyDataCardRepository cardRepository;
    private final MyDataInvestRepository investRepository;
    private final MyDataInsuranceRepository insuranceRepository;

    @Transactional
    public void syncAllAssets(String username, String mockToken) {

        // 1. 유저 객체 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Long userId = user.getId(); // 여기서 ID를 꺼내서 씁니다.

        // 2. 기존 데이터 삭제
        bankRepository.deleteByUserId(userId);
        cardRepository.deleteByUserId(userId);
        investRepository.deleteByUserId(userId);
        insuranceRepository.deleteByUserId(userId);

        log.info(">>> 자산 동기화 시작 (User: {})", username);

        // ==========================================
        // 3. Bank (은행)
        // ==========================================
        try {

            MockAssetResponse<MockAssetResponse.BankAccount> res = adapter.getBankAccounts(mockToken);

            if (res != null && res.getResultList() != null) {
                for (MockAssetResponse.BankAccount dto : res.getResultList()) {
                    bankRepository.save(MyDataBank.builder()
                            .userId(userId) // 👈 [수정 완료] 다시 userId로 변경!
                            .bankName(dto.getBankName())
                            .accountNum(dto.getAccountNum())
                            .prodName(dto.getProdName())
                            .balanceAmt(new BigDecimal(dto.getBalanceAmt()))
                            .build());
                }
            }
        } catch (Exception e) { log.error("Bank Sync Fail", e); }

        // ==========================================
        // 4. Card (카드)
        // ==========================================
        try {
            MockAssetResponse<MockAssetResponse.Card> res = adapter.getCards(mockToken);

            if (res != null && res.getResultList() != null) {
                for (MockAssetResponse.Card dto : res.getResultList()) {
                    cardRepository.save(MyDataCard.builder()
                            .userId(userId) // 👈 [수정 완료]
                            .cardCompanyName(dto.getCardCompanyName())
                            .cardNum(dto.getCardNum())
                            .cardName(dto.getCardName())
                            .paymentAmt(new BigDecimal(dto.getPaymentAmt()))
                            .build());
                }
            }
        } catch (Exception e) { log.error("Card Sync Fail", e); }

        // ==========================================
        // 5. Invest (증권)
        // ==========================================
        try {
            MockAssetResponse<MockAssetResponse.SecurityAccount> res = adapter.getInvestAccounts(mockToken);

            if (res != null && res.getResultList() != null) {
                for (MockAssetResponse.SecurityAccount dto : res.getResultList()) {
                    MyDataInvest invest = MyDataInvest.builder()
                            .userId(userId) // 👈 [수정 완료]
                            .companyName(dto.getCompanyName())
                            .accountNum(dto.getAccountNum())
                            .prodName(dto.getProdName())
                            .totalEvalAmt(new BigDecimal(dto.getTotalEvalAmt()))
                            .build();

                    if (dto.getProducts() != null) {
                        for (MockAssetResponse.Product p : dto.getProducts()) {
                            invest.addProduct(MyDataInvestProduct.builder()
                                    .prodName(p.getProdName())
                                    .holdQty(Integer.parseInt(p.getHoldQty()))
                                    .evalAmt(new BigDecimal(p.getEvalAmt()))
                                    .build());
                        }
                    }
                    investRepository.save(invest);
                }
            }
        } catch (Exception e) { log.error("Invest Sync Fail", e); }

        // ==========================================
        // 6. Insurance (보험)
        // ==========================================
        try {
            MockAssetResponse<MockAssetResponse.Insurance> res = adapter.getInsuContracts(mockToken);

            if (res != null && res.getResultList() != null) {
                for (MockAssetResponse.Insurance dto : res.getResultList()) {
                    insuranceRepository.save(MyDataInsurance.builder()
                            .userId(userId) // 👈 [수정 완료]
                            .companyName(dto.getCompanyName())
                            .prodName(dto.getProdName())
                            .insuType(dto.getInsuType())
                            .paidAmt(new BigDecimal(dto.getPaidAmt()))
                            .build());
                }
            }
        } catch (Exception e) { log.error("Insu Sync Fail", e); }

        log.info(">>> 동기화 완료!");
    }
}
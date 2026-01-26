package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.adapter.MyDataMockAdapter;
// [변경] 새로 만든 DTO 패키지 Import (패키지명은 본인 프로젝트에 맞게 수정!)
import org.example.dto.mock.*;
import org.example.entity.*;
import org.example.repository.*;
import org.example.domain.User;
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

    /**
     * 자산 동기화 메서드
     * @param username 유저 아이디
     * @param mockToken 마이데이터 접근 토큰
     * @param userSearchId [New] 사용자 식별값 (CI) - 표준 규격 필수 헤더
     */
    @Transactional
    public void syncAllAssets(String username, String mockToken, String userSearchId) {

        // 1. 유저 객체 조회
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        Long userId = user.getId();

        // 2. 기존 데이터 삭제 (중복 방지)
        bankRepository.deleteByUserId(userId);
        cardRepository.deleteByUserId(userId);
        investRepository.deleteByUserId(userId);
        insuranceRepository.deleteByUserId(userId);

        log.info(">>> 자산 동기화 시작 (User: {}, CI: {})", username, userSearchId);

        // ==========================================
        // 3. Bank (은행) - 일반 계좌 + IRP
        // ==========================================
        try {
            // 3-1. 일반 수신 계좌
            BankAcctResponse bankRes = adapter.getBankAccounts(mockToken, userSearchId);
            if (bankRes != null && bankRes.getAccountList() != null) {
                for (BankAccountDto dto : bankRes.getAccountList()) {
                    bankRepository.save(MyDataBank.builder()
                            .userId(userId)
                            .bankName("Woori Bank") // 표준 API 리스트엔 은행명이 없으므로(기관코드로 식별) 임의 지정
                            .accountNum(dto.getAccountNum())
                            .prodName(dto.getProdName())
                            .balanceAmt(new BigDecimal(dto.getBalanceAmt())) // String -> BigDecimal
                            .build());
                }
            }

            // 3-2. [New] 은행 IRP 계좌 (별도 API)
            BankIrpResponse irpRes = adapter.getBankIrps(mockToken, userSearchId);
            if (irpRes != null && irpRes.getIrpList() != null) {
                for (BankIrpDto dto : irpRes.getIrpList()) {
                    bankRepository.save(MyDataBank.builder()
                            .userId(userId)
                            .bankName("Woori Bank (IRP)")
                            .accountNum(dto.getAccountNum())
                            .prodName(dto.getProdName())
                            .balanceAmt(new BigDecimal(dto.getEvalAmt())) // IRP는 평가금액 사용
                            .build());
                }
            }
        } catch (Exception e) { log.error("Bank Sync Fail", e); }

        // ==========================================
        // 4. Card (카드)
        // ==========================================
        try {
            CardResponse cardRes = adapter.getCards(mockToken, userSearchId);
            if (cardRes != null && cardRes.getCardList() != null) {
                for (CardDto dto : cardRes.getCardList()) {
                    cardRepository.save(MyDataCard.builder()
                            .userId(userId)
                            .cardCompanyName("Hyundai Card") // 임의 지정
                            .cardNum(dto.getCardNum())
                            .cardName(dto.getCardName())
                            .paymentAmt(new BigDecimal(dto.getPaymentAmt())) // 결제 예정 금액
                            .build());
                }
            }
        } catch (Exception e) { log.error("Card Sync Fail", e); }

        // ==========================================
        // 5. Invest (증권) - 위탁 계좌 + IRP
        // ==========================================
        try {
            // 5-1. 일반 위탁 계좌
            InvestAcctResponse investRes = adapter.getInvestAccounts(mockToken, userSearchId);
            if (investRes != null && investRes.getAccountList() != null) {
                for (InvestAccountDto dto : investRes.getAccountList()) {
                    MyDataInvest invest = MyDataInvest.builder()
                            .userId(userId)
                            .companyName("Kiwoom Securities")
                            .accountNum(dto.getAccountNum())
                            .prodName(dto.getAccountName())
                            .totalEvalAmt(new BigDecimal(dto.getEvalAmt()))
                            .build();

                    // 주의: 표준 v2.0 '계좌 목록 조회' API에는 '보유 종목(products)'이 포함되지 않음.
                    // 종목 정보를 가져오려면 '/accounts/{account_num}/products'를 별도로 호출해야 함.
                    // 이번 Mock 구현에서는 계좌 총액만 동기화합니다.

                    investRepository.save(invest);
                }
            }

            // 5-2. [New] 증권 IRP 계좌
            InvestIrpResponse irpRes = adapter.getInvestIrps(mockToken, userSearchId);
            if (irpRes != null && irpRes.getIrpList() != null) {
                for (InvestIrpDto dto : irpRes.getIrpList()) {
                    investRepository.save(MyDataInvest.builder()
                            .userId(userId)
                            .companyName("Mirae Asset (IRP)")
                            .accountNum(dto.getAccountNum())
                            .prodName(dto.getProdName())
                            .totalEvalAmt(new BigDecimal(dto.getEvalAmt()))
                            .build());
                }
            }
        } catch (Exception e) { log.error("Invest Sync Fail", e); }

        // ==========================================
        // 6. Insurance (보험)
        // ==========================================
        try {
            InsuResponse insuRes = adapter.getInsuContracts(mockToken, userSearchId);
            if (insuRes != null && insuRes.getInsuList() != null) {
                for (InsuDto dto : insuRes.getInsuList()) {
                    insuranceRepository.save(MyDataInsurance.builder()
                            .userId(userId)
                            .companyName("Samsung Fire")
                            .prodName(dto.getProdName())
                            .insuType(dto.getInsuType())
                            .paidAmt(new BigDecimal(dto.getFaceAmt())) // 가입금액(보장금액)을 매핑 (실제 납입료 필드는 별도)
                            .build());
                }
            }
        } catch (Exception e) { log.error("Insu Sync Fail", e); }

        log.info(">>> 동기화 완료!");
    }
}
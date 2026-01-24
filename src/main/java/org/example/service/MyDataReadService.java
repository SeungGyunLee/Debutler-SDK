package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.User;
import org.example.dto.MyDataPortfolioDto;
import org.example.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyDataReadService {

    private final UserRepository userRepository;
    private final MyDataBankRepository bankRepository;
    private final MyDataCardRepository cardRepository;
    private final MyDataInvestRepository investRepository;
    private final MyDataInsuranceRepository insuranceRepository;

    /**
     * 유저네임으로 통합 자산 포트폴리오를 조회합니다.
     */
    public MyDataPortfolioDto getPortfolioByUsername(String username) {

        // 1. 유저 정보 조회
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        Long userId = user.getId();

        // 2. 각 자산 엔티티를 DTO로 변환하여 통합 DTO 빌드
        return MyDataPortfolioDto.builder()
                // --- 은행 (Bank) ---
                .bankList(bankRepository.findByUserId(userId).stream()
                        .map(e -> MyDataPortfolioDto.BankDto.builder()
                                .bankName(e.getBankName())
                                .accountNum(e.getAccountNum())
                                .balanceAmt(e.getBalanceAmt() != null ? e.getBalanceAmt().toString() : "0")
                                .build())
                        .collect(Collectors.toList()))

                // --- 카드 (Card) ---
                .cardList(cardRepository.findByUserId(userId).stream()
                        .map(e -> MyDataPortfolioDto.CardDto.builder()
                                .cardCompanyName(e.getCardCompanyName())
                                .cardName(e.getCardName())
                                .paymentAmt(e.getPaymentAmt() != null ? e.getPaymentAmt().toString() : "0")
                                .build())
                        .collect(Collectors.toList()))

                // --- 증권 (Invest) : 보내주신 엔티티 필드 반영 ---
                .investList(investRepository.findByUserId(userId).stream()
                        .map(e -> MyDataPortfolioDto.InvestDto.builder()
                                .companyName(e.getCompanyName())
                                .accountNum(e.getAccountNum())
                                .totalEvalAmt(e.getTotalEvalAmt() != null ? e.getTotalEvalAmt().toString() : "0")
                                .build())
                        .collect(Collectors.toList()))

                // --- 보험 (Insurance) ---
                .insuList(insuranceRepository.findByUserId(userId).stream()
                        .map(e -> MyDataPortfolioDto.InsuDto.builder()
                                .companyName(e.getCompanyName())
                                .prodName(e.getProdName())
                                .paidAmt(e.getPaidAmt() != null ? e.getPaidAmt().toString() : "0")
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }
}
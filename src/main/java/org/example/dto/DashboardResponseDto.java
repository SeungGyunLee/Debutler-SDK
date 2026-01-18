package org.example.dto;

import lombok.Builder;
import lombok.Getter;
import org.example.domain.BankAccount;
import org.example.domain.CryptoWallet;

import java.util.List;

@Getter
@Builder
public class DashboardResponseDto {
    private Long totalBalance; // 총자산 표시 (은행 + 가상자산)
    private Long bankTotalBalance; // 은행 총액
    private Long cryptoTotalBalance; // 가상자산 총액

    private List<BankAccount> bankAccounts;
    private List<CryptoWallet> cryptoWallets;
}

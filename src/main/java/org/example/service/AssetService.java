package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.domain.*;
import org.example.dto.*;
import org.example.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    private final BankAccountRepository bankRepo;
    private final CryptoWalletRepository cryptoRepo;
    private final UserRepository userRepo;

    // 대시보드 정보 조회
    @Transactional(readOnly = true)
    public DashboardResponseDto getDashboard(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        List<BankAccount> banks = bankRepo.findAllByUser(user);
        List<CryptoWallet> cryptos = cryptoRepo.findAllByUser(user);

        long bankSum = banks.stream().mapToLong(BankAccount::getBalance).sum();
        long cryptoSum = cryptos.stream().mapToLong(CryptoWallet::getBalance).sum();

        return DashboardResponseDto.builder()
                .totalBalance(bankSum + cryptoSum)
                .bankTotalBalance(bankSum)
                .cryptoTotalBalance(cryptoSum)
                .bankAccounts(banks)
                .cryptoWallets(cryptos)
                .build();
    }

    // 은행 계좌 추가
    @Transactional
    public void addBank(String username, AddAssetRequestDto dto) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        BankAccount account = BankAccount.builder()
                .user(user)
                .bankname(dto.getBankName())
                .accountNumber(dto.getAccountNumber())
                .balance(dto.getBalance())
                .build();
        bankRepo.save(account);
    }

    // 코인 계좌 추가
    @Transactional
    public void addCrypto(String username, AddAssetRequestDto dto) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        CryptoWallet wallet = CryptoWallet.builder()
                .user(user)
                .symbol(dto.getSymbol())
                .walletAddress(dto.getWalletAddress())
                .quantity(dto.getQuantity())
                .balance(dto.getBalance())
                .build();
        cryptoRepo.save(wallet);
    }
}

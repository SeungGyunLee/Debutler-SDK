package org.example.repository;

import org.example.entity.MyDataBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MyDataBankRepository extends JpaRepository<MyDataBank, Long> {


    // 특정 유저의 데이터만 싹 지우기 (초기화용)
    void deleteByUserId(Long userId);

    // 특정 유저의 데이터 가져오기
    List<MyDataBank> findByUserId(Long userId);
}
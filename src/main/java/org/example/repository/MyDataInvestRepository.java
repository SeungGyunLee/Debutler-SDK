package org.example.repository;

import org.example.entity.MyDataInvest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MyDataInvestRepository extends JpaRepository<MyDataInvest, Long> {
    void deleteByUserId(Long userId);
    List<MyDataInvest> findByUserId(Long userId);
}
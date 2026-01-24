package org.example.repository;

import org.example.entity.MyDataInsurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MyDataInsuranceRepository extends JpaRepository<MyDataInsurance, Long> {
    List<MyDataInsurance> findByUserId(Long userId);
    void deleteByUserId(Long userId);
    List<MyDataInsurance> findAllByUserId(Long userId);
}
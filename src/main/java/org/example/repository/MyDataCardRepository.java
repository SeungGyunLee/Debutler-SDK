package org.example.repository;

import org.example.entity.MyDataCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MyDataCardRepository extends JpaRepository<MyDataCard, Long> {
    List<MyDataCard> findByUserId(Long userId);
    void deleteByUserId(Long userId);

}
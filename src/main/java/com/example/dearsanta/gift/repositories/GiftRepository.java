package com.example.dearsanta.gift.repositories;

import com.example.dearsanta.gift.models.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftRepository extends JpaRepository<Gift, Long> {
    List<Gift> findByGiftListId(Long giftListId);
}

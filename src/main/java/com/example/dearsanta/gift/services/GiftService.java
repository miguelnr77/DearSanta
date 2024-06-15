package com.example.dearsanta.gift.services;

import com.example.dearsanta.gift.models.Gift;
import com.example.dearsanta.gift.repositories.GiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftService {

    private final GiftRepository giftRepository;

    @Autowired
    public GiftService(GiftRepository giftRepository) {
        this.giftRepository = giftRepository;
    }

    public List<Gift> findByGiftListId(Long giftListId) {
        return giftRepository.findByGiftListId(giftListId);
    }

    public Gift save(Gift gift) {
        return giftRepository.save(gift);
    }

    public void deleteById(Long id) {
        giftRepository.deleteById(id);
    }

    public Gift findById(Long id) {
        return giftRepository.findById(id).orElse(null);
    }
}

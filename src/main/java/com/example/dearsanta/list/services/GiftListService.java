package com.example.dearsanta.list.services;

import com.example.dearsanta.list.models.GiftList;
import com.example.dearsanta.list.repositories.GiftListRepository;
import com.example.dearsanta.users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftListService {

    private final GiftListRepository giftListRepository;

    @Autowired
    public GiftListService(GiftListRepository giftListRepository) {
        this.giftListRepository = giftListRepository;
    }

    public List<GiftList> findByUser(User user) {
        return giftListRepository.findByUser(user);
    }

    public GiftList saveGiftList(GiftList giftList) {
        return giftListRepository.save(giftList);
    }

    public void deleteGiftList(Long id) {
        giftListRepository.deleteById(id);
    }

    public GiftList getGiftListById(Long id) {
        return giftListRepository.findById(id).orElse(null);
    }
}

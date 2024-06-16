package com.example.dearsanta.list.services;

import com.example.dearsanta.gift.models.Gift;
import com.example.dearsanta.list.models.GiftList;
import com.example.dearsanta.list.repositories.GiftListRepository;
import com.example.dearsanta.users.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GiftListService {

    private final GiftListRepository giftListRepository;

    @Autowired
    public GiftListService(GiftListRepository giftListRepository) {
        this.giftListRepository = giftListRepository;
    }

    @Transactional(readOnly = true)
    public List<GiftList> findByUser(User user) {
        return giftListRepository.findByUser(user);
    }

    @Transactional
    public GiftList saveGiftList(GiftList giftList) {
        return giftListRepository.save(giftList);
    }

    @Transactional
    public void deleteGiftList(Long id) {
        GiftList giftList = giftListRepository.findById(id).orElse(null);
        if (giftList != null) {
            giftListRepository.delete(giftList);
        }
    }

    @Transactional(readOnly = true)
    public GiftList getGiftListById(Long id) {
        return giftListRepository.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public GiftList getGiftListWithGifts(Long id) {
        GiftList giftList = giftListRepository.findById(id).orElse(null);
        if (giftList != null) {
            giftList.getGifts().size(); // Load gifts collection
        }
        return giftList;
    }

    @Transactional
    public void updateGiftListStatus(GiftList giftList) {
        boolean allReceived = giftList.getGifts().stream()
                .allMatch(gift -> gift.getStatus() == Gift.Status.RECIBIDO);
        GiftList.Status newStatus = allReceived ? GiftList.Status.CERRADA : GiftList.Status.PENDIENTE;
        giftList.setStatus(newStatus);
        giftListRepository.save(giftList);
    }
}
